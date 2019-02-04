package fr.univangers.pacman.score.forms;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormInscription {
    private static final String CHAMP_EMAIL      = "email";
    private static final String CHAMP_PASS       = "motdepasse";
    private static final String CHAMP_CONF       = "confirmation";
    private static final String CHAMP_PSEUDO     = "pseudo";

    private static final String ALGO_CHIFFREMENT = "SHA-256";
    
    private DAOUtilisateur daoUtilisateur;

    public FormInscription(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
	
	public Utilisateur inscrireUtilisateur(HttpServletRequest request) {
		String email = (String) request.getAttribute(CHAMP_EMAIL);
		String pass = (String) request.getAttribute(CHAMP_PASS);
		String conf = (String) request.getAttribute(CHAMP_CONF);
		String pseudo = (String) request.getAttribute(CHAMP_PSEUDO);

		Utilisateur utilisateur = new Utilisateur();
		try {
			traiterEmail(email, utilisateur);
			traiterPass(pass, conf, utilisateur);
			traiterPseudo(pseudo, utilisateur);
			
			daoUtilisateur.creer(utilisateur);
		} catch(DAOException e) {
			
		}
		
		return utilisateur;
	}
	
	private void traiterEmail(String email, Utilisateur utilisateur) {
		
		utilisateur.setEmail(email);
	}
	
	private void validationEmail(String email) throws FormException {
		
	}
	
	private void traiterPass(String pass, String conf, Utilisateur utilisateur) {
		try {
			validationPass(pass, conf);
		} catch (FormException e) {
			e.printStackTrace();
		}
		
		String cryptPass = BCrypt.withDefaults().hashToString(12, pass.toCharArray());	
		utilisateur.setMotDePasse(cryptPass);
	}
	
	private void validationPass(String pass, String conf) throws FormException {
		if(!pass.equals(conf))
			throw new FormException("Les deux mots de passe ne corresponde pas");
	}
	
	private void traiterPseudo(String pseudo, Utilisateur utilisateur) {
		
		utilisateur.setPseudo(pseudo);
	}
	
}
