package com.tad.arquillian.chapter7.graphene;

import static org.jboss.arquillian.ajocado.Graphene.id;
import static org.jboss.arquillian.ajocado.Graphene.jq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.ajocado.dom.Attribute;
import org.jboss.arquillian.ajocado.framework.GrapheneSelenium;
import org.jboss.arquillian.ajocado.javascript.JavaScript;
import org.jboss.arquillian.ajocado.locator.JQueryLocator;
import org.jboss.arquillian.ajocado.network.NetworkTraffic;
import org.jboss.arquillian.ajocado.network.NetworkTrafficType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.chapter7.DeploymentUtils;

/**
 * @author JohnAment
 * 
 */
@RunWith(Arquillian.class)
public class GrapheneTest {

    @ArquillianResource
    private URL              url;

    @Deployment(testable = false)
    public static WebArchive createChapter7Archive() {
        return DeploymentUtils.createChapter7Archive();
    }

    @Drone
    private GrapheneSelenium driver;
    
    @Test
    public void testShowPeople() throws MalformedURLException {
        driver.open(new URL(url,"/chapter7/graphenePeople.html"));
        JQueryLocator myTable = jq("#peopleList");
        JQueryLocator rows = myTable.getChild(jq("tr"));
        int count =driver.getCount(rows);
        assertEquals("Wrong count",1,count);
    }
    
    @Test
    public void testClickEdit() throws Exception {
        driver.open(new URL(url,"/chapter7/graphenePeople.html"));
        driver.click(jq("#edit1"));
        assertEquals("1",driver.getValue(jq("#personId")));
        assertEquals("John",driver.getValue(jq("#firstName")));
        assertEquals("Bob",driver.getValue(jq("#lastName")));
        assertEquals("john@nowhere.net",driver.getValue(jq("#email")));
    }
    
    @Test
    public void testClickEditAndChange() throws Exception {
        driver.open(new URL(url,"/chapter7/graphenePeople.html"));
        driver.click(jq("#edit1"));
        assertEquals("1",driver.getValue(jq("#personId")));
        assertEquals("John",driver.getValue(jq("#firstName")));
        assertEquals("Bob",driver.getValue(jq("#lastName")));
        assertEquals("john@nowhere.net",driver.getValue(jq("#email")));
        driver.type(jq("#firstName"), "Boy");
        driver.click(jq("#save"));
    }
    
    @Test
    public void testWithNetwork() throws Exception {
        NetworkTraffic jsonTraffic = driver.captureNetworkTraffic(NetworkTrafficType.JSON);
        // do work..
        System.out.println(jsonTraffic.getTraffic());
    }
    @Test
    public void testWithMultipleNetwork() throws Exception {
        NetworkTraffic jsonTraffic = driver.captureNetworkTraffic(NetworkTrafficType.JSON);
        NetworkTraffic plainTraffic = driver.captureNetworkTraffic(NetworkTrafficType.PLAIN);
        // do work..
        System.out.println(jsonTraffic.getTraffic());
        System.out.println(plainTraffic.getTraffic());
    }
    @Test
    public void testOpenPageAndScreenCap() throws Exception {
        driver.open(new URL(url,"/chapter7/graphenePeople.html"));
        BufferedImage homePageImage = driver.captureScreenshot();
        //output image to file
    }
    @Test
    public void testInteractions() throws Exception {
        driver.altKeyDown(); //press the alt key
        driver.type(jq("#email"), "jane.doe@gmail.com");//find the elment email, set the value to jane.doe@gmail.com
        driver.scrollIntoView(jq("#somePageElement"), true); //scroll to the given element's location on the page.
        driver.click(jq("#save")); //click the save button.
        driver.mouseOver(jq("#lowerDiv")); //moves mouse over the lowerDiv element.
        driver.check(jq("#isEligible")); //checks the checkbox isEligible
    }
    
    @Test
    public void testUsingJavascript() throws Exception {
        JavaScript js = JavaScript.js("(\"#peopleListCont\").empty();");
        js.append("\n").append("window.alert('Hello, world!');");
        // do more work.
        driver.addScript(js);
        // do more work..
        assertTrue(driver.isAlertPresent());
    }
}
