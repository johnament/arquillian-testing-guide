package com.tad.arquillian.chapter8.springmvc;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.Activity;
import org.jboss.arquillian.warp.Inspection;
import org.jboss.arquillian.warp.RequestObserver;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.WarpTest;
import org.jboss.arquillian.warp.extension.spring.SpringMvcResource;
import org.jboss.arquillian.warp.servlet.AfterServlet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tad.arquillian.chapter8.springmvc.controller.LoginController;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

@WarpTest
@RunWith(Arquillian.class)
@RunAsClient
public class SpringWarpTest {
	@ArquillianResource
	URL baseUrl;

	@Drone
	WebDriver driver;

	@Deployment
	public static WebArchive createDeployment() {
		File[] libs = Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.resolve("org.springframework:spring-webmvc:3.1.1.RELEASE",
						"org.hibernate:hibernate-validator:4.2.0.Final")
				.withTransitivity().asFile();

		return ShrinkWrap
				.create(WebArchive.class, "chapter8-springmvc.war")
				.addPackage(LoginController.class.getPackage())
				.addAsWebInfResource("WEB-INF/web.xml", "web.xml")
				.addAsResource("applicationContext.xml",
						"applicationContext.xml")
				.addAsResource("views/login.jsp",
						"views/login.jsp")
				.addAsLibraries(libs);
	}
	
	@Test
	public void testInvalidSubmission() { 
		Warp.initiate(new LoginActivity("not-an-email",""))
        .observe(new RequestObserver(){})
        .inspect(new ErrorInspection(2));
	}
	
	@Test
	public void testValidLogin() {
		Warp.initiate(new LoginActivity("admin@admin.com","admin1"))
        .observe(new RequestObserver(){})
        .inspect(new ErrorInspection(0));
	}
	
	private class ErrorInspection extends Inspection {
        private static final long serialVersionUID = 1L;
        private int numberOfErrors;
        public ErrorInspection(int numberOfErrors) {
        	this.numberOfErrors = numberOfErrors;
        }
        @SpringMvcResource
        private ModelAndView modelAndView;

        @SpringMvcResource
        private Errors errors;

        @AfterServlet
        public void testAfterServlet() {
            assertEquals(numberOfErrors,errors.getErrorCount());
        }
	}
	
	private class LoginActivity implements Activity {
		private String username;
		private String password;
		public LoginActivity(String username, String password) {
			this.username = username;
			this.password = password;
		}
		@Override
        public void perform() {
            driver.get(baseUrl + "/chapter8-springmvc/");
            driver.findElement(By.name("username")).clear();
            driver.findElement(By.name("username")).sendKeys(username);
            driver.findElement(By.name("password")).clear();
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("Login")).click();    
        }
	}
}
