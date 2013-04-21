package com.tad.arquillian.chapter9.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;


/**
 * @author JohnAment
 *
 */
public class DeclarativeServicesSupport extends RepositorySupport {

    public static final String APACHE_FELIX_SCR = "org.apache.felix:org.apache.felix.scr";

    public static void provideDeclarativeServices(BundleContext syscontext, Bundle bundle) throws BundleException {
        if (syscontext.getServiceReference("org.apache.felix.scr.ScrService") == null) {
            installSupportBundle(syscontext, getCoordinates(bundle, APACHE_FELIX_SCR)).start();
        }
    }
}