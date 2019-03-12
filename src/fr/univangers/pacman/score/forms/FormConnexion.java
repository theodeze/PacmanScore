package fr.univangers.pacman.score.forms;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormConnexion {
    private static final String CHAMP_IDENTFIANT = "Identifiant_connexion";
    private static final String CHAMP_PASS       = "MotDePasse_connexion";

    private String              resultat;
    private DAOUtilisateur 		daoUtilisateur;
    
    public String getResultat() {
    	return resultat;
    }
    
    public FormConnexion(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
    
	public Utilisateur connecterUtilisateur(HttpServletRequest request) {
		String identifiant = request.getParameter(CHAMP_IDENTFIANT);
		String pass = request.getParameter(CHAMP_PASS);
		Utilisateur utilisateur;
		try {
			utilisateur = daoUtilisateur.trouver(identifiant);
			if(utilisateur != null) {
				BCrypt.Result bresultat = BCrypt.verifyer().verify(pass.toCharArray(), utilisateur.getMotDePasse());
				if(bresultat.verified) {
					resultat = "Connexion réussie";
				} else {
					resultat = "Connexion échouée";
					utilisateur = null;
				}
			} else {
				resultat = "Connexion échouée";
				utilisateur = null;
			}
		} catch (DAOException e) {
			utilisateur = null;
		}
	
		return utilisateur;
	}
}
