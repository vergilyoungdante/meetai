package org.acme.rest;


import org.acme.domain.problemFact.Person;
import org.acme.repository.PersonRepository;

import javax.transaction.Transactional;
import javax.ws.rs.*;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonRepository personRepository;

    @GET
    public List<Person> allPerson() {
        return personRepository.listAll();
    }

    @GET
    @Path("/{personID}")
    public Person getPerson(@PathParam("personID") Long personID){
        Person person = personRepository.findById(personID);
        if(person==null){
            throw new WebApplicationException("任务ID" + personID + "不存在",404);
        }
        return person;
    }

    @POST
    @Transactional
    public Response createPerson(Person person) {
        if (person.getId() != null) {
            throw new WebApplicationException("创建新人物时不能带有id", 400);
        }

        personRepository.persist(person);
        return Response.status(201).entity(person).build();
    }
}
