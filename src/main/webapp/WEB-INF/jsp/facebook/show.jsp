<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
    <head>
        <title>Facebook test page</title>
    </head>
    <body>
        <h2>Welcome ${sessionObjects.facebook.name}!</h2>
        <ul>
        	<li>ID: ${sessionObjects.facebook.id}</li>
        	<li>Email: ${sessionObjects.facebook.me.email}</li>
        </ul>       
            
		<form action="<c:url value="/facebook/post" />" method="post">
			<h3>Post your status!</h3>
			<textarea cols="80" rows="2" name="message"></textarea>
			<br />
			<input type="submit" value="Statuses" />
		</form>              
        
        <hr />
        
		<c:if test="${not empty messageSuccess}">
		
		<h3>Mensagem: <span style="color: red;"> ${messageSuccess}</span></h3>
		
		</c:if>         
        
        <hr />
            
        <a href="<c:url value="/facebook/logout" />">Click here to make logout!</a>
    </body>
</html>