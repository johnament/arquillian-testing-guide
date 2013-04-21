package com.tad.arquillian.chapter8.persistence;

import java.util.List;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.chapter8.entity.Person;
import com.tad.arquillian.chapter8.entity.PersonDAO;

@RunWith(Arquillian.class)
public class PersonTest {
	@Deployment
	public static JavaArchive createArchive() {
		JavaArchive ja = ShrinkWrap.create(JavaArchive.class, "chapter8-persistence.jar")
				.addClasses(Person.class, PersonDAO.class)
				.addAsManifestResource("META-INF/persistence.xml","persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return ja;
	}

	@EJB
	private PersonDAO dao;

	@Before
	public void setupTestData() {
		dao.deleteAllPeople();
		Person p1 = new Person();
		p1.setFirstName("Bob");
		p1.setLastName("Smith");
		dao.save(p1);
		Person p2 = new Person();
		p2.setFirstName("Jane");
		p2.setLastName("Smith");
		dao.save(p2);
	}

	@Test
	public void testLoadedPeople() {
		List<Person> allPeople = dao.findAllPeople();
		Assert.assertEquals(2, allPeople.size());
		Person p1 = allPeople.get(0);
		Assert.assertEquals("Bob", p1.getFirstName());
		Assert.assertEquals("Smith", p1.getLastName());
		
		Person p2 = allPeople.get(1);
		Assert.assertEquals("Jane", p2.getFirstName());
		Assert.assertEquals("Smith", p2.getLastName());
	}
}
