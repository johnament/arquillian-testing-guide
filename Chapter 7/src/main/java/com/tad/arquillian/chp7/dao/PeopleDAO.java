package com.tad.arquillian.chp7.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.tad.arquillian.chapter7.model.People;


@Stateless
@LocalBean
public class PeopleDAO {
	@PersistenceContext
	private EntityManager entityManager;
	
	public People find(int personId) {
	    return entityManager.find(People.class,personId);
	}
	
	public List<People> findAllPeople() {
		return entityManager.createQuery("select p from People p order by p.personId",People.class).getResultList();
	}
	
	public People savePeople(People p) {
		return this.entityManager.merge(p);
	}
	
	public List<People> findByEmail(String email) {
	    return entityManager.createQuery("select p from People p where p.email = :email order by p.personId",People.class).setParameter("email", email).getResultList();
	}
}
