package com.tad.arquillian.chp7.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.tad.arquillian.chapter7.model.People;
import com.tad.arquillian.chp7.dao.PeopleDAO;


/**
 * @author JohnAment
 *
 */
@RequestScoped
@Path("/people")
public class PeopleResource {
    @EJB
    private PeopleDAO peopleDao;
    
    @Produces("application/json")
    @Path("/list")
    @BadgerFish
    @GET
    public List<People> list() {
        return peopleDao.findAllPeople();
    }
    
    @Produces("application/json")
    @Path("/find/{personId}")
    @BadgerFish
    @GET
    public People find(@PathParam("personId") Integer personId) {
        return peopleDao.find(personId);
    }
    
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("/save")
    @BadgerFish
    @POST
    public Response update(@FormParam("personId")Integer personId,
            @FormParam("email")String email,
            @FormParam("firstName")String firstName,
            @FormParam("lastName")String lastName,
            @FormParam("salutation")String salutation
            ) {
        People person = new People();
        if(personId != null && personId > 0) {
            person.setPersonId(personId);
        }
        person.setEmail(email);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setSalutation(salutation);
        peopleDao.savePeople(person);
        return Response.ok().build();
    }
    
    @Produces("text/plain")
    @Path("/email/{email}")
    @GET
    public String find(@PathParam("email") String email) {
        List<People> peeps = peopleDao.findByEmail(email);
        if(peeps.size() > 0) {
            People p = peeps.get(0);
            return String.format("%s %s %s",p.getSalutation(),p.getFirstName(),p.getLastName());
        } else {
            return "none found.";
        }
    }
}
