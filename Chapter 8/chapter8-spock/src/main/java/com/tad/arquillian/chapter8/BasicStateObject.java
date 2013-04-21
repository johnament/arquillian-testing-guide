package com.tad.arquillian.chapter8;

public class BasicStateObject extends Stateful {
	private String name;
	private String display;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public BasicStateObject() {
		
	}
	public BasicStateObject(String name, String display, int state) {
		super();
		this.name = name;
		this.display = display;
		super.state = state;
	}
	
}
