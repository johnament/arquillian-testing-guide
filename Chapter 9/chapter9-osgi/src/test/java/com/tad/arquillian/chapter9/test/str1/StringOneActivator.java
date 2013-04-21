package com.tad.arquillian.chapter9.test.str1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.tad.arquillian.chapter9.test.api.StringProvider;

public class StringOneActivator implements BundleActivator {
	private ServiceRegistration registration;
	public void start(BundleContext context) throws Exception {
		context.registerService(StringProvider.class.getName(),
				new StringOne(), null);
	}
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}
}
