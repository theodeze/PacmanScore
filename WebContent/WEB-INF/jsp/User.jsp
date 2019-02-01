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
    <script src="<c:url value="/js/vue.min.js"/>"></script>
   	<script src="https://cdn.jsdelivr.net/npm/vee-validate@latest/dist/vee-validate.js"></script>
</head>

<body>
	
	<div id="main">
		<c:import url="/jsp/Menu.jsp"/>
		
		<div class="container-fluid text-center mb-4">
		    	<div class="pb-4" style="text-align:center;"><img src="<c:url value="/img/Partie.png"/>" alt="Pacman" ></div>
		 <form id="divUser" class="form-signin" method="post" v-if=v2_account > 
	          <div class="form-label-group">
	            <input type="text" id="pseudo" name="Identifiant" class="form-control" required>
	            <label for="identifiant">Pseudo</label>
	          </div> 
	          <div class="form-label-group">
	            <input type="password" id="mdp" name="MotDePasse" class="form-control" required>
	            <label for="mdp">Mot de passe</label>
	          </div>      
	          <input type="hidden" name="Type" value="User" />
	              <button class="btn btn-info" type="submit">SE CONNECTER</button>
		</form>
		
		<form id="User" class="form-signin" @submit.prevent="validateBeforeSubmit()" method="post" v-if=v1_account> 
	          <div class="form-label-group">
	            <input type="text" id="pseudo" name="Identifiant" class="form-control" required>
	            <label for="pseudo">Pseudo</label>
	          </div> 
				
				<div class="form-label-group">	    
			      <input v-validate="'required'" id="pwd" name="password" type="password" class="form-control" ref="password" required>
			      <label for="pwd">Mot de Passe</label>
				</div>

				<div class="form-label-group">
			      <input v-validate="'required|confirmed:password'" id="pwd_conf" name="password_confirmation" type="password" class="form-control" data-vv-as="password" required>
					<label for="pwd_conf">Confirmation</label>	      
			  </div>
			
			  <div class="alert alert-danger" v-show="errors.any()">
			    <div v-if="errors.has('password')">
			      {{ errors.first('password') }}
			    </div>
			    <div v-if="errors.has('password_confirmation')">
			      {{ errors.first('password_confirmation') }}
			    </div>	
			  </div>     
     
	            <button class="btn btn-info" type="submit">CRÉER COMPTE</button>
        </form>
        
        <div class="row justify-content-center pb-4">
			
			<div class="dropdown pr-1" id="show_gen" >
				  <button v-on:click="show_gen" class="btn btn-outline-primary dropdown-toggle" type="button" id="dropdownMenuButton_classement" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				   Classement Personnel
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton_classement">
				    <a class="dropdown-item" href="#">Général</a>
				    <a class="dropdown-item" href="#">Mensuel</a>
				    <a class="dropdown-item" href="#">Hebdomadaire</a>
				  </div>
			</div>  
			
				
			<div class="dropdown pl-1" id="show_pers">
				  <button v-on:click="show_perso" class="btn btn-outline-primary dropdown-toggle" type="button" id="dropdownMenuButton_personnel" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    Résultat par Partie
				  </button>
				  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton_personnel">
				    <a class="dropdown-item" href="#">Général</a>
				    <a class="dropdown-item" href="#">Mensuel</a>
				    <a class="dropdown-item" href="#">Hebdomadaire</a>
				  </div>
			</div>   
			</div>
		
		 <div class="row justify-content-center pb-2" v-if=v1_tab>
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
	        
	        <div class="row justify-content-center pb-2" v-if=v2_tab>
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
		</div>
      
      <script src="<c:url value="/js/vue.js"/>"></script>	

</body>

</html>
