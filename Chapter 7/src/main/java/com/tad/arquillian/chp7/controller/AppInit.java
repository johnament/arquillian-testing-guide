package com.tad.arquillian.chp7.controller;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.tad.arquillian.chapter7.model.People;
import com.tad.arquillian.chp7.dao.PeopleDAO;

@Singleton
@Startup
public class AppInit {
	@EJB
	private PeopleDAO dao;
	
	@PostConstruct
	public void init() {
		People p = new People();
		p.setEmail("john@nowhere.net");
		p.setFirstName("John");
		p.setLastName("Bob");
		p.setSalutation("Mr.");
		dao.savePeople(p);
	}
}
