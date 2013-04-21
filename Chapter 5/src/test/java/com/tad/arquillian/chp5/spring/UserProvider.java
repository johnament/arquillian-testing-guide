package com.tad.arquillian.chp5.spring;

import java.util.List;

public interface UserProvider {
	public List<User> findAllUsers();
}
