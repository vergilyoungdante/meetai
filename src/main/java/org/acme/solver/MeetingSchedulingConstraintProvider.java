package org.acme.solver;

import org.acme.domain.planningEntity.MeetingAssignment;
import org.acme.domain.problemFact.Attendance;
import org.acme.domain.problemFact.TimeGrain;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import static org.optaplanner.core.api.score.stream.Joiners.*;

public class MeetingSchedulingConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{avoidOvertime(constraintFactory),
                requiredAttendanceConflict(constraintFactory),
                startAndEndOnSameDay(constraintFactory),
                doMeetingsAsSoonAsPossible(constraintFactory),
                overlappingMeetings(constraintFactory)
        };
    }

    protected Constraint avoidOvertime(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingAssignment.class)
                .filter(meetingAssignment -> meetingAssignment.getStartingTimeGrain() != null)
                .ifNotExists(TimeGrain.class, equal(MeetingAssignment::getLastTimeGrainIndex, TimeGrain::getGrainIndex))
                .penalize("Don't go in overtime", HardMediumSoftScore.ofHard(1), MeetingAssignment::getLastTimeGrainIndex);
    }

    protected Constraint requiredAttendanceConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(Attendance.class,
                        equal(Attendance::getPerson))
                .join(MeetingAssignment.class,
                        equal((leftRequiredAttendance, rightRequiredAttendance) -> leftRequiredAttendance.getMeeting(),
                                MeetingAssignment::getMeeting))
                .join(MeetingAssignment.class,
                        equal((leftRequiredAttendance, rightRequiredAttendance, leftAssignment) -> rightRequiredAttendance
                                        .getMeeting(),
                                MeetingAssignment::getMeeting),
                        overlapping((attendee1, attendee2, assignment) -> assignment.getStartingTimeGrain().getGrainIndex(),
                                (attendee1, attendee2, assignment) -> assignment.getStartingTimeGrain().getGrainIndex() +
                                        assignment.getMeeting().getDurationInGrains(),
                                assignment -> assignment.getStartingTimeGrain().getGrainIndex(),
                                assignment -> assignment.getStartingTimeGrain().getGrainIndex() +
                                        assignment.getMeeting().getDurationInGrains()))
                .penalize("Required attendance conflict",
                        HardMediumSoftScore.ofHard(1),
                        (leftRequiredAttendance, rightRequiredAttendance, leftAssignment, rightAssignment) -> rightAssignment
                                .calculateOverlap(leftAssignment));
    }

    protected Constraint startAndEndOnSameDay(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingAssignment.class)
                .filter(meetingAssignment -> meetingAssignment.getStartingTimeGrain() != null)
                .join(TimeGrain.class,
                        equal(MeetingAssignment::getLastTimeGrainIndex, TimeGrain::getGrainIndex),
                        filtering((meetingAssignment,
                                   timeGrain) -> meetingAssignment.getStartingTimeGrain().getDay() != timeGrain.getDay()))
                .penalize("Start and end on same day",
                        HardMediumSoftScore.ofHard(1));
    }

    protected Constraint doMeetingsAsSoonAsPossible(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingAssignment.class)
                .filter(meetingAssignment -> meetingAssignment.getStartingTimeGrain() != null)
                .penalize("Do all meetings as soon as possible",
                        HardMediumSoftScore.ofSoft(1),
                        MeetingAssignment::getLastTimeGrainIndex);
    }

    protected Constraint overlappingMeetings(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingAssignment.class)
                .filter(meetingAssignment -> meetingAssignment.getStartingTimeGrain() != null)
                .join(constraintFactory.forEachIncludingNullVars(MeetingAssignment.class)
                                .filter(meetingAssignment -> meetingAssignment.getStartingTimeGrain() != null),
                        greaterThan((leftAssignment) -> leftAssignment.getMeeting().getId(),
                                (rightAssignment) -> rightAssignment.getMeeting().getId()),
                        overlapping(assignment -> assignment.getStartingTimeGrain().getGrainIndex(),
                                assignment -> assignment.getStartingTimeGrain().getGrainIndex() +
                                        assignment.getMeeting().getDurationInGrains()))
                .penalize("Overlapping meetings",HardMediumSoftScore.ofHard(1), MeetingAssignment::calculateOverlap);
    }


}
