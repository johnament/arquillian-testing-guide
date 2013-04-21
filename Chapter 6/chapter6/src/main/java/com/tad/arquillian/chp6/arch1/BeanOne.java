package com.tad.arquillian.chp6.arch1;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class BeanOne {
	public String getMessage() {
		return "Bean One";
	}
}
