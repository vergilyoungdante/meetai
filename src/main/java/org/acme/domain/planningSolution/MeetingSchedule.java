package org.acme.domain.planningSolution;

import org.acme.domain.planningEntity.MeetingAssignment;
import org.acme.domain.problemFact.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.util.List;

@PlanningSolution
public class MeetingSchedule {
//    @ConstraintConfigurationProvider
//    private MeetingConstraintConfiguration constraintConfiguration;

    @Schema(title = "会议列表")
    @ProblemFactCollectionProperty
    private List<Meet> meetingList;

    @Schema(title = "可用日期列表")
    @ProblemFactCollectionProperty
    private List<Day> dayList;
    @ValueRangeProvider(id = "timeGrainRange")
    @ProblemFactCollectionProperty
    private List<TimeGrain> timeGrainList;

    @ProblemFactCollectionProperty
    private List<Person> personList;
    @ProblemFactCollectionProperty
    private List<Attendance> attendanceList;

    @PlanningEntityCollectionProperty
    private List<MeetingAssignment> meetingAssignmentList;

    @PlanningScore
    private HardMediumSoftScore score;

//    public MeetingConstraintConfiguration getConstraintConfiguration() {
//        return constraintConfiguration;
//    }
//
//    public void setConstraintConfiguration(MeetingConstraintConfiguration constraintConfiguration) {
//        this.constraintConfiguration = constraintConfiguration;
//    }

    public List<Meet> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Meet> meetingList) {
        this.meetingList = meetingList;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }

    public List<TimeGrain> getTimeGrainList() {
        return timeGrainList;
    }

    public void setTimeGrainList(List<TimeGrain> timeGrainList) {
        this.timeGrainList = timeGrainList;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public List<MeetingAssignment> getMeetingAssignmentList() {
        return meetingAssignmentList;
    }

    public void setMeetingAssignmentList(List<MeetingAssignment> meetingAssignmentList) {
        this.meetingAssignmentList = meetingAssignmentList;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }
}
