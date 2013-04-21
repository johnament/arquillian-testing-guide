package com.tad.arquillian.chapter8.entity;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PersonDAO {
	@PersistenceContext(unitName="Chapter8Persistence")
	private EntityManager em;
	
	public void deleteAllPeople() {
		em.createQuery("Delete from Person p").executeUpdate();
	}
	
	public Person save(Person p) {
		return em.merge(p);
	}
	
	public Person find(int id) {
		return em.find(Person.class, id);
	}
	
	public List<Person> findAllPeople() {
		return em.createQuery("select p from Person p order by p.id",Person.class).getResultList();
	}
}
