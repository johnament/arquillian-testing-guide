package com.tad.arquillian.chapter7.warp;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.tad.arquillian.chapter7.DeploymentUtils;
import com.tad.arquillian.chapter7.model.Addresses;
import com.tad.arquillian.chapter7.model.People;
import com.tad.arquillian.chp7.controller.PeopleController;
import com.tad.arquillian.chp7.dao.PeopleDAO;

/**
 * @author JohnAment
 *
 */
@Ignore
@RunWith(Arquillian.class)
public class Selenium2WebDriverTest {
    @Deployment(testable=false)
    public static WebArchive createChapter7Archive() {
        return DeploymentUtils.createChapter7Archive();
    }

    @ArquillianResource
    private URL url;
    
    @Test
    @RunAsClient
    public void testSelenium2WebDriver() throws Exception {
        String baseUrl = String.format("http://%s:%s",url.getHost(),url.getPort());
        WebDriver driver = new FirefoxDriver();
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
}
