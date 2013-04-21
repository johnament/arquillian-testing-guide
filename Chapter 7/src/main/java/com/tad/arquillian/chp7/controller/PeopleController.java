package com.tad.arquillian.chp7.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.tad.arquillian.chapter7.model.People;
import com.tad.arquillian.chp7.dao.PeopleDAO;

@SessionScoped
@Named("people")
public class PeopleController implements Serializable {
	@EJB
	private PeopleDAO peopleDao;
	
	private People selectedPerson;
	private int personId;
	
	private List<People> everybody;
	
	@PostConstruct
	public void init() {
		this.everybody = this.peopleDao.findAllPeople();
	}
	
	@Produces
	@Named("peopleList")
	public List<People> showPeople() {
		return this.everybody;
	}

	public People getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(People selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
		if(this.personId>0) {
			for(People p : this.everybody) {
				if(p.getPersonId() == this.personId) {
					this.selectedPerson = p;
					break;
				}
			}
		} else {
			this.selectedPerson = new People();
		}
	}
	public String savePerson() {
		this.peopleDao.savePeople(this.selectedPerson);
		this.selectedPerson = new People();
		this.personId = 0;
		this.init();
		return "/people.xhtml";
	}
}
