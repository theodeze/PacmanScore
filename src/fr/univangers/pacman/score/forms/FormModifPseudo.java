package fr.univangers.pacman.score.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;


public class FormModifPseudo {

	private static final String CHAMP_EMAIL      = "Identifiant_modif_pseudo";
    private static final String CHAMP_PASS       = "MotDePasse_modif_pseudo";
    private static final String CHAMP_PSEUDO     = "Pseudo_modif_pseudo";


    private String              resultat;
    private Map<String, String> erreurs          = new HashMap<>();    
    private DAOUtilisateur 		daoUtilisateur;
    
    public String getResultat() {
    	return resultat;
    }
    
    public Map<String, String> getErreurs() {
    	return erreurs;
    }
    
    public FormModifPseudo(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
	
	public Utilisateur modifPseudoUtilisateur(HttpServletRequest request) {
		String email = request.getParameter(CHAMP_EMAIL);
		String pass = request.getParameter(CHAMP_PASS);
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		Utilisateur utilisateur = null;
		try {
			utilisateur = daoUtilisateur.trouver(email);
			if(utilisateur !=null && verifMdp(pass,utilisateur)) {
				traiterPseudo(pseudo,utilisateur);
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
	
	private boolean verifMdp(String pwd, Utilisateur utilisateur) {
		return BCrypt.verifyer().verify(pwd.toCharArray(), utilisateur.getMotDePasse()).verified;
	}
	
	private void traiterPseudo(String pseudo,Utilisateur utilisateur) throws DAOException {
		try {
			validationPseudo(pseudo);
		} catch(FormException e) {
			setErreur(CHAMP_PSEUDO, e.getMessage());
		}
		daoUtilisateur.modifierPseudo(utilisateur, pseudo);
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
