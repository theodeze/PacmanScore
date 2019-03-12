package fr.univangers.pacman.score.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormModifEmail {
	private static final String CHAMP_EMAIL_OLD      = "Old_Identifiant_modif_email";
	private static final String CHAMP_EMAIL      = "Identifiant_modif_email";
    private static final String CHAMP_PASS       = "MotDePasse_modif_email";
    
    private static final String PATTERN_MAIL	= 
    		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private String              resultat;
    private Map<String, String> erreurs          = new HashMap<>();    
    private DAOUtilisateur 		daoUtilisateur;
    
    public String getResultat() {
    	return resultat;
    }
    
    public Map<String, String> getErreurs() {
    	return erreurs;
    }
    
    public FormModifEmail(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
	
	public Utilisateur modifEmailUtilisateur(HttpServletRequest request) {
		String emailOld = request.getParameter(CHAMP_EMAIL_OLD);
		String email = request.getParameter(CHAMP_EMAIL);
		String pass = request.getParameter(CHAMP_PASS);
		Utilisateur utilisateur = null;
		try {
			utilisateur = daoUtilisateur.trouver(emailOld);
			if(utilisateur !=null && traiterPass(pass, utilisateur)) {
				traiterEmail(email, utilisateur);
				if(erreurs.isEmpty()) {
					resultat = "Modification réussite";
				} else {
					resultat = "Echec modification";
					utilisateur = null;
				}
			} else {
				resultat = "Echec modification";
				utilisateur = null;
			}
		} catch(DAOException e) {
            resultat = "Échec de la modification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			utilisateur = null;
		}
		return utilisateur;
	}
	
	private void traiterEmail(String email, Utilisateur utilisateur) {
		try {
			validationEmail(email);
			daoUtilisateur.modifierEmail(utilisateur, email);
		} catch(FormException|DAOException e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
	}
	
	private void validationEmail(String email) throws FormException {
		if(email == null)
			 throw new FormException("Merci de saisir un email.");
		else if(!email.matches(PATTERN_MAIL))
			throw new FormException("L'email est invalide");
	}
	
	private boolean traiterPass(String pass, Utilisateur utilisateur) {
		return BCrypt.verifyer().verify(pass.toCharArray(), utilisateur.getMotDePasse()).verified;
	}
	
	
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

}
