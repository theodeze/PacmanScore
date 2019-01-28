<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html lang="fr-FR">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" href="<c:url value="/img/ghost_blue.png"/>">

    <title>Connexion - PacmanScore</title>

    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/Accueil.css"/>">
    <script src="<c:url value="/js/jquery.min.js"/>"></script>
    <script src="<c:url value="/js/popper.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>   
</head>
<body>	

  	<c:import url="/jsp/Menu.jsp"/>
  
    <div class="container-fluid text-center mb-4" id="container">
    
	    <div class="row justify-content-center pb-2">
	        <div class="col-md-6" style="text-align:center;"><img src="<c:url value="/img/pacman.png"/>" alt="Pacman" width="200"></div>
	        
	    </div>

        <h1 class="font-weight-normal">Connexion</h1>

        <form id="divUser" class="form-signin" method="post"> 
          <div class="form-label-group">
            <input type="text" id="pseudo" name="Identifiant" class="form-control" placeholder="Identifiant" required>
            <label for="identifiant">Pseudo</label>
          </div>      
          <input type="hidden" name="Type" value="User" />
          <div class="btn-group" role="group">
              <button class="btn btn-primary" type="submit">SE CONNECTER</button>
          </div>
        </form>
         
      </div>
      
      <c:import url="/jsp/Footer.jsp"/>
      
</body>
</html>
