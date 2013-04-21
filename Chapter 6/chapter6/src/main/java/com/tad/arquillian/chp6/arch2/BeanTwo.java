package com.tad.arquillian.chp6.arch2;

import javax.ejb.EJB;

import com.tad.arquillian.chp6.arch1.BeanOne;

public class BeanTwo {
	@EJB(lookup = "java:global/archiveone/BeanOne")
	private BeanOne beanOne;
	
	public String getMessage() {
		return "Bean Two";
	}
}
