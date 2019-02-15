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
	
	<link rel="stylesheet" href="<c:url value="/css/menu.css"/>"> <!-- Menu en header de la page -->	
	
	<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <script src="<c:url value="/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/js/popper.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	
</head>
<body>

	<nav class="navbar navbar-expand-lg fixed-top navbar-dark shadowbottom">
	    <div class="container">
	
	        <!-- PACMAN -->
	        <a class="navbar-brand" href="#" style="color:white">PACMAN</a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07"
	            aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	
	        <!-- Lien Accueil -->
	
	        <div class="collapse navbar-collapse" id="navbarsExample07">
	            <ul class="navbar-nav mr-auto">
	
	                <li class="nav-item active">
	                    <a class="nav-link" style="color:white" href="<c:url value="/User"/>">Accueil <span class="sr-only">(current)</span>
	                    </a>
	                </li>
	            </ul>
	
	
	            <!-- Bouton pour la Connexion/Première Connexion/Déconnexion -->
	
	            <c:if test="${empty sessionScope.sessionUtilisateur}">
	                <div class="btn-group" role="group">
	
	                    <div id="create">
	                        <div class="pr-2"><a href="<c:url value='/User'/>"><button v-on:click="create" class="btn btn-info" type="button">Créer Compte</button></a></div>
	                    </div>
	                    <div id="notConnected">
	                        <div class="pl-2"><a href="<c:url value='/User'/>"><button v-on:click="notConnected" class="btn btn-success" type="button">SE
	                                CONNECTER</button></a></div>
	                    </div>
	                </div>
	            </c:if>
	
	            <c:if test="${!empty sessionScope.sessionUtilisateur}">
	                <div class="btn-group" role="group">
	                    <div class="pr-2"><a href="<c:url value='/User'/>"><button v-on:click="modification" class="btn btn-info" type="button">Modifier
	                            Compte</button></a></div>
	                    <div class="pl-2"><a href="<c:url value='/User'/>"><form id="button_deco" method="post"> <input type="hidden" name="Type" value="deconnexion" /><button name="Deconnexion_activate" class="btn btn-danger" type="submit">SE DECONNECTER</button></form></a></div>
	                </div>
	            </c:if>
	        </div>
		</div>
	</nav>

	<div class="card" style="padding-top:3%;">
		  	<div class="card-header">
		  		<h5 class="card-title" style='font-weight:bold'>En Développement...</h5>
		  	</div>
		  	<div id="histo-div" class="card-body">
		  		<div class="row justify-content-center">
		  			<div class="col-md-4 justify-content-center">
		  				<p style="font-size: 150%;">Désolé, cette fonctionnalité n'existe pas encore. Nous vous remercions pour votre compréhension...</p>
		  			</div>
		  		</div>
		  		<div class="row justify-content-center">
		  			<div class="col-md-2 justify-content-center">
		  				<img src="<c:url value="/img/worker.png"/>" alt="Women Worker" width="250">
		  			</div>
		  		</div>
	   		</div>
	</div>

	<div class="pb-4 pt-4 text-center"><img src="<c:url value="/img/pacman.png"/>" alt="Personnages" width=500></div>


</body>
</html>