package com.tad.arquillian.chp6.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * The Class TemperatureConverter.
 * Converts from farenheit to celsius, and then celsius back to farenheit
 * 
 */
@Path("/convert")
@Stateless
public class TemperatureConverter {
	
	/**
	 * Convert to celsius.
	 *
	 * @param farenheit the farenheit
	 * @return the double
	 */
	@GET
	@Produces("text/plain")
	@Path("/farenheit/{far}")
	public double convertToCelsius(@PathParam("far") double farenheit) {
		return (farenheit - 32)*5/9;
	}
	
	/**
	 * Convert to farenheit.
	 *
	 * @param celsius the celsius
	 * @return the double
	 */
	@GET
	@Produces("text/plain")
	@Path("/celsius/{cel}")
	public double convertToFarenheit(@PathParam("cel")  double celsius) {
		return (celsius*9) / 5 + 32;
	}
}
