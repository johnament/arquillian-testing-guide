package com.tad.arqdevguide.chp3.cdi;

import java.io.File;
import java.util.Collection;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import static org.junit.Assert.*;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.EffectivePomMavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolutionFilter;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arqdevguide.chp3.HelloWorld;

@RunWith(Arquillian.class)
public class HelloCDITest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class).addClass(HelloWorld.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	BeanManager beanManager;

	@Inject
	HelloWorld helloWorld;

	@Test
	public void testCdiBootstrap() {
		assertNotNull(beanManager);
		assertFalse(beanManager.getBeans(BeanManager.class).isEmpty());
		assertEquals("Hello, World!", helloWorld.getText());
	}
}
