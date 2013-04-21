package com.tad.arquillian.chp6;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.chp6.convert.TemperatureConverter;

@RunWith(Arquillian.class)
public class TemperatureEJBTest {
	@Deployment//(testable = false)
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClass(TemperatureConverter.class);
	}
	
	@EJB(lookup = "java:global/test/TemperatureConverter!com.tad.arquillian.chp6.convert.TemperatureConverter")
	private TemperatureConverter converter;
	
	@Test
	public void testFarenheit() {
		converter.convertToCelsius(10);
	}
	
	@Test
	public void testCelsius() {
		converter.convertToFarenheit(10);
	}
}
