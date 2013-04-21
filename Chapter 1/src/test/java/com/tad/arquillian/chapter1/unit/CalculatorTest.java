package com.tad.arquillian.chapter1.unit;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.tad.arquillian.chapter1.CalculatorController;
import com.tad.arquillian.chapter1.CalculatorData;
import com.tad.arquillian.chapter1.CalculatorForm;
import com.tad.arquillian.chapter1.CalculatorService;
import com.tad.arquillian.chapter1.CalculatorServiceImpl;

/**
 * @author JohnAment
 * 
 */
@RunWith(Arquillian.class)
public class CalculatorTest {

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class, "foo.jar")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addPackage(CalculatorData.class.getPackage());
    }

    @Inject
    CalculatorForm       form;
    @Inject
    CalculatorController controller;

    @Test
    public void testInjectedCalculator() {
        form.setX(1);
        form.setY(3);
        form.setZ(5);
        controller.sum();
        Assert.assertEquals(9, form.getSum());
    }

    @Test
    public void testCalculationOfBusinessData() {
        CalculatorData cd = new CalculatorData(1, 3, 5);
        CalculatorService ms = new CalculatorServiceImpl();
        ms.calculateSum(cd);
        Assert.assertEquals(1 + 3 + 5, cd.getCalculatedResult());
    }

}
