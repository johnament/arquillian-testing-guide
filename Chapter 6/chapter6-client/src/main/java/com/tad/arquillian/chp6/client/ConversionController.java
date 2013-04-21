package com.tad.arquillian.chp6.client;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.tad.arquillian.chp6.convert.TemperatureConverter;

@RequestScoped
@Named("conversion")
public class ConversionController {
	@EJB(lookup="java:global/chapter6-ear/chapter6/TemperatureConverter!com.tad.arquillian.chp6.convert.TemperatureConverter")
	private TemperatureConverter converter;
	
	private Double celsius;
	private Double farenheit;
	
	public String handleConversion() {
		if(celsius != null) {
			farenheit = converter.convertToFarenheit(celsius);
		} else if(farenheit != null) {
			celsius = converter.convertToCelsius(farenheit);
		}
		return "/mainView.xhtml";
	}

	public Double getCelsius() {
		return celsius;
	}

	public void setCelsius(Double celsius) {
		this.celsius = celsius;
	}

	public Double getFarenheit() {
		return farenheit;
	}

	public void setFarenheit(Double farenheit) {
		this.farenheit = farenheit;
	}
	
}
