<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<c:url value="/img/ghost_blue.png"/>">
<title>Accueil - PacmanScore</title>

 <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/Accueil.css"/>">
    <script src="<c:url value="/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/js/popper.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script> 
</head>

<body>
<c:import url="/jsp/Menu.jsp"/>
  
    <div class="container-fluid text-center mb-4" id="container">
    
	    <div class="row justify-content-center pb-4">
	        <div class="col-md-6" style="text-align:center;"><img src="<c:url value="/img/Partie.png"/>" alt="Pacman" ></div>
	   </div>
	   
	   <div class="row justify-content-center pb-4">
	           
			<div class="dropdown pr-1">
				  <button class="btn btn-outline-primary dropdown-toggle" type="button" id="dropdownMenuButton_classement" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				   Classement Personnel
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton_classement">
				    <a class="dropdown-item" href="#">Général</a>
				    <a class="dropdown-item" href="#">Mensuel</a>
				    <a class="dropdown-item" href="#">Hebdomadaire</a>
				  </div>
			</div>  
			
				
			<div class="dropdown pl-1">
				  <button class="btn btn-outline-primary dropdown-toggle" type="button" id="dropdownMenuButton_personnel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Résultat par Partie
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton_personnel">
				    <a class="dropdown-item" href="#">Général</a>
				    <a class="dropdown-item" href="#">Mensuel</a>
				    <a class="dropdown-item" href="#">Hebdomadaire</a>
				  </div>
			</div>   
			
			</div>
			       
			
	        <div class="row justify-content-center pb-2">
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Classement</h5>	        	
	        	</div>
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Pseudo</h5>	        	
	        	</div>
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Score total</h5>        	
	        	</div>	        
	        </div>
	        
	         <div class="row justify-content-center pb-2">
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Pseudo</h5>	        	
	        	</div>
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Date</h5>	        	
	        	</div>
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Score</h5>	        	
	        	</div>
	        	<div class="col-md-2 table-bordered table_score">
	        		<h5>Résultat de la partie</h5>	        	
	        	</div> 	        
	        </div>	    

       </div> 
      
</body>
</html>