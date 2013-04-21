package com.tad.arquillian.chapter8;

public abstract class Stateful {
	protected int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
