<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Chapter8 Spring</title>
</head>
<body>
	<h3>Login Form</h3>
	<FONT color="blue">
		<h5>Login using admin@admin.com/admin1</h5>
	</FONT>
	<form:form action="login.page" commandName="loginForm">
		<table>
			<tr>
				<td>User Name:<FONT color="red"><form:errors
							path="username" /></FONT></td>
				<td><form:input path="username" /></td>
			</tr>
			<tr>
				<td>Password:<FONT color="red"><form:errors
							path="password" /></FONT></td>
				<td><form:password path="password" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Login" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>