package fr.univangers.pacman.score.dao;

import fr.univangers.pacman.score.beans.Partie;

public class Test {

	public static void main(String[] args) {
		DAOPartie daoPartie = DAOFactory.getInstance().getDaoPartie();
		Partie partie = new Partie();
		partie.setPseudo("Marc");
		partie.setVictoire(true);
		partie.setScore(500);
		daoPartie.creer(partie);
	}

}
