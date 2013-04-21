package com.tad.arqdevguide.chp3;

import javax.enterprise.context.Dependent;

@Dependent
public class Calculator {
	public int add(int i,int j) {
		return i+j;
	}
	public int subtract(int i,int j) {
		return i - j;
	}
}
