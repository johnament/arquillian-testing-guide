package com.tad.arquillian.chapter8;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class StateProgressor {
	public void progressToNextState(Stateful stateful) {
		int currentState = stateful.getState();
		if(currentState < 1) {
			currentState = 1;
		} else if(currentState > 5) {
			currentState = 5;
		}
		int newState = currentState+1;
		if(currentState == 5) {
			newState = 1;
		}
		stateful.setState(newState);
	}
	
	public boolean isValidState(Stateful stateful) {
		return stateful.getState() >= 1 && stateful.getState() <=5;
	}
	
	public List<BasicStateObject> getAllStates() {
		BasicStateObject[] states = {
		new BasicStateObject("State Zero","Zero",0), 
		new BasicStateObject("State Six","Six",6), 
		new BasicStateObject("State One","One",1)};
		return Arrays.asList(states);
	}
}
