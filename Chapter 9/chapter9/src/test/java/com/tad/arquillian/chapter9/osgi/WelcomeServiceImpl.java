package com.tad.arquillian.chapter9.osgi;

public class WelcomeServiceImpl implements WelcomeService {

	public String welcome(String person) {
		return String.format("Welcome, %s!",person);
	}

}
