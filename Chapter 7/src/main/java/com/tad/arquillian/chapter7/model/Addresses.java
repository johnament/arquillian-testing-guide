package com.tad.arquillian.chapter7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="ADDRESSES")
@Entity
public class Addresses {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ADDRESS_ID",precision=10,scale=0)
	private int addressId;
	@Column(name="ADDRESS_LINE_1",length=512)
	private String addressLine1;
	@Column(name="ADDRESS_LINE_2",length=512)
	private String addressLine2;
	@Column(name="CITY",length=512)
	private String city;
	@Column(name="STATE",length=512)
	private String state;
	@Column(name="COUNTRY",length=512)
	private String country;
	@Column(name="COUNTY",length=512)
	private String county;
	@Column(name="POSTAL_CODE",length=10)
	private String postalCode;
	@Column(name="POSTAL_EXTRA",length=10)
	private String postalExtra;
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	private People person;
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPostalExtra() {
		return postalExtra;
	}
	public void setPostalExtra(String postalExtra) {
		this.postalExtra = postalExtra;
	}
	public People getPerson() {
		return person;
	}
	public void setPerson(People person) {
		this.person = person;
	}
}
