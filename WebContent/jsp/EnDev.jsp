<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="<c:url value="/img/ua.png"/>">
    
	<title>En cours de Développement...</title>
	
	<link rel="stylesheet" href="<c:url value="/css/default.css"/>">
	
	<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <script src="<c:url value="/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/js/popper.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	
</head>
<body>

  	<c:import url="/jsp/Menu.jsp"/>


	<div class="card">
		  	<div class="card-header">
		  		<h5 class="card-title" style='font-weight:bold'>En Développement...</h5>
		  	</div>
		  	<div id="histo-div" class="card-body">
		  		<div class="row justify-content-center">
		  			<div class="col-md-4 justify-content-center">
		  				<p style="font-size: 150%;">Désolé, cette page n'est pas encore accessible...</p>
		  			</div>
		  		</div>
		  		<div class="row justify-content-center">
		  			<div class="col-md-2 justify-content-center">
		  				<img src="<c:url value="/img/worker.png"/>" alt="Women Worker" width="250">
		  			</div>
		  		</div>
	   		</div>
	</div>



</body>
</html>