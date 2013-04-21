package com.tad.arqdevguide.chp3.junit;

import static org.junit.Assert.*;
import org.junit.Test;

import com.tad.arqdevguide.chp3.HelloWorld;

public class HelloWorldTest
{
   @Test
   public void testGetText() {
      HelloWorld fixture = new HelloWorld();
      assertEquals("Hello, World!", fixture.getText());
   }
}
