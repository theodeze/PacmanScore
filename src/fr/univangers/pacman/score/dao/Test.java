package fr.univangers.pacman.score.dao;

import fr.univangers.pacman.score.beans.Utilisateur;

public class Test {

	public static void main(String[] args) {
		DAOUtilisateur daoUtilisateur = DAOFactory.getInstance().getDaoUtilisateur();
		Utilisateur u1 = new Utilisateur();
		u1.setEmail("marc@mail.fr");
		u1.setPseudo("Marc");
		u1.setMotDePasse("mdp");
		//daoUtilisateur.creer(u1);
		Utilisateur u2 = daoUtilisateur.trouver("marc2@mail.fr");
		System.out.println(u2.getDateInscription());
		//daoUtilisateur.supprimer(u2.getEmail());
	}

}
