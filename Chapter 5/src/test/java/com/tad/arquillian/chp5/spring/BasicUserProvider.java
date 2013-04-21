package com.tad.arquillian.chp5.spring;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BasicUserProvider implements UserProvider {
	@Override
	public List<User> findAllUsers() {
		User[] users = new User[]{ new User("booboo","Boo Boo"), new User("foo","Foo Bar") };
		return Arrays.asList(users);
	}
}
