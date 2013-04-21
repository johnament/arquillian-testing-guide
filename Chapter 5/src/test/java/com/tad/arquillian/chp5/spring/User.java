package com.tad.arquillian.chp5.spring;

public class User {
	private String username;
	private String realname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public User() {
	}
	public User(String username, String realname) {
		super();
		this.username = username;
		this.realname = realname;
	}
	
}
