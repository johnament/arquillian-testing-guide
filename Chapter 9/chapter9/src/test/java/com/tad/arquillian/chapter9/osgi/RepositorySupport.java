package com.tad.arquillian.chapter9.osgi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import org.jboss.osgi.resolver.v2.MavenCoordinates;
import org.jboss.osgi.resolver.v2.XRequirementBuilder;
import org.jboss.osgi.resolver.v2.XResource;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.resource.Capability;
import org.osgi.framework.resource.Requirement;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.service.repository.Repository;


/**
 * @author JohnAment
 *
 */
public class RepositorySupport {

    public static final String BUNDLE_VERSIONS_FILE = "3rdparty-bundle.versions";

    public static Repository getRepository(BundleContext context) {
        ServiceReference sref = context.getServiceReference(Repository.class.getName());
        return (Repository) context.getService(sref);
    }

    public static PackageAdmin getPackageAdmin(BundleContext syscontext) {
        ServiceReference sref = syscontext.getServiceReference(PackageAdmin.class.getName());
        return (PackageAdmin) syscontext.getService(sref);
    }

    public static Bundle installSupportBundle(BundleContext context, String coordinates) throws BundleException {
        Repository repository = getRepository(context);
        Requirement req = XRequirementBuilder.createArtifactRequirement(MavenCoordinates.parse(coordinates));
        Collection<Capability> caps = repository.findProviders(req);
        if (caps.isEmpty())
            throw new IllegalStateException("Cannot find capability for: " + req);
        Capability cap = caps.iterator().next();
        XResource xres = (XResource) cap.getResource();
        return context.installBundle(coordinates, xres.getContent());
    }

    public static String getCoordinates(Bundle bundle, String artifactid) {
        Properties props = new Properties();
        URL entry = bundle.getEntry("META-INF/" + BUNDLE_VERSIONS_FILE);
        if (entry == null)
            throw new IllegalStateException("Cannot find resource: META-INF/" + BUNDLE_VERSIONS_FILE);
        try {
            InputStream input = entry.openStream();
            props.load(input);
            input.close();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return artifactid + ":" + props.getProperty(artifactid);
    }
}
