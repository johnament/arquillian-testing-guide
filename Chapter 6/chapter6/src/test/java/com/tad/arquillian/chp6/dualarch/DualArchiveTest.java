package com.tad.arquillian.chp6.dualarch;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.chp6.arch1.BeanOne;
import com.tad.arquillian.chp6.arch2.BeanTwo;

@Ignore
@RunWith(Arquillian.class)
public class DualArchiveTest {
	@Deployment(order=1,name="archone")
	public static JavaArchive createArchiveOne() {
		return ShrinkWrap.create(JavaArchive.class, "archiveone.jar")
				.addClass(BeanOne.class)
				.addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml");
	}
	
	@Deployment(order=2,name="archtwo")
	public static JavaArchive createArchiveTwo() {
		return ShrinkWrap.create(JavaArchive.class, "archivetwo.jar")
				.addClass(BeanTwo.class)
				.addAsManifestResource(EmptyAsset.INSTANCE,"beans.xml");
	}
	
	@Inject
	@Test
	@OperateOnDeployment("archone")
	public void testArchOne(BeanOne beanOne) {
		Assert.assertEquals("Bean One",beanOne.getMessage());
	}
	
	@Inject
	@Test
	@OperateOnDeployment("archtwo")
	public void testArchTwo(BeanTwo beanTwo) {
		Assert.assertEquals("Bean Two",beanTwo.getMessage());
	}
}
