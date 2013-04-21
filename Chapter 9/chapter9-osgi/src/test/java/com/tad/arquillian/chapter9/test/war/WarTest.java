package com.tad.arquillian.chapter9.test.war;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.annotation.Resource;
import javax.ws.rs.core.Application;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleActivator;
import org.osgi.util.tracker.ServiceTracker;

import com.tad.arquillian.chapter9.test.api.StringProvider;
import com.tad.arquillian.chapter9.test.str1.StringOne;
import com.tad.arquillian.chapter9.test.str1.StringOneActivator;
import com.tad.arquillian.chapter9.test.str2.StringTwo;
import com.tad.arquillian.chapter9.test.str2.StringTwoActivator;

@RunWith(Arquillian.class)
public class WarTest {
	@Deployment(testable=false)
	public static EnterpriseArchive createDeployment() {
		JavaArchive api = ShrinkWrap.create(JavaArchive.class,"api.jar")
				.addClass(StringProvider.class)
				.setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("api.jar")
		                .addBundleManifestVersion(2)
		                .addExportPackages(StringProvider.class));
		JavaArchive one = ShrinkWrap.create(JavaArchive.class,"one.jar")
				.addClasses(StringOne.class,StringOneActivator.class)
				.setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("one.jar")
		                .addBundleManifestVersion(2)
		                .addBundleActivator(StringOneActivator.class)
		                .addExportPackages(StringOne.class)
		                .addImportPackages(BundleActivator.class,StringProvider.class));
		JavaArchive two = ShrinkWrap.create(JavaArchive.class,"two.jar")
				.addClasses(StringTwo.class,StringTwoActivator.class)
				.setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("two.jar")
		                .addBundleManifestVersion(2)
		                .addBundleActivator(StringTwoActivator.class)
		                .addExportPackages(StringTwo.class)
		                .addImportPackages(BundleActivator.class,StringProvider.class));
		
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "war-test.war")
		        .addClasses(RestApplication.class,RestResource.class)
		        .setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("war-test.war")
		                .addBundleManifestVersion(2)
		                .addImportPackages(BundleActivator.class,StringProvider.class,ServiceTracker.class,Application.class)
		                .addBundleClasspath("WEB-INF/classes"));
		EnterpriseArchive ea = ShrinkWrap.create(EnterpriseArchive.class,"test.ear")
				.addAsModule(api).addAsModule(one).addAsModule(two).addAsModule(wa);
		return ea;
	}
	
	@ArquillianResource
	private URL baseUrl;
	
	@Test
	@RunAsClient
	public void testGetResource() throws Exception {
		URL url = new URL(baseUrl,"/war-test/rest/resource");
		InputStream is = url.openStream();
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		while((line = br.readLine()) != null) {
			sb.append(line).append("\n");
		}
		Assert.assertEquals(StringTwo.class.getSimpleName()+"\n"+StringOne.class.getCanonicalName()+"\n",sb.toString());
	}
}
