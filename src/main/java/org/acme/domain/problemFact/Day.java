package org.acme.domain.problemFact;

import org.acme.domain.AbstractPersistable;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "day")
public class Day extends AbstractPersistable {

    @Positive
    @Column(name = "day_of_year", nullable = false)
    @Schema(title = "一年中的第多少天")
    private int dayOfYear;
    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("E", Locale.CHINA);

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public String getDateString() {
        return DAY_FORMATTER.format(toDate());
    }

    public LocalDate toDate() {
        return LocalDate.ofYearDay(LocalDate.now().getYear(), dayOfYear);
    }

    @Override
    public String toString() {
        return getDateString();
    }
}
