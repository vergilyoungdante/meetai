package org.acme.domain.problemFact;

import org.acme.domain.AbstractPersistable;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Attendance extends AbstractPersistable {
    @Schema(title = "出席人")
    private Person person;
    @Schema(title = "出席会议")
    private Meet meeting;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Meet getMeeting() {
        return meeting;
    }

    public void setMeeting(Meet meeting) {
        this.meeting = meeting;
    }

    @Override
    public String toString() {
        return person + "-" + meeting;
    }
}
