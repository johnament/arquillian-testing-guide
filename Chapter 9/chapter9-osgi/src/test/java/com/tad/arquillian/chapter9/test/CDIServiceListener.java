package com.tad.arquillian.chapter9.test;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

@ApplicationScoped
public class CDIServiceListener {
	private BundleContext context;
	private boolean observed = false;

    @Resource
    public void setContext(BundleContext context) {
        this.context = context;
    }

    @PostConstruct
    public void start() {
        ServiceTracker tracker = new ServiceTracker(context, WelcomeService.class.getName(), null) {

            @Override
            public Object addingService(ServiceReference reference) {
            	WelcomeService service = (WelcomeService) super.addingService(reference);
                System.out.println("Adding welcome service: "+service);
                observed=true;
                return service;
            }

            @Override
            public void removedService(ServiceReference reference, Object service) {
                System.out.println("Removing service :"+service);
                super.removedService(reference, service);
            }
        };
        tracker.open();
    }

	public boolean isObserved() {
		return observed;
	}

}
