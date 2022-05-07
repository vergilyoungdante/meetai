package org.acme.domain.problemFact;

import org.acme.domain.AbstractPersistable;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

public class Meet extends AbstractPersistable {
    @Schema(title = "会议名称")
    private String topic;
    @Schema(title = "发言人")
    private List<Person> speakerList;

    private String content;
    /**
     * 乘以{@link TimeGrain#GRAIN_LENGTH_IN_MINUTES}获得会议时长
     */
    private int durationInGrains;

    private List<Attendance> requiredAttendanceList;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Person> getSpeakerList() {
        return speakerList;
    }

    public void setSpeakerList(List<Person> speakerList) {
        this.speakerList = speakerList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDurationInGrains() {
        return durationInGrains;
    }

    public void setDurationInGrains(int durationInGrains) {
        this.durationInGrains = durationInGrains;
    }

    public List<Attendance> getRequiredAttendanceList() {
        return requiredAttendanceList;
    }

    public void setRequiredAttendanceList(List<Attendance> requiredAttendanceList) {
        this.requiredAttendanceList = requiredAttendanceList;
    }



    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getRequiredCapacity() {
        return requiredAttendanceList.size();
    }

    public String getDurationString() {
        return (durationInGrains * TimeGrain.GRAIN_LENGTH_IN_MINUTES) + " minutes";
    }

    public String getLabel() {
        return topic;
    }

    @Override
    public String toString() {
        return topic;
    }
}
