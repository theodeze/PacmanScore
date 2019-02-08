package fr.univangers.pacman.score.dao;

import fr.univangers.pacman.score.beans.Partie;
import fr.univangers.pacman.score.beans.Utilisateur;

public class Test {

	public static void main(String[] args) {
		DAOPartie dao = DAOFactory.getInstance().getDaoPartie();
		Partie p = new Partie();
		p.setPseudo("Marc");
		p.setScore(2555);
		p.setVictoire(true);
		dao.creer(p);
	}

}
