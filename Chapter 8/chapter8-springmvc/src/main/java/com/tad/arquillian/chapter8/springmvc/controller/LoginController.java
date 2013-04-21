package com.tad.arquillian.chapter8.springmvc.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("login.page")
public class LoginController {
	private static String SUCCESS = "loggedin";
	private static String SHOW_FORM = "login";
	private static String FAILURE = "login";
	private static String USER = "admin@admin.com";
	private static String PASS = "admin1";
	
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map model) {
		LoginForm form = new LoginForm();
		model.put("loginForm", form);
		return SHOW_FORM;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid LoginForm form, BindingResult result, Map model) {
		if(form.getUsername().equalsIgnoreCase(USER) && form.getPassword().equals(PASS)) {
			return SUCCESS;	
		} else {
			return FAILURE;
		}
	}
}
