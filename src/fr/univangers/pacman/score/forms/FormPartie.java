package fr.univangers.pacman.score.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.univangers.pacman.score.beans.Partie;
import fr.univangers.pacman.score.beans.Utilisateur;
import fr.univangers.pacman.score.dao.DAOException;
import fr.univangers.pacman.score.dao.DAOPartie;
import fr.univangers.pacman.score.dao.DAOUtilisateur;

public class FormPartie {
	private static final Logger LOGGER 			= LogManager.getLogger("Form Partie");
    private static final String CHAMP_TOKEN 	= "token";
    private static final String CHAMP_PSEUDO 	= "pseudo";
    private static final String CHAMP_SCORE  	= "score";
    private static final String CHAMP_VICTOIRE	= "victoire";

	private DAOPartie daoPartie;
	private DAOUtilisateur daoUtilisateur;
    
    public FormPartie(DAOPartie daoPartie, DAOUtilisateur daoUtilisateur) {
        this.daoPartie = daoPartie;
        this.daoUtilisateur = daoUtilisateur;
    }
    
	public String get(HttpServletRequest request) {
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		List<Partie> resultat = null;
		try {
			if(pseudo != null)
				resultat = daoPartie.trouverParPseudo(pseudo);
			else
				resultat = daoPartie.tous();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return new GsonBuilder().setPrettyPrinting().create().toJson(resultat);
	}
    
	public String get(long id) {
		Partie resultat;
		try {
			resultat = daoPartie.trouver(id);
		} catch (DAOException e) {
			resultat = null;
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(resultat);
	}
	
	public String post(HttpServletRequest request) {
		Partie partie = new Partie();
		String token = request.getParameter(CHAMP_TOKEN);
		Utilisateur utilisateur;
		try {
			utilisateur = daoUtilisateur.trouverToken(token);
			if(utilisateur != null) {
				partie.setPseudo(utilisateur.getPseudo());
				partie.setScore(Integer.valueOf(request.getParameter(CHAMP_SCORE)));
				partie.setVictoire(Boolean.valueOf(request.getParameter(CHAMP_VICTOIRE)));
				daoPartie.creer(partie);
			} else {
				partie = null;
			}
		} catch (DAOException e1) {
			partie = null;
		}
		return new GsonBuilder().setPrettyPrinting().create().toJson(partie);
	}
	
	public void delete(long id) {
		try {
			daoPartie.supprimer(id);
		} catch (DAOException e) {
			LOGGER.warn(e.getMessage());
		}
	}
	
}
