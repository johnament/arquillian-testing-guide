package com.tad.arquillian.chapter6.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.api.application.ApplicationDescriptor;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.chp6.client.ConversionController;
import com.tad.arquillian.chp6.convert.TemperatureConverter;

@RunWith(Arquillian.class)
public class ConversionControllerTest {
	@Deployment
	public static Archive<?> createArchiveWithResolver() {
		File[] files = Maven.resolver().loadPomFromFile("pom.xml")
				.resolve("com.tad.arquillian:chapter6").withTransitivity()
				.asFile();
		File ejb = files[0];
		JavaArchive cdi = ShrinkWrap
				.create(JavaArchive.class, "chapter6-cdi.jar")
				.addClass(ConversionController.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		JavaArchive test = ShrinkWrap.create(JavaArchive.class).addClass(
				ConversionControllerTest.class);
		return ShrinkWrap
				.create(EnterpriseArchive.class, "chapter6-ear.ear")
				.addAsModule(ejb,"chapter6.jar")
				.addAsModule(cdi)
				.addAsLibrary(test)
				.addAsManifestResource(createApplicationXml(),
						"application.xml");
	}

	public static Archive<?> createArchiveWithManualBuild() {
		JavaArchive ejb = ShrinkWrap.create(JavaArchive.class, "chapter6.jar")
				.addClass(TemperatureConverter.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		JavaArchive cdi = ShrinkWrap
				.create(JavaArchive.class, "chapter6-cdi.jar")
				.addClass(ConversionController.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		JavaArchive test = ShrinkWrap.create(JavaArchive.class).addClass(
				ConversionControllerTest.class);
		return ShrinkWrap
				.create(EnterpriseArchive.class, "chapter6-ear.ear")
				.addAsModule(ejb)
				.addAsModule(cdi)
				.addAsLibrary(test)
				.addAsManifestResource(createApplicationXml(),
						"application.xml");
	}

	private static StringAsset createApplicationXml() {
		return new StringAsset(Descriptors.create(ApplicationDescriptor.class)
				.version("6").displayName("chapter6-ear")
				.ejbModule("chapter6.jar").ejbModule("chapter6-cdi.jar")
				.exportAsString());
	}

	@Inject
	private Instance<ConversionController> controllerInstance;

	@Test
	public void testConvertedToFareinheit() {
		ConversionController controller = controllerInstance.get();
		double celsius = 10;
		double expectedFarenheit = 50;
		controller.setCelsius(celsius);
		controller.handleConversion();
		assertEquals(expectedFarenheit, controller.getFarenheit(), 0);
	}

	@Test
	public void testConvertedToCelsius() {
		ConversionController controller = controllerInstance.get();
		double expectedCelsius = 20;
		double farenheit = 68;
		controller.setFarenheit(farenheit);
		controller.handleConversion();
		assertEquals(expectedCelsius, controller.getCelsius(), 0);
	}
}
