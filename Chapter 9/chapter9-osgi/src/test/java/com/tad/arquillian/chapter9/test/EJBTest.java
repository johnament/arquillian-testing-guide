package com.tad.arquillian.chapter9.test;

import javax.ejb.EJB;
import javax.naming.InitialContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.modules.Module;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleActivator;

@RunWith(Arquillian.class)
public class EJBTest {
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "mytest.jar")
		        .addClasses(WelcomeService.class, WelcomeServiceImpl.class, WelcomeServiceActivator.class, WelcomeProxyBean.class)
		        .addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml")
		        .setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("mytest.jar")
		                .addBundleManifestVersion(2)
		                .addBundleActivator(WelcomeServiceActivator.class)
		                .addImportPackages(BundleActivator.class, Logger.class, Module.class, InitialContext.class)
                .addExportPackages(WelcomeProxyBean.class));
	}
	
	@EJB
	WelcomeProxyBean proxy;
	
	@Test
	public void testViaProxyEJB() {
		String name = "Larry";
		Assert.assertEquals("Welcome, Larry!",proxy.greet(name));
	}
}
