package com.tad.arquillian.chapter9.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class WelcomeServiceActivator implements BundleActivator {

	private ServiceRegistration registration;
	
	public void start(BundleContext context) throws Exception {
		context.registerService(WelcomeService.class.getName(), new WelcomeServiceImpl(), null);
	}

	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}
