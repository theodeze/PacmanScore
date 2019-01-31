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
    	
    	<div id="app-co">
		
		<div v-if=v1>
			     	<div class="btn-group" role="group">
		
    	<div id="creat">
    	<div class="pr-2"><button v-on:click="creat" class="btn btn-info" type="submit" >Créer Compte</button></div>
    	</div>
    	    			<div id="conn">
    	
		<div class="pl-2"><button v-on:click="conn" class="btn btn-success" type="submit">SE CONNECTER</button></div> 
		</div>
		</div>        	       
		</div>
	            
	        <div v-if=v2> 
	     	<div class="btn-group" role="group">
	              <div class="pr-2"><button class="btn btn-info" type="submit">Modifier Compte</button></div>         
	              <div class="pl-2"><button class="btn btn-danger" type="submit">SE DECONNECTER</button></div>
	     </div> 
	     </div>  
	     </div> 
    </div>     
  </div>
</nav>