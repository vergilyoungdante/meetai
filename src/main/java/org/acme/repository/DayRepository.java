package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.domain.problemFact.Day;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DayRepository implements PanacheRepository<Day> {
}
