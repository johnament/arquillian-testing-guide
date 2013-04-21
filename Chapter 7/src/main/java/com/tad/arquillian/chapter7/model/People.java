package com.tad.arquillian.chapter7.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

@Entity
@XmlRootElement
@Table(name="PEOPLE")
@XmlAccessorType(XmlAccessType.FIELD)
public class People {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PERSON_ID",precision=10,scale=0)
	private int personId;
	@Column(name="FIRST_NAME",length=255)
	private String firstName;
	@Column(name="LAST_NAME",length=255)
	private String lastName;
	@Column(name="SALUTATION",length=10)
	private String salutation;
	@Column(name="EMAIL",length=512)
	private String email;
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
