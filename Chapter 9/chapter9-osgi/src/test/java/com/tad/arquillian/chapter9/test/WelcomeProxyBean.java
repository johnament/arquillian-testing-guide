package com.tad.arquillian.chapter9.test;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

@Stateless
@LocalBean
public class WelcomeProxyBean {
	@Resource
	BundleContext context;
	
	public String greet(String name) {
		ServiceReference sref = context.getServiceReference(WelcomeService.class.getName());
        WelcomeService service = (WelcomeService) context.getService(sref);
        return service.welcome(name);
	}
}
