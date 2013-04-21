package com.tad.arquillian.chapter9.osgi;

import javax.annotation.Resource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.osgi.spi.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.service.packageadmin.PackageAdmin;


@RunWith(Arquillian.class)
public class ActivatorTest {
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "mytest.jar")
		        .addClasses(WelcomeServiceActivator.class,WelcomeService.class,WelcomeServiceImpl.class,CDIServiceListener.class)
		        .addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml")
		        .setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("mytest.jar")
		                .addBundleManifestVersion(2)
		                .addImportPackages(PackageAdmin.class));
	}
	
	@Resource
	private CDIServiceListener listener;
	
	@Test
	public void testDeployment() throws InterruptedException {
		System.out.println("Observed: "+listener.isObserved());
	}
}
