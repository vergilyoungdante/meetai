package org.acme.domain.problemFact;

import org.acme.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person extends AbstractPersistable {

    @NotBlank(message = "不能输入空格")
    @NotNull(message = "输入不能为空")
    @Column(name = "full_name")
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
