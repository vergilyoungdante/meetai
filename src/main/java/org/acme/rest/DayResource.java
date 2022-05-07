package org.acme.rest;

import org.acme.domain.problemFact.Day;
import org.acme.repository.DayRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/day")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DayResource {

    @Inject
    DayRepository dayRepository;


    @GET
    public List<Day> allDay() {
        return dayRepository.listAll();
    }

    @GET
    @Path("/{dayId}")
    public Day getDay(@PathParam("dayId") Long dayId) {
        Day day = dayRepository.findById(dayId);
        if (day == null) {
            throw new WebApplicationException("day with id" + dayId + "does net exist", 404);
        }
        return day;
    }

    @POST
    @Transactional
    public Response createDay(Day day) {
        if (day.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 400);
        }

        dayRepository.persist(day);
        return Response.status(201).entity(day).build();
    }

}
