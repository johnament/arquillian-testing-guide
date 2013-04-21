package com.tad.arquillian.chp6.convert;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
@Stateless
@LocalBean
public class TemperatureConverter {
	@WebMethod(operationName="ConvertToCelsius")
	@WebResult(name="Celsius")
	public double convertToCelsius(@WebParam(name="Farenheit") double farenheit) {
		return (farenheit - 32)*5/9;
	}
	
	@WebMethod(operationName="ConvertToFarenheit")
	@WebResult(name="Farenheit")
	public double convertToFarenheit(@WebParam(name="Celsius")  double celsius) {
		return (celsius*9) / 5 + 32;
	}
}
