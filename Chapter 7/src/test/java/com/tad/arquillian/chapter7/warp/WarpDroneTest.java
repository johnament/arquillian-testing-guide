package com.tad.arquillian.chapter7.warp;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

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
import org.jboss.arquillian.warp.impl.client.filter.http.DefaultHttpFilterBuilder;
import org.jboss.arquillian.warp.jsf.AfterPhase;
import org.jboss.arquillian.warp.jsf.Phase;
import org.jboss.arquillian.warp.servlet.AfterServlet;
import org.jboss.arquillian.warp.servlet.BeforeServlet;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.tad.arquillian.chapter7.DeploymentUtils;
import com.tad.arquillian.chapter7.model.People;
import com.tad.arquillian.chp7.dao.PeopleDAO;


/**
 * @author JohnAment
 *
 */
@WarpTest
@RunWith(Arquillian.class)
@RunAsClient
public class WarpDroneTest {
    @ArquillianResource
    URL url;
 
    @Drone
    WebDriver driver;
    
    @Deployment(testable=true)
    public static WebArchive createChapter7Archive() {
        return DeploymentUtils.createChapter7Archive();
    }
    
    @Test
    public void test() {
        final String baseUrl = String.format("http://%s:%s",url.getHost(),url.getPort());
        driver.get(baseUrl + "/chapter7/people.jsf");
        driver.findElement(By.linkText("Add")).click();
        new Select(driver.findElement(By.id("editPersonForm:salutation"))).selectByVisibleText("Mrs.");
        driver.findElement(By.id("editPersonForm:email")).clear();
        driver.findElement(By.id("editPersonForm:email")).sendKeys("jane.doe@anyplace.com");
        driver.findElement(By.id("editPersonForm:firstName")).clear();
        driver.findElement(By.id("editPersonForm:firstName")).sendKeys("Jane");
        driver.findElement(By.id("editPersonForm:lastName")).clear();
        driver.findElement(By.id("editPersonForm:lastName")).sendKeys("Doe");
        Warp.initiate(new Activity(){
            @Override
            public void perform() {
                driver.findElement(By.name("editPersonForm:saveButton")).click();
            }
        })
        .inspect(new Inspection(){
            private static final long serialVersionUID = 1L;
            @EJB
            private PeopleDAO dao;

            @AfterPhase(Phase.RENDER_RESPONSE)
            public void testAfterRenderResponse() {
                List<People> people = dao.findAllPeople();
                boolean found = false;
                for (People p : people) {
                    if (p.getEmail().equals("jane.doe@anyplace.com")) {
                        found = true;
                    }
                }
                assertTrue(found);
            }
        });
    }
    
    private String janeDoeContent;
    private String notValidContent;
    
    @Test
    public void testWithGroups() {
        final String baseUrl = String.format("http://%s:%s",url.getHost(),url.getPort());
        Warp
        .initiate(new Activity(){
            String baseRest = baseUrl + "/chapter7/rest/people/email/";
            @Override
            public void perform() {
                driver.get(baseRest+"jane.doe@anyplace.com");
                janeDoeContent = driver.getPageSource();
                driver.get(baseRest+"not.a.valid@email.net");
                notValidContent = driver.getPageSource();
            }
        })
        .group()
        .observe(new DefaultHttpFilterBuilder().uri().contains("jane.doe"))
        .inspect(new Inspection(){
            private static final long serialVersionUID = 1L;
            @AfterServlet
            public void testAfterServlet() throws IOException {
                assertEquals("Ms. Jane Doe",janeDoeContent);
            }
        })
        .group()
        .observe(new DefaultHttpFilterBuilder().uri().contains("not.a.valid"))
        .inspect(new Inspection(){
            private static final long serialVersionUID = 1L;
            @AfterServlet
            public void testAfterServlet() throws IOException {
                assertEquals("none found",notValidContent);
            }
        });
    }
}
