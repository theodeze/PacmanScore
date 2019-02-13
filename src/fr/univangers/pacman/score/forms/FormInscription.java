package fr.univangers.pacman.score.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormInscription {
	private static final String CHAMP_EMAIL      = "Identifiant_inscription";
    private static final String CHAMP_PASS       = "MotDePasse_inscription";
    private static final String CHAMP_CONF       = "MotDePasse_inscription_confirmation";
    private static final String CHAMP_PSEUDO     = "Pseudo_inscription";

    private static final String PATTERN_MAIL	= 
    		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PATTERN_PASS 	=
    		"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    
    private String              resultat;
    private Map<String, String> erreurs          = new HashMap<>();    
    private DAOUtilisateur 		daoUtilisateur;
    
    public String getResultat() {
    	return resultat;
    }
    
    public Map<String, String> getErreurs() {
    	return erreurs;
    }
    
    public FormInscription(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
	
	public Utilisateur inscrireUtilisateur(HttpServletRequest request) {
		String email = (String) request.getParameter(CHAMP_EMAIL);
		String pass = (String) request.getParameter(CHAMP_PASS);
		String conf = (String) request.getParameter(CHAMP_CONF);
		String pseudo = (String) request.getParameter(CHAMP_PSEUDO);
		Utilisateur utilisateur = new Utilisateur();
		try {
			traiterEmail(email, utilisateur);
			traiterPass(pass, conf, utilisateur);
			traiterPseudo(pseudo, utilisateur);
			if(erreurs.isEmpty()) {
				daoUtilisateur.creer(utilisateur);
				resultat = "Inscription réussite";
			} else {
				resultat = "Echec inscription";
				utilisateur = null;
			}
		} catch(DAOException e) {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			utilisateur = null;
		}
		return utilisateur;
	}
	
	private void traiterEmail(String email, Utilisateur utilisateur) {
		try {
			validationEmail(email);
		} catch(FormException e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
		utilisateur.setEmail(email);
	}
	
	private void validationEmail(String email) throws FormException {
		if(email == null)
			 throw new FormException("Merci de saisir un email.");
		else if(!email.matches(PATTERN_MAIL))
			throw new FormException("L'email est invalide");
	}
	
	private void traiterPass(String pass, String conf, Utilisateur utilisateur) {
		try {
			validationPass(pass, conf);
		} catch (FormException e) {
			setErreur(CHAMP_PASS, e.getMessage());
		}
		String cryptPass = BCrypt.withDefaults().hashToString(12, pass.toCharArray());	
		utilisateur.setMotDePasse(cryptPass);
	}
	
	private void validationPass(String pass, String conf) throws FormException {
		if(pass == null || conf == null)
			 throw new FormException("Merci de saisir et de confirmer votre mot de passe.");
		else if(!pass.equals(conf))
			throw new FormException("Les deux mots de passe ne corresponde pas.");
		else if(pass.length() < 8)
			throw new FormException("Les mots de passe doit avoir une taille supperieur à 8.");
		else if(!pass.matches(PATTERN_PASS))
			throw new FormException("Les mots de passe doit être composé d'une miniscule, "
					+ "une majuscule et un chiffre.");
			
	}
	
	private void traiterPseudo(String pseudo, Utilisateur utilisateur) {
		try {
			validationPseudo(pseudo);
		} catch(FormException e) {
			setErreur(CHAMP_PSEUDO, e.getMessage());
		}
		utilisateur.setPseudo(pseudo);
	}
	
	private void validationPseudo(String pseudo) throws FormException {
		if(pseudo == null)
			 throw new FormException("Merci de saisir votre pseudo.");
		else if(pseudo.length() < 4)
			throw new FormException("Le pseudo doit avoir une taille supperieur à 4.");
	}
	
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }
	
}
