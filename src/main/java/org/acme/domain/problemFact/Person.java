package org.acme.domain.problemFact;

import org.acme.domain.AbstractPersistable;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Person extends AbstractPersistable {
    @Schema(title = "姓名")
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person person = (Person) obj;
        return id.equals(person.id) &&
                fullName.equals(person.fullName);
    }

    @Override
    public String toString() {
        return fullName;
    }
}
