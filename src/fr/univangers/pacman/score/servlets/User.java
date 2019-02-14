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
import fr.univangers.pacman.score.forms.FormModifEmail;
import fr.univangers.pacman.score.forms.FormModifMdp;
import fr.univangers.pacman.score.forms.FormModifPseudo;
import fr.univangers.pacman.score.forms.FormSuppr;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    	}
    	catch(Exception e) {
    		
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
    	HttpSession session = request.getSession();
    	Utilisateur utilisateur = new Utilisateur();   	
		String type = request.getParameter("Type");
			
		if(type.equals("Connexion")) {
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
			FormModifEmail formModifEmail = new FormModifEmail(daoUser);
			request.setAttribute(ATT_FORM, formModifEmail);
			Utilisateur userTmp = formModifEmail.modifEmailUtilisateur(request);
			if(userTmp == null) {
				request.setAttribute( ATT_MSG_SUCCESS, formModifEmail.getResultat());
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, formModifEmail.getResultat());
				utilisateur = userTmp;
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);
		}
		
		else if(type.equals("Modif_mdp")) {
			FormModifMdp formModifMdp = new FormModifMdp(daoUser);
			request.setAttribute(ATT_FORM, formModifMdp);
			Utilisateur userTmp = formModifMdp.modifMDPUtilisateur(request);
			if(userTmp == null) {
				request.setAttribute( ATT_MSG_SUCCESS, formModifMdp.getResultat());
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, formModifMdp.getResultat());
				utilisateur = userTmp;
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);		
		}
		
		else if(type.equals("Modif_pseudo")) {
			FormModifPseudo formModifPseudo = new FormModifPseudo(daoUser);
			request.setAttribute(ATT_FORM, formModifPseudo);
			Utilisateur userTmp = formModifPseudo.modifPseudoUtilisateur(request);
			if (userTmp == null) {
				request.setAttribute( ATT_MSG_SUCCESS, formModifPseudo.getResultat());
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, formModifPseudo.getResultat());
				utilisateur = userTmp;
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);				
		}
		
		else if(type.equals("Suppr")) {
			FormSuppr formSuppr = new FormSuppr(daoUser);
			utilisateur = formSuppr.supprimerUtilisateur(request);
			if (utilisateur!=null) {
				request.setAttribute( ATT_MSG_SUCCESS, formSuppr.getResultat());
				session.invalidate();
	            session = request.getSession();
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, formSuppr.getResultat());
			}
			session.setAttribute( ATT_SESSION_USER, utilisateur);				
		}		

    	
		else if(type.equals("deconnexion")) {
            // Supprime la session, ce qui déconnecte l'ulisateur (Doit être testé avant exécution)
            session.invalidate();
            session = request.getSession();
            session.setAttribute(ATT_MSG_SUCCESS, "Vous êtes déconnecté");
    		}	
		
    	try {
    		doGet(request, response);
    	}
    	catch(Exception e) {
    		
    	}
	}		
}
