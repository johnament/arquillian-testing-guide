package com.tad.arquillian.chapter9.osgi;

import static org.junit.Assert.assertNotNull;

import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.osgi.resolver.v2.XRequirementBuilder;
import org.jboss.osgi.spi.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.resource.Resource;
import org.osgi.service.repository.Repository;
import org.osgi.util.tracker.ServiceTracker;

@RunWith(Arquillian.class)
public class OSGITest {
    @Inject
    public BundleContext context;

    @Inject
    public Bundle bundle;
    
	@Deployment
	public static JavaArchive createDeployment() {
		String osgiServices = "OSGI-INF/sample.xml";
		String archiveName = "example-archive";
		return ShrinkWrap.create(JavaArchive.class, archiveName)
        .addClasses(WelcomeService.class, WelcomeServiceImpl.class, DeclarativeServicesSupport.class, RepositorySupport.class)
        .addAsResource(osgiServices)
        .addAsManifestResource(RepositorySupport.BUNDLE_VERSIONS_FILE)
        .setManifest(OSGiManifestBuilder.newInstance()
                .addBundleSymbolicName(archiveName)
                .addBundleManifestVersion(2)
                .addImportPackages(ServiceTracker.class,XRequirementBuilder.class, Repository.class, Resource.class)
                .addManifestHeader("Service-Component", osgiServices));
	}

	@Test
	public void testInjected() throws BundleException, InterruptedException, TimeoutException {
	    bundle.start();
	    // Provide Declarative Services support
        DeclarativeServicesSupport.provideDeclarativeServices(context, bundle);
	}
	
	@Test
	public void testUsingWelcomeService() {
        ServiceReference sref = context.getServiceReference(WelcomeService.class.getName());
        WelcomeService service = (WelcomeService) context.getService(sref);
        Assert.assertEquals("Welcome, Harry!",service.welcome("Harry"));
	}
}
