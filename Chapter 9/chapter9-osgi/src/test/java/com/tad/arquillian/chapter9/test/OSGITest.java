package com.tad.arquillian.chapter9.test;

import javax.annotation.ManagedBean;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.ServiceTracker;

@RunWith(Arquillian.class)
public class OSGITest {
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class, "mytest.jar")
		        .addClasses(WelcomeServiceActivator.class,WelcomeService.class,WelcomeServiceImpl.class,CDIServiceListener.class)
		        .setManifest(OSGiManifestBuilder.newInstance()
		                .addBundleSymbolicName("mytest.jar")
		                .addBundleManifestVersion(2)
		                .addBundleActivator(WelcomeServiceActivator.class)
		                .addImportPackages(PackageAdmin.class, BundleContext.class, 
		                		ServiceTracker.class, ManagedBean.class, 
		                		ManagementClient.class));
	}

    @ArquillianResource
    PackageAdmin packageAdmin;

    @ArquillianResource
    ManagementClient managementClient;
    
    @ArquillianResource
    BundleContext context;
    
	@Test
	public void testNothing() throws BundleException, InterruptedException {
		ServiceReference sref = context.getServiceReference(WelcomeService.class.getName());
        WelcomeService service = (WelcomeService) context.getService(sref);
        String w = service.welcome("Larry");
        Assert.assertEquals("Welcome, Larry!",w);
	}
}
