package com.tad.arquillian.chp5.test.spring;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import com.tad.arquillian.chp5.spring.BasicUserProvider;
import com.tad.arquillian.chp5.spring.User;
import com.tad.arquillian.chp5.spring.UserProvider;

public class SpringTestUtils {
	public static File[] getSpringDependencies() {
		return Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.resolve("cglib:cglib", "org.springframework:spring-context",
						"org.springframework:spring-web")
				.withMavenCentralRepo(false).withTransitivity().as(File.class);
	}

	public static WebArchive createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(User.class, BasicUserProvider.class,
						UserProvider.class)
				.addAsLibraries(getSpringDependencies());
	}
}
