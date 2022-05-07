package org.acme.domain.planningEntity;

import org.acme.domain.problemFact.Meet;
import org.acme.domain.problemFact.TimeGrain;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class MeetingAssignment {

    @PlanningId
    private Long id;
    private Meet meeting;

    //true就是这个实例不动了，continue-planning中这个特别重要
    private boolean pinned;

    // Planning variables: changes during planning, between score calculations.
    private TimeGrain startingTimeGrain;

    public MeetingAssignment() {
    }

    public MeetingAssignment(long id, Meet meeting, TimeGrain startingTimeGrain) {
        this.id = id;
        this.meeting = meeting;
        this.startingTimeGrain = startingTimeGrain;
    }

    public Meet getMeeting() {
        return meeting;
    }

    public void setMeeting(Meet meeting) {
        this.meeting = meeting;
    }

    @PlanningPin
    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    @PlanningVariable(valueRangeProviderRefs = { "timeGrainRange" })
    public TimeGrain getStartingTimeGrain() {
        return startingTimeGrain;
    }

    public void setStartingTimeGrain(TimeGrain startingTimeGrain) {
        this.startingTimeGrain = startingTimeGrain;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int calculateOverlap(MeetingAssignment other) {
        if (startingTimeGrain == null || other.getStartingTimeGrain() == null) {
            return 0;
        }
        int start = startingTimeGrain.getGrainIndex();
        int end = start + meeting.getDurationInGrains();
        int otherStart = other.startingTimeGrain.getGrainIndex();
        int otherEnd = otherStart + other.meeting.getDurationInGrains();

        if (end < otherStart) {
            return 0;
        } else if (otherEnd < start) {
            return 0;
        }
        return Math.min(end, otherEnd) - Math.max(start, otherStart);
    }

    public Integer getLastTimeGrainIndex() {
        if (startingTimeGrain == null) {
            return null;
        }
        return startingTimeGrain.getGrainIndex() + meeting.getDurationInGrains() - 1;
    }

    public String getStartingDateTimeString() {
        if (startingTimeGrain == null) {
            return null;
        }
        return startingTimeGrain.getDateTimeString();
    }

    public int getRequiredCapacity() {
        return meeting.getRequiredCapacity();
    }

    @Override
    public String toString() {
        return meeting.toString();
    }
}
