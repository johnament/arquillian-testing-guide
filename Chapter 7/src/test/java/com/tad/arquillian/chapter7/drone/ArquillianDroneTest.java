/**
 * Copyright 2012 Burlington Coat Factory
 */
package com.tad.arquillian.chapter7.drone;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.tad.arquillian.chapter7.DeploymentUtils;


/**
 * @author JohnAment
 *
 */
@RunWith(Arquillian.class)
public class ArquillianDroneTest {
    @ArquillianResource
    private URL url;
 
    @Drone
    private WebDriver driver;
    
    @Deployment(testable=false)
    public static WebArchive createChapter7Archive() {
        return DeploymentUtils.createChapter7Archive();
    }
    
    @Test
    @RunAsClient
    public void testSomething() {
        String baseUrl = String.format("http://%s:%s",url.getHost(),url.getPort());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/chapter7/people.jsf");
        driver.findElement(By.linkText("Add")).click();
        new Select(driver.findElement(By.id("editPersonForm:salutation"))).selectByVisibleText("Dr.");
        driver.findElement(By.id("editPersonForm:email")).clear();
        driver.findElement(By.id("editPersonForm:email")).sendKeys("dr.killjoy@cnn.net");
        driver.findElement(By.id("editPersonForm:firstName")).clear();
        driver.findElement(By.id("editPersonForm:firstName")).sendKeys("David");
        driver.findElement(By.id("editPersonForm:lastName")).clear();
        driver.findElement(By.id("editPersonForm:lastName")).sendKeys("Killjoy");
        driver.findElement(By.name("editPersonForm:saveButton")).click();
        driver.quit();
    }
    
    @Test
    @RunAsClient
    public void testViaDelegate() {
        EditPersonForm epf = new EditPersonForm(driver,url);
        epf.clickAdd();
        epf.setSalutation("Dr.");
        epf.setEmail("dr.killjoy@cnn.net");
        epf.setFirstName("David");
        epf.setLastName("Killjoy");
        epf.clickSave();
    }
}
