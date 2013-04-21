package com.tad.arquillian.chapter10;

import java.io.File;
import java.io.InputStream;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence20.PersistenceUnit;
import org.jboss.shrinkwrap.descriptor.api.persistence20.Properties;
import org.jboss.shrinkwrap.descriptor.api.persistence20.Property;
import org.jboss.shrinkwrap.descriptor.api.webapp30.WebAppDescriptor;
import org.jboss.shrinkwrap.resolver.api.ResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

import org.junit.Ignore;
import org.junit.Test;

import com.tad.arquillian.chapter10.foo.FooBar;

/**
 * @author JohnAment
 * 
 */
public class ShrinkWrapTest {

    @Test
    @Ignore
    public void showArchives() throws InterruptedException {
        JavaArchive ja = ShrinkWrap.create(JavaArchive.class, "myejb.jar")
                .addClasses(MyLocalBean.class);
        ja.addAsResource(new FileAsset(
                new File("src/test/resources/myfile.txt")), "myfile.txt");
        WebArchive wa = ShrinkWrap
                .create(WebArchive.class, "chapter6-rest.war")
                .addClasses(JaxRsActivator.class, TemperatureConverter.class);
        wa.addAsResource("myfile.txt");
        System.out.println(ja.toString(true));
        System.out.println(wa.toString(true));
    }

    public File[] getWebInfLibs() {
        File dir = new File("target/my-app/WEB-INF/lib");
        return dir.listFiles();
    }

    @Test
    @Ignore
    public void loadDependencies() {
        PomEquippedResolveStage mavenResolver = Maven.resolver().offline()
                .loadPomFromFile("pom.xml");
        mavenResolver.importRuntimeAndTestDependencies().asFile();
        mavenResolver.importRuntimeDependencies().asFile();
        File[] libs = mavenResolver.resolve("com.mycompany:foo-jar")
                .withTransitivity().asFile();
        WebArchive wa = ShrinkWrap
                .create(WebArchive.class, "chapter6-rest.war")
                .addClasses(JaxRsActivator.class, TemperatureConverter.class);
        wa.addAsLibraries(libs);
    }

    @Ignore
    public void createEARFile() {
        EnterpriseArchive ea = ShrinkWrap.create(EnterpriseArchive.class,
                "foo-ear.ear");
        PomEquippedResolveStage mavenResolver = Maven.resolver().offline()
                .loadPomFromFile("pom.xml");
        JavaArchive ejbJar = mavenResolver.resolve("com.mycompany:foo-ejb")
                .withoutTransitivity().as(JavaArchive.class)[0];
        WebArchive warFile = mavenResolver.resolve("com.mycompany:foo-ui")
                .withoutTransitivity().as(WebArchive.class)[0];
        JavaArchive lib = mavenResolver.resolve("com.mycompany:foo-lib")
                .withoutTransitivity().as(JavaArchive.class)[0];
        ea.addAsLibraries(lib);
        ea.addAsModule(ejbJar);
        ea.addAsModule(warFile);
    }

    public void importFileTest() {
        JavaArchive ja = ShrinkWrap.createFromZipFile(JavaArchive.class,
                new File("target/myfile.jar"));
    }

    @Test
    @Ignore
    public void createDescriptorBeans() {
        BeansDescriptor beans = Descriptors.create(BeansDescriptor.class);
        beans.getOrCreateInterceptors()
                .clazz("com.mycompany.cdi.MyInterceptor");
        StringAsset beansXml = new StringAsset(beans.exportAsString());
        System.out.println(beansXml.getSource());
        JavaArchive ja = ShrinkWrap.create(JavaArchive.class);
        ja.addAsManifestResource(beansXml, "beans.xml");
    }

    @Test
    @Ignore
    public void importPersistenceDescriptor() {
        PersistenceDescriptor pd = Descriptors.importAs(
                PersistenceDescriptor.class).fromFile(
                "src/test/resources/META-INF/persistence.xml");
        for (PersistenceUnit<PersistenceDescriptor> unit : pd
                .getAllPersistenceUnit()) {
            for (Property<Properties<PersistenceUnit<PersistenceDescriptor>>> prop : unit
                    .getOrCreateProperties().getAllProperty()) {
                if (prop.getName().equalsIgnoreCase("hibernate.hbm2ddl.auto")) {
                    prop.value("create-drop");
                }
            }
        }
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class);
        System.out.println(pd.exportAsString());
        jar.addAsManifestResource(new StringAsset(pd.exportAsString()),
                "persistence.xml");
        System.out.println(jar.toString(true));
    }

    @Test
    public void createComplexArchive() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,
                "ComplexExample.war");
        PomEquippedResolveStage mavenResolver = Maven.resolver().offline()
                .loadPomFromFile("pom.xml");
        File[] libs = mavenResolver
                .resolve("org.apache.commons:commons-email",
                        "commons-lang:commons-lang").withTransitivity()
                .asFile();
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "foobar.jar")
                .addPackage(FooBar.class.getPackage());
        war.addAsLibraries(libs);
        war.addAsLibraries(jar);
        war.addAsWebResource("web/index.html");
        war.addAsWebResource("web/jquery.js");
        WebAppDescriptor web = Descriptors.create(WebAppDescriptor.class)
                .description("A Complex Webapp Example")
                .displayName("ComplexExample").getOrCreateWelcomeFileList()
                .welcomeFile("index.html").up().distributable();
        String webXml = web.exportAsString();
        System.out.println("web.xml: " + webXml);
        war.addAsWebInfResource(new StringAsset(webXml), "web.xml");
        System.out.println(war.toString(true));
    }
}
