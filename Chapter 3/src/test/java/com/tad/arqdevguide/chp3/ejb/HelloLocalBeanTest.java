package com.tad.arqdevguide.chp3.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arqdevguide.chp3.HelloLocalBean;

@RunWith(Arquillian.class)
public class HelloLocalBeanTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class)
				.addClass(HelloLocalBean.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "ejb-jar.xml");
	}

	@EJB
	HelloLocalBean helloWorld;

	@Test
	public void testEJBBootstrap() {
		assertNotNull(helloWorld);
		assertEquals("Hello, World!", helloWorld.getText());
	}
}
