package com.tad.arqdevguide.chp3.delegate;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arqdevguide.chp3.Calculator;
import com.tad.arqdevguide.chp3.HelloWorld;

@RunWith(Arquillian.class)
public class CDIDelegateTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClasses(HelloWorld.class,Calculator.class,CalculatorDelegate.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	Instance<HelloWorld> helloWorldInstance;

	@Test
	public void testCdiInstance() {
		assertFalse(helloWorldInstance.isUnsatisfied());
		
		HelloWorld helloWorld = helloWorldInstance.get();
		HelloWorld helloWorld2 = helloWorldInstance.get();
		
		assertEquals("Hello, World!", helloWorld.getText());
		assert helloWorld != helloWorld2;
	}
	
	@Inject
	Instance<CalculatorDelegate> delegateCalcInstance;
	
	@Test
	public void testDelegateAdd() {
		delegateCalcInstance.get().testAdd();
	}
	@Test
	public void testDelegateSubtract() {
		delegateCalcInstance.get().testSubtract();
	}
}
