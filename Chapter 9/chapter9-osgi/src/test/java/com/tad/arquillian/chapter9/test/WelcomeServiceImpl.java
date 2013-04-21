package com.tad.arquillian.chapter9.test;

public class WelcomeServiceImpl implements WelcomeService {

	public String welcome(String person) {
		return String.format("Welcome, %s!",person);
	}

}
