package com.tad.arquillian.chp6.dualarch;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.chp6.arch1.BeanOne;
import com.tad.arquillian.chp6.arch2.BeanTwo;

@RunWith(Arquillian.class)
public class EJBDualArchiveTest {
	@Deployment(order = 1, name = "archone")
	public static JavaArchive createArchiveOne() {
		return ShrinkWrap.create(JavaArchive.class, "archiveone.jar")
				.addClass(BeanOne.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Deployment(order = 5, name = "myarchive.jar")
	public static JavaArchive importMyDependency() throws Exception {
		File f = Maven.resolver().loadPomFromFile("pom.xml")
				.resolve("com.mycompany:myarchive").withTransitivity()
				.asSingleFile();
		return ShrinkWrap.createFromZipFile(JavaArchive.class, f);
	}

	@Deployment(order = 2, name = "archtwo")
	public static JavaArchive createArchiveTwo() {
		return ShrinkWrap
				.create(JavaArchive.class, "archivetwo.jar")
				.addClass(BeanTwo.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource(createDeploymentAsset(),
						"jboss-deployment-structure.xml");
	}

	private static StringAsset createDeploymentAsset() {
		StringBuilder sb = new StringBuilder();
		sb.append("<jboss-deployment-structure xmlns=\"urn:jboss:deployment-structure:1.1\">");
		sb.append("<deployment><dependencies>");
		sb.append("<module name=\"deployment.archiveone.jar\" />");
		sb.append("</dependencies></deployment></jboss-deployment-structure>");
		return new StringAsset(sb.toString());
	}

	@Inject
	private BeanTwo beanTwo;

	@Test
	@OperateOnDeployment("archtwo")
	public void testArchTwo() {
		Assert.assertEquals("Bean Two", beanTwo.getMessage());
	}

}