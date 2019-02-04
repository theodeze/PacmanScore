package fr.univangers.pacman.score.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOFactory;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE = "/WEB-INF/jsp/User.jsp";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_MSG_WARNING = "Warning";
    public static final String ATT_MSG_SUCCESS = "Success";
	//private static final String URL_REDIRECTION  = "/User";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    public boolean validateMDP(Utilisateur aTester, Utilisateur dansLaBD) {
    	if (dansLaBD!=null) {  	
    		if (aTester.getMotDePasse().equals(dansLaBD.getMotDePasse()))
    			return true;
    	}
    	return false;
    }
    
    public boolean validateNotExistInDB(Utilisateur dansLaBD) {
    	if (dansLaBD==null) {  	
   			return true;
    	}
    	return false;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
        // Supprime la session, ce qui déconnecte l'ulisateur (Doit être testé avant exécution)
       /* session.invalidate();
        session = request.getSession();
        session.setAttribute("Success", "Vous êtes déconnecté");
        response.sendRedirect(request.getContextPath() + URL_REDIRECTION);	*/	
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
    	HttpSession session = request.getSession();
    	Utilisateur utilisateur = new Utilisateur();	
    	DAOUtilisateur daoUser = DAOFactory.getInstance().getDaoUtilisateur();
    	
    	boolean result_connexion = false;
    	boolean result_inscription = false;  	
    	String email_connexion=request.getParameter("Identifiant_connexion");
		String email_inscription=request.getParameter("Identifiant_inscription");

    	if (email_connexion!=null) {
	    	utilisateur.setEmail(email_connexion);
			utilisateur.setMotDePasse(request.getParameter("MotDePasse_connexion"));
					
			result_connexion = validateMDP(utilisateur,daoUser.trouver(email_connexion));
			if(!result_connexion) {
				request.setAttribute( ATT_MSG_WARNING, "Email et/ou mot de passe incorrect(s)");
			}
			if (type != null && result_connexion) {
				request.setAttribute( ATT_MSG_SUCCESS, "Vous êtes connecté");
				session.setAttribute( ATT_SESSION_USER, utilisateur);
			}
			else {
				session.setAttribute( ATT_SESSION_USER, null);
			}
    	}
		
		else if (email_inscription!=null) {
	    	utilisateur.setEmail(email_inscription);
			String pseudo = request.getParameter("Pseudo_inscription");
			utilisateur.setPseudo(pseudo);
			utilisateur.setMotDePasse(request.getParameter("MotDePasse_inscription"));
			
			result_inscription = validateNotExistInDB(daoUser.trouver(email_connexion));
			if(!result_inscription) {
				request.setAttribute( ATT_MSG_WARNING, "Email déjà utilisé");
			}
			if (type != null && result_inscription) {
				daoUser.creer(utilisateur);
				request.setAttribute( ATT_MSG_SUCCESS, "Inscrit avec succès");
				session.setAttribute( ATT_SESSION_USER, utilisateur);
			}
			else {
				session.setAttribute( ATT_SESSION_USER, null);
			}		
		}
		
		doGet(request, response);
	}	
}
