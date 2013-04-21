package com.tad.arquillian.chapter7;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public @Model class HelloWorld
{
   private final String text = "Hello World!";

   private String letters;
   
   private String numbers;
   
   private String email;
   
   public HelloWorld() {}

   @PostConstruct
   public void initialize()
   {
      System.out.println(this.getClass().getSimpleName() + " was constructed");
   }

   public String getText()
   {
      return text;
   }

   @NotNull
   @Pattern(regexp = "[A-Za-z]*", message = "must contain only letters")
   public String getLetters()
   {
      return letters;
   }

   public void setLetters(String letters)
   {
      this.letters = letters;
   }

   @NotNull
   @Digits(fraction = 0, integer = 2)
   public String getNumbers()
   {
      return numbers;
   }

   public void setNumbers(String numbers)
   {
      this.numbers = numbers;
   }

   @NotNull
   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

}
