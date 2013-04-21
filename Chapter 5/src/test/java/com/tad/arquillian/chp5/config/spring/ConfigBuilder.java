package com.tad.arquillian.chp5.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tad.arquillian.chp5.spring.BasicUserProvider;
import com.tad.arquillian.chp5.spring.UserProvider;

@Configuration
public class ConfigBuilder {
	@Bean
	public UserProvider createUserProvider() {
		return new BasicUserProvider();
	}
}