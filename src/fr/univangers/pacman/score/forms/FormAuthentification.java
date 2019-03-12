package fr.univangers.pacman.score.forms;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormAuthentification {
    private static final String CHAMP_USER  	= "username";
    private static final String CHAMP_PASS   	= "password";

    private String              resultat;
    private DAOUtilisateur 		daoUtilisateur;
    
    public String getResultat() {
    	return resultat;
    }
    
    public FormAuthentification(DAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }
    
    public String newToken() throws DAOException {
    	String token;
    	do {
    		token = UUID.randomUUID().toString();
    	} while(daoUtilisateur.trouverToken(token) != null);
    	return token;
    }
    
    public String post(HttpServletRequest request) {
		String identifiant = request.getParameter(CHAMP_USER);
		String motDePasse  = request.getParameter(CHAMP_PASS);
		String token = null;
		Utilisateur utilisateur;
		try {
			utilisateur = daoUtilisateur.trouver(identifiant);
			if(utilisateur != null) {
				BCrypt.Result bresultat = BCrypt.verifyer().verify(motDePasse.toCharArray(), utilisateur.getMotDePasse());
				if(bresultat.verified) {
					resultat = "Connexion réussie";
					String stoken = newToken();
					daoUtilisateur.modifierToken(utilisateur, stoken);
					token = new Gson().toJson(stoken);
				} else {
					resultat = "Connexion échouée";
					token = null;
				}
			} else {
				resultat = "Connexion échouée";
				token = null;
			}
		} catch (DAOException e) {
			token = null;
		}
		return token;
    }
    
}
