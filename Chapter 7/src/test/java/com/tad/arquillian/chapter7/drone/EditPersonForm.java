package com.tad.arquillian.chapter7.drone;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


/**
 * @author JohnAment
 *
 */
public class EditPersonForm {
    private static final String EMAIl = "editPersonForm:email";
    private static final String FIRST_NAME = "editPersonForm:firstName";
    private static final String LAST_NAME = "editPersonForm:lastName";
    private static final String SALUTATION = "editPersonForm:salutation";
    private WebDriver driver;
    private URL url;
    public EditPersonForm(WebDriver driver,URL url) {
        this.driver = driver;
        this.url = url;
        navigate();
    }
    private void navigate() {
        String baseUrl = String.format("http://%s:%s",url.getHost(),url.getPort());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/chapter7/people.jsf");
    }
    private void select(String field, String value) {
        new Select(driver.findElement(By.id(field))).selectByVisibleText(value);
    }
    private void setFieldValue(String field, String value) {
        WebElement el = driver.findElement(By.id(field));
        el.clear();
        el.sendKeys(value);
    }
    public void setEmail(String email) {
        setFieldValue(EMAIl,email);
    }
    public void setFirstName(String firstName) {
        setFieldValue(FIRST_NAME,firstName);
    }
    public void setLastName(String lastName) {
        setFieldValue(LAST_NAME,lastName);
    }
    public void setSalutation(String salutation) {
        select(SALUTATION,salutation);
    }
    public void clickAdd() {
        driver.findElement(By.linkText("Add")).click();
    }
    public void clickSave() {
        driver.findElement(By.name("editPersonForm:saveButton")).click();
    }
}
