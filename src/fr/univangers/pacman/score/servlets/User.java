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
import fr.univangers.pacman.score.dao.DAOPartie;
import fr.univangers.pacman.score.dao.DAOUtilisateur;
import fr.univangers.pacman.score.forms.FormConnexion;
import fr.univangers.pacman.score.forms.FormInscription;
import fr.univangers.pacman.score.forms.FormModifEmail;
import fr.univangers.pacman.score.forms.FormModifMdp;
import fr.univangers.pacman.score.forms.FormModifPseudo;

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
    public static final String ATT_FORM = "form";
    private String email;
    
    private DAOUtilisateur daoUser;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
    }

    @Override
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
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
    	HttpSession session = request.getSession();
    	Utilisateur utilisateur = new Utilisateur();
    	
    	boolean result = false;
    	
		String email_suppr=request.getParameter("Identifiant_suppr");				
		String deconnexion = request.getParameter("Deconnexion_activate");
						
		String type = request.getParameter("Type");
		if(type == null) {
			
		} else if(type.equals("Connexion")) {
			FormConnexion formConnexion = new FormConnexion(daoUser);	
			utilisateur=formConnexion.connecterUtilisateur(request);
			if(utilisateur == null) {
				request.setAttribute( ATT_MSG_WARNING, formConnexion.getResultat());
				session.setAttribute( ATT_SESSION_USER, null);
			}
			else {
				request.setAttribute( ATT_MSG_SUCCESS, formConnexion.getResultat());
				session.setAttribute( ATT_SESSION_USER, utilisateur);
			}	
		} 
		
		else if(type.equals("Inscription")) {
			FormInscription formInscription = new FormInscription(daoUser);	
			utilisateur=formInscription.inscrireUtilisateur(request);
			request.setAttribute(ATT_FORM, formInscription);
			if(utilisateur == null) {
				request.setAttribute( ATT_MSG_WARNING, formInscription.getResultat());
				session.setAttribute( ATT_SESSION_USER, null);
			}
			else {
				request.setAttribute( ATT_MSG_SUCCESS, formInscription.getResultat());
				session.setAttribute( ATT_SESSION_USER, utilisateur);
			}		
		}

		else if(type.equals("Modif_email")) {
			FormModifEmail form_modif_email = new FormModifEmail(daoUser);
			request.setAttribute(ATT_FORM, form_modif_email);
			result = form_modif_email.ModifEmailUtilisateur(request,utilisateur);
			if(result) {
				request.setAttribute( ATT_MSG_SUCCESS, form_modif_email.getResultat());
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, form_modif_email.getResultat());
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);
		}
		
		else if(type.equals("Modif_mdp")) {
			FormModifMdp form_modif_mdp = new FormModifMdp(daoUser);
			request.setAttribute(ATT_FORM, form_modif_mdp);
			result= form_modif_mdp.ModifMDPUtilisateur(request,utilisateur);
			if(result) {
				request.setAttribute( ATT_MSG_SUCCESS, form_modif_mdp.getResultat());
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, form_modif_mdp.getResultat());
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);		
		}
		
		else if(type.equals("Modif_pseudo")) {
			FormModifPseudo form_modif_pseudo = new FormModifPseudo(daoUser);
			request.setAttribute(ATT_FORM, form_modif_pseudo);
			result = form_modif_pseudo.ModifPseudoUtilisateur(request, utilisateur);
			if (result) {
				request.setAttribute( ATT_MSG_SUCCESS, form_modif_pseudo.getResultat());
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, form_modif_pseudo.getResultat());
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);		
			
		}
		
		else if(type.equals("Suppr")) {
			
			
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
					session.setAttribute(ATT_SESSION_USER,null);
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
	
}
