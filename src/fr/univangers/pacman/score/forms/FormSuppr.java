package fr.univangers.pacman.score.forms;

import javax.servlet.http.HttpServletRequest;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOUtilisateur;


public class FormSuppr {

	 	private static final String CHAMP_IDENTFIANT = "Identifiant_suppr";
	    private static final String CHAMP_PASS       = "MotDePasse_suppr";

	    private String              resultat;
	    private DAOUtilisateur 		daoUtilisateur;
	    
	    public String getResultat() {
	    	return resultat;
	    }
	    
	    public FormSuppr(DAOUtilisateur daoUtilisateur) {
	        this.daoUtilisateur = daoUtilisateur;
	    }
	    
		public Utilisateur SupprimerUtilisateur(HttpServletRequest request) {
			String identifiant = (String) request.getParameter(CHAMP_IDENTFIANT);
			String pass = (String) request.getParameter(CHAMP_PASS);
			Utilisateur utilisateur = daoUtilisateur.trouver(identifiant);
			if(utilisateur != null) {
				BCrypt.Result bresultat = BCrypt.verifyer().verify(pass.toCharArray(), utilisateur.getMotDePasse());
				if(bresultat.verified) {
					daoUtilisateur.supprimer(identifiant);
					resultat = "Suppression réussie";
					utilisateur = null;
				} else {
					resultat = "Suppression échouée";
				}
			} else {
				resultat = "Suppression échouée";
			}
			return utilisateur;
		}
}
