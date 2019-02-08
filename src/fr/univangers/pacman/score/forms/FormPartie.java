package fr.univangers.pacman.score.forms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.univangers.pacman.score.beans.Partie;
import fr.univangers.pacman.score.dao.DAOPartie;

public class FormPartie {
    private static final String CHAMP_PSEUDO 	= "pseudo";
    private static final String CHAMP_SCORE  	= "score";
    private static final String CHAMP_VICTOIRE	= "victoire";

	private DAOPartie daoPartie;
    
    public FormPartie(DAOPartie daoPartie) {
        this.daoPartie = daoPartie;
    }
    
	public String get(HttpServletRequest request) {
		List<Partie> resultat = daoPartie.tous();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(resultat);
	}
	
	public String post(HttpServletRequest request) {
		Partie partie = new Partie();
		partie.setPseudo(request.getParameter(CHAMP_PSEUDO));
		partie.setScore(Integer.valueOf(request.getParameter(CHAMP_SCORE)));
		partie.setVictoire(Boolean.valueOf(request.getParameter(CHAMP_VICTOIRE)));
		daoPartie.creer(partie);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(partie);
	}
	
}
