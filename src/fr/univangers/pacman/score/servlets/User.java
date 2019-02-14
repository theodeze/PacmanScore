package fr.univangers.pacman.score.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger("User Servlet");  
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
    		LOGGER.warn("Échec du chargement de la vue");
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
			connexionInscriptionServlet(session, request, utilisateur,formConnexion.getResultat());
		} 
		
		else if(type.equals("Inscription")) {
			FormInscription formInscription = new FormInscription(daoUser);	
			utilisateur=formInscription.inscrireUtilisateur(request);
			request.setAttribute(ATT_FORM, formInscription);
			connexionInscriptionServlet(session,request,utilisateur,formInscription.getResultat());
		}

		else if(type.equals("Modif_email")) {
			FormModifEmail formModifEmail = new FormModifEmail(daoUser);
			request.setAttribute(ATT_FORM, formModifEmail);
			Utilisateur userTmp = formModifEmail.modifEmailUtilisateur(request);
			modificationServlet(session,request,utilisateur,userTmp,formModifEmail.getResultat());
		}
		
		else if(type.equals("Modif_mdp")) {
			FormModifMdp formModifMdp = new FormModifMdp(daoUser);
			request.setAttribute(ATT_FORM, formModifMdp);
			Utilisateur userTmp = formModifMdp.modifMDPUtilisateur(request);
			modificationServlet(session,request,utilisateur,userTmp,formModifMdp.getResultat());
		}
		
		else if(type.equals("Modif_pseudo")) {
			FormModifPseudo formModifPseudo = new FormModifPseudo(daoUser);
			request.setAttribute(ATT_FORM, formModifPseudo);
			Utilisateur userTmp = formModifPseudo.modifPseudoUtilisateur(request);	
			modificationServlet(session,request,utilisateur,userTmp,formModifPseudo.getResultat());
		}
		
		else if(type.equals("Suppr")) {
			FormSuppr formSuppr = new FormSuppr(daoUser);
			utilisateur = formSuppr.supprimerUtilisateur(request);
			if (utilisateur==null) {
				session=finSession(session,request);
				request.setAttribute( ATT_MSG_SUCCESS, formSuppr.getResultat());
	            session.setAttribute(ATT_SESSION_USER, null);
			}
			else {
				request.setAttribute( ATT_MSG_WARNING, formSuppr.getResultat());
				session.setAttribute( ATT_SESSION_USER, utilisateur);				
			}
		}		

    	
		else if(type.equals("deconnexion")) {
            // Supprime la session, ce qui déconnecte l'ulisateur (Doit être testé avant exécution)
			session=finSession(session,request);
            session.setAttribute(ATT_MSG_SUCCESS, "Vous êtes déconnecté");
    		}	
		
    	try {
    		doGet(request, response);
    	}
    	catch(Exception e) {
    		LOGGER.warn("Échec du doGet");
    	}
	}	
	
	private void connexionInscriptionServlet(HttpSession session, HttpServletRequest request, Utilisateur utilisateur,String affichage) {
		if(utilisateur == null) {
			request.setAttribute( ATT_MSG_WARNING, affichage);
			session.setAttribute( ATT_SESSION_USER, null);
		}
		else {
			request.setAttribute( ATT_MSG_SUCCESS, affichage);
			session.setAttribute( ATT_SESSION_USER, utilisateur);
		}	
	}
	
	private void modificationServlet(HttpSession session, HttpServletRequest request, Utilisateur utilisateur, Utilisateur userTmp, String affichage) {
		if (userTmp != null) {
			request.setAttribute( ATT_MSG_SUCCESS, affichage);
			utilisateur = userTmp;
		}
		else {
			request.setAttribute( ATT_MSG_WARNING, affichage);
		}
		session.setAttribute( ATT_SESSION_USER, utilisateur);
	}
	
	private HttpSession finSession(HttpSession session, HttpServletRequest request){
		session.invalidate();
        return request.getSession();
        
	}
	
	
	
	
}
