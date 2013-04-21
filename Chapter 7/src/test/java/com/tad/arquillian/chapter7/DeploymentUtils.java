package com.tad.arquillian.chapter7;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.tad.arquillian.chapter7.model.Addresses;
import com.tad.arquillian.chapter7.model.People;
import com.tad.arquillian.chp7.controller.JaxRsActivator;
import com.tad.arquillian.chp7.controller.PeopleController;
import com.tad.arquillian.chp7.controller.PeopleResource;
import com.tad.arquillian.chp7.dao.PeopleDAO;


/**
 * @author JohnAment
 *
 */
public class DeploymentUtils {
    public static WebArchive createChapter7Archive() {
        return ShrinkWrap.create(WebArchive.class, "chapter7.war")
                .addClasses(Addresses.class,People.class,
                        PeopleDAO.class,PeopleController.class,
                        PeopleResource.class,JaxRsActivator.class)
                .addAsResource("META-INF/persistence.xml","META-INF/persistence.xml")
                .addAsWebResource("editPerson.xhtml","editPerson.xhtml")
                .addAsWebResource("people.xhtml","people.xhtml")
                .addAsWebResource("graphenePeople.html","graphenePeople.html")
                .addAsWebInfResource("WEB-INF/faces-config.xml","faces-config.xml")
                .addAsWebInfResource("WEB-INF/web.xml","web.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }
}
