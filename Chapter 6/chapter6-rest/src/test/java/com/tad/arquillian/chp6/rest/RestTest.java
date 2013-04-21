package com.tad.arquillian.chp6.rest;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eviware.soapui.tools.SoapUITestCaseRunner;

@RunWith(Arquillian.class)
public class RestTest {
	@Deployment
	public static Archive<?> createArchiveWithResolver() {
		return ShrinkWrap.create(WebArchive.class, "chapter6-rest.war")
				.addClasses(JaxRsActivator.class, TemperatureConverter.class);
	}

	@ArquillianResource
	private URL serverUrl;

	@Test
	@RunAsClient
	public void testConvertUsingSoapUI() throws Exception {
		SoapUITestCaseRunner runner = new SoapUITestCaseRunner();
		runner.setProjectFile("src/test/resources/soapui/TemperatureConverter-soapui-project.xml");
		runner.setHost(String.format("%s:%s", serverUrl.getHost(),
				serverUrl.getPort()));
		runner.setOutputFolder("target/surefire-reports");
		runner.setJUnitReport(true);
		runner.setPrintReport(true);
		runner.run();
	}

}
