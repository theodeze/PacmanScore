package fr.univangers.pacman.score.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormModifMdp {

	private static final String CHAMP_EMAIL      = "Identifiant_modif_mdp";
    private static final String CHAMP_PASS_OLD   = "old_MotDePasse_modif_mdp";
    private static final String CHAMP_PASS       = "MotDePasse_modif_mdp";
    private static final String CHAMP_CONF       = "MotDePasse_modif_mdp_confirm";

    private String              resultat;
    private Map<String, String> erreurs          = new HashMap<>();    
    private DAOUtilisateur 		daoUtilisateur;
    
    private static final String PATTERN_PASS 	=
    		"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    
    public String getResultat() {
    	return resultat;
    }
    
    public Map<String, String> getErreurs() {
    	return erreurs;
    }
    
    public FormModifMdp(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
	
	public boolean ModifMDPUtilisateur(HttpServletRequest request, Utilisateur utilisateur) {
		String email = (String) request.getParameter(CHAMP_EMAIL);
		String pass_old = (String) request.getParameter(CHAMP_PASS_OLD);
		String pass = (String) request.getParameter(CHAMP_PASS);
		String conf = (String) request.getParameter(CHAMP_CONF);
		Utilisateur tmp = daoUtilisateur.trouver(email);
		try {
			if(tmp!=null && verifOldMdp(pass_old,tmp)) {
				traiterPass(pass, conf, tmp);
				if(erreurs.isEmpty()) {
					daoUtilisateur.modifierPass(tmp, BCrypt.withDefaults().hashToString(12, pass.toCharArray()));
					resultat = "Modification réussite";
					utilisateur = tmp;
					return true;
					
				} else {
					resultat = "Echec modification";
					return false;
				}
			}
			else {
				resultat = "Echec modification";
				return false;
			}
		} catch(DAOException e) {
            resultat = "Échec de la modification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            return false;
		}
	}
	
	private boolean verifOldMdp(String pwd, Utilisateur utilisateur) {
		return BCrypt.verifyer().verify(pwd.toCharArray(), utilisateur.getMotDePasse()).verified;
	}
		
	private void traiterPass(String pass, String conf, Utilisateur utilisateur) {
		try {
			validationPass(pass, conf);
		} catch (FormException e) {
			setErreur(CHAMP_PASS, e.getMessage());
		}
		String cryptPass = BCrypt.withDefaults().hashToString(12, pass.toCharArray());	
		daoUtilisateur.modifierPass(utilisateur, cryptPass);
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
	
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }
	
}
