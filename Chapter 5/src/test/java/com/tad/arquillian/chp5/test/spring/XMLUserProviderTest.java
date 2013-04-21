package com.tad.arquillian.chp5.test.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.spring.integration.test.annotation.SpringConfiguration;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.tad.arquillian.chp5.spring.User;
import com.tad.arquillian.chp5.spring.UserProvider;

@RunWith(Arquillian.class)
@SpringConfiguration({ "test-userProviderContext.xml" })
public class XMLUserProviderTest {
	@Deployment
	public static WebArchive createTestArchive() {
		return SpringTestUtils.createTestArchive().addAsResource(
				"test-userProviderContext.xml");
	}
	@Autowired
	private UserProvider userProvider;
	@Test
	public void testInjection() {
		assertNotNull(userProvider);
	}
	@Test
	public void testUsersContent() {
		List<User> users = userProvider.findAllUsers();
		assertEquals(2, users.size());
	}
}
