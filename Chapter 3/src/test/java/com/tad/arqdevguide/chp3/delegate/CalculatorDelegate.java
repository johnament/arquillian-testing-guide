package com.tad.arqdevguide.chp3.delegate;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.junit.Assert;

import com.tad.arqdevguide.chp3.Calculator;

public class CalculatorDelegate {
	@Inject
	Instance<Calculator> calculatorInstance;
	
	public void testAdd() {
		Calculator calc = calculatorInstance.get();
		int expected = 5;
		int actual = calc.add(3, 2);
		Assert.assertEquals(expected,actual);
	}
	
	public void testSubtract() {
		Calculator calc = calculatorInstance.get();
		int expected = 3;
		int actual = calc.subtract(10,7);
		Assert.assertEquals(expected,actual);
	}
}
