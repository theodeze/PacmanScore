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
import fr.univangers.pacman.score.forms.FormConnexion;
import fr.univangers.pacman.score.forms.FormInscription;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public static final String CONF_DAO_FACTORY = "daofactory";
	private static final String VUE = "/WEB-INF/jsp/User.jsp";
	public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ATT_MSG_WARNING = "Warning";
    public static final String ATT_MSG_SUCCESS = "Success";
    private String email;
    
    private DAOUtilisateur daoUser;
	private Utilisateur utilisateur;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException{
    	 this.daoUser = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getDaoUtilisateur();
    }
    
    public boolean validateMDP(String mdp_a_tester, Utilisateur dansLaBD) {
    	if (dansLaBD!=null) {  	
    		if (mdp_a_tester.equals(dansLaBD.getMotDePasse()))
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
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	utilisateur = new Utilisateur();
    	
    	boolean result = false;
    	
    	/*String email_connexion=request.getParameter("Identifiant_connexion");
		String email_inscription=request.getParameter("Identifiant_inscription");*/
		String old_email_modif_email=request.getParameter("Old_Identifiant_modif_email");
		String email_modif_mdp=request.getParameter("Identifiant_modif_mdp");
		String email_modif_pseudo=request.getParameter("Identifiant_modif_pseudo");
		String email_suppr=request.getParameter("Identifiant_suppr");
				
		String deconnexion = request.getParameter("Deconnexion_activate");
				
		FormConnexion formConnexion = new FormConnexion(daoUser);	
		attribuerUtilisateur(formConnexion.connecterUtilisateur(request));

		FormInscription formInscription = new FormInscription(daoUser);	
		attribuerUtilisateur(formInscription.inscrireUtilisateur(request));

		
		email=utilisateur.getEmail();
		session.setAttribute( ATT_SESSION_USER, utilisateur);
		
		/*if(email_connexion!=null) {
			email = email_connexion;
		}
		else if (email_inscription != null) {
			email = email_inscription;
		}d

    	if (email_connexion!=null) {
	    	utilisateur.setEmail(email_connexion);
	    	String mdp = request.getParameter("MotDePasse_connexion");
			utilisateur.setMotDePasse(mdp);
					
			result = validateMDP(mdp,daoUser.trouver(email_connexion));
			if(!result) {
				request.setAttribute( ATT_MSG_WARNING, "Email et/ou mot de passe incorrect(s)");
				session.setAttribute( ATT_SESSION_USER, null);
			}
			else {
				request.setAttribute( ATT_MSG_SUCCESS, "Vous êtes connecté");
				session.setAttribute( ATT_SESSION_USER, utilisateur);
			}
    	}
			
		 else if (email_inscription!=null) {
			String mdp = request.getParameter("MotDePasse_inscription");
			String confirm = request.getParameter("MotDePasse_inscription_confirmation");

			if (mdp.equals(confirm)) {
		    	utilisateur.setEmail(email_inscription);
				String pseudo = request.getParameter("Pseudo_inscription");
				utilisateur.setPseudo(pseudo);
				utilisateur.setMotDePasse(mdp);
				
				result = validateNotExistInDB(daoUser.trouver(email_inscription));
				if(!result) {
					request.setAttribute( ATT_MSG_WARNING, "Email déjà utilisé");
					session.setAttribute( ATT_SESSION_USER, null);
				}
				else {				
					daoUser.creer(utilisateur);
					request.setAttribute( ATT_MSG_SUCCESS, "Inscrit avec succès");
					session.setAttribute( ATT_SESSION_USER, utilisateur);
				}		
			}
		}  */
    	
		if (old_email_modif_email!=null) {
			String new_email_modif = request.getParameter("Identifiant_modif_email");
			String pwd_email_modif = request.getParameter("MotDePasse_modif_email");
			result = validateMDP(pwd_email_modif,daoUser.trouver(old_email_modif_email));
		
			// Modification dans la DAO
			//utilisateur.setEmail(new_email_modif);
			
		} 
    	
		else if (email_modif_mdp!=null) {
			
			String old_pwd_modif_mdp = request.getParameter("old_MotDePasse_modif_mdp");
			String pwd_modif_mdp = request.getParameter("MotDePasse_modif_mdp");
			String pwd_modif_mdp_confirm = request.getParameter("MotDePasse_modif_mdp_confirm");
			result = validateMDP(old_pwd_modif_mdp,daoUser.trouver(email_modif_mdp));
		
			// Modification dans la DAO
			//utilisateur.setEmail(new_email_modif);
			
		} 
    	
		else if (email_modif_pseudo!=null) {
			
			String pseudo_modif_pseudo = request.getParameter("Pseudo_modif_pseudo");
			String pwd_modif_pseudo = request.getParameter("MotDePasse_modif_pseudo");
			result = validateMDP(pwd_modif_pseudo,daoUser.trouver(email_modif_pseudo));
		
			// Modification dans la DAO
			//utilisateur.setEmail(new_email_modif);
			
		} 

		else if (email_suppr!=null) {			
			String pwd_suppr = request.getParameter("MotDePasse_suppr");
			result = validateMDP(pwd_suppr,daoUser.trouver(email_suppr));			
			if(!result) {
				request.setAttribute( ATT_MSG_WARNING, "Email et/ou mot de passe incorrect(s)");				
			}
			else {
				if(email_suppr.equals(email)) {
					daoUser.supprimer(email_suppr);				
					session.invalidate();
		            session = request.getSession();
					request.setAttribute( ATT_MSG_SUCCESS, "Compte supprimé avec succès");
				}
				else 
					request.setAttribute( ATT_MSG_WARNING, "Il ne s'agit pas de votre compte");
			}			
		} 
    	
		else if(deconnexion != null) {
            // Supprime la session, ce qui déconnecte l'ulisateur (Doit être testé avant exécution)
            session.invalidate();
            session = request.getSession();
            session.setAttribute(ATT_MSG_SUCCESS, "Vous êtes déconnecté");
    		}	
    	
		doGet(request, response);
	}	
	
	private void attribuerUtilisateur(Utilisateur tmp) {
		if(tmp != null) {
			utilisateur = tmp;
		}
	}
	
	
}
