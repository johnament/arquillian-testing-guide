package com.tad.arquillian.chapter9.test.war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleReference;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.tad.arquillian.chapter9.test.api.StringProvider;

@Path("/resource")
public class RestResource {
	@GET
	@Produces("text/plain")
	public String listServices() throws InvalidSyntaxException {
		BundleContext ctx = getBundleContextFromClass();
		ServiceReference[] refs= ctx.getServiceReferences(StringProvider.class.getCanonicalName(), null);
        StringBuilder sb = new StringBuilder();
        List<String> strings = new ArrayList<String>();
        for(ServiceReference sr : refs) {
        	StringProvider sp = (StringProvider)ctx.getService(sr);
        	strings.add(sp.getString());
        }
        Collections.sort(strings);
        for(String s : strings) {
        	sb.append(s).append("\n");
        }
        return sb.toString();
	}
	
	private BundleContext getBundleContextFromClass() {
        BundleReference bref = (BundleReference) getClass().getClassLoader();
        Bundle bundle = bref.getBundle();
        if (bundle.getState() != Bundle.ACTIVE) {
            try {
                bundle.start();
            } catch (BundleException ex) {
                throw new RuntimeException(ex);
            }
        }
        return bundle.getBundleContext();
    }
}
