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
	
	public boolean ModifEmailUtilisateur(HttpServletRequest request, Utilisateur utilisateur) {
		String email_old = (String) request.getParameter(CHAMP_EMAIL_OLD);
		String email = (String) request.getParameter(CHAMP_EMAIL);
		String pass = (String) request.getParameter(CHAMP_PASS);
		Utilisateur tmp = daoUtilisateur.trouver(email_old);
		try {
			if (tmp!=null && traiterPass(pass, tmp)) {
				traiterEmail(email,tmp);
				if(erreurs.isEmpty()) {
					daoUtilisateur.modifierEmail(tmp, email);
					resultat = "Modification réussite";
					utilisateur = tmp ;
					return true;
				}
				else {
					resultat = "Echec modification";
					return false;
				}
			} else {
				resultat = "Echec modification";
				return false;
			}
		} catch(DAOException e) {
            resultat = "Échec de la modification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            return false;
		}
	}
	
	private void traiterEmail(String email, Utilisateur utilisateur) {
		try {
			validationEmail(email);
		} catch(FormException e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
		daoUtilisateur.modifierEmail(utilisateur, email);
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
