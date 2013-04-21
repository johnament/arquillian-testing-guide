package com.tad.arqdevguide.chp3.servlet;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.EffectivePomMavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arqdevguide.chp3.HelloServlet;

@RunWith(Arquillian.class)
public class HelloServletTest {
	
	public static Archive<?> createTestArchiveAllFiles() {
		EffectivePomMavenDependencyResolver resolver = DependencyResolvers.use(
				MavenDependencyResolver.class).loadEffectivePom("pom.xml");
		File[] files = resolver.importAllDependencies().resolveAsFiles();
		File[] webinfs = new File("src/main/webapp/WEB-INF").listFiles();
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war")
				.addClass(HelloServlet.class);
		if (webinfs != null) {
			for (File f : webinfs) {
				if (f.isFile())
					wa.addAsWebInfResource(f);
			}
		}
		wa.addAsLibraries(files);
		return wa;
	}

	@Deployment
	public static Archive<?> createTestArchiveProperties() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war")
				.addClass(HelloServlet.class).addAsWebInfResource("web.xml");
		Properties props = System.getProperties();
		if (props.containsKey("jettyMode")) {
			System.out.println("We're in jetty mode.");
			wa.addAsWebInfResource("jetty-web.xml")
			.addAsWebInfResource("jetty-env.xml");
		} else {
			System.out.println("Not in jetty mode.");
		}
		return wa;
	}

	@Test
	public void testGetText() throws Exception {
		URL url = new URL("http://localhost:8080/test/hello");
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String result = br.readLine();
		String expected = "Hello, World!";
		assertEquals(expected, result);
	}
}
