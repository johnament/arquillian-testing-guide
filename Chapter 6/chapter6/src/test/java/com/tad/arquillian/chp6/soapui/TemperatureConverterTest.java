package com.tad.arquillian.chp6.soapui;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.submit.transports.http.support.attachments.WsdlSinglePartHttpResponse;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepResult;
import com.eviware.soapui.model.testsuite.TestAssertion;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.tools.SoapUITestCaseRunner;
import com.tad.arquillian.chp6.convert.TemperatureConverter;

@RunWith(Arquillian.class)
public class TemperatureConverterTest {
	@Deployment//(testable = false)
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "chapter6.jar")
				.addClass(TemperatureConverter.class);
	}

	@ArquillianResource
	private URL serverUrl;
	
	@Test
	@RunAsClient
	public void testConvertUsingSoapUI() throws Exception {
		SoapUITestCaseRunner runner = new SoapUITestCaseRunner();
		runner.setProjectFile("src/test/resources/soapui/TemperatureConverter-soapui-project.xml");
		runner.setHost(String.format("%s:%s",serverUrl.getHost(),serverUrl.getPort()));
		runner.setOutputFolder("target/surefire-reports");
		runner.setJUnitReport(true);
		runner.setPrintReport(true);
		runner.run();
	}
	
}
