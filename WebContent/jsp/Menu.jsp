<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="<c:url value="/css/menu.css"/>">

	<!-- Menu en header de la page -->						

<nav class="navbar navbar-expand-lg fixed-top navbar-dark shadowbottom">
	<div class="container">
	
	<!-- PACMAN -->
	    <a class="navbar-brand" href="#" style="color:white">PACMAN</a>
	    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
	    	<span class="navbar-toggler-icon"></span>
	    </button>
      
	<!-- Lien Accueil -->
	
    <div class="collapse navbar-collapse" id="navbarsExample07">
      <ul class="navbar-nav mr-auto">
        
        <li class="nav-item active">
          <a class="nav-link" style="color:white" href="<c:url value="/User"/>">Accueil
          	<span class="sr-only">(current)</span>
          </a>
        </li>
      </ul>
     
      
    	<!-- Bouton pour la Connexion/Première Connexion/Déconnexion -->
    
      <form class="form-inline my-2 my-md-0">
     
	        <a class="btn btn-info" href="<c:url value="/connexion"/>">SE CONNECTER</a>
    	
      </form>
            
    </div>     
  </div>
</nav>