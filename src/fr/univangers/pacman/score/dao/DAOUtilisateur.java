package fr.univangers.pacman.score.dao;

import fr.univangers.pacman.score.beans.Utilisateur;

public interface DAOUtilisateur {

	public final static String TABLE_NAME = "Utillisateur";
	public final static String COLUMN_ID = "id";
	public final static String COLUMN_EMAIL = "email";
	public final static String COLUMN_PASSWORD = "mot_de_passe";
	public final static String COLUMN_PSEUDO = "pseudo";
	public final static String COLUMN_DATE_INS = "date_inscription";
	
    void creer(Utilisateur utilisateur) throws DAOException;
    Utilisateur trouver(String email) throws DAOException;
    void supprimer(String email) throws DAOException;
	
    /*
     * modifier le mot de passe
     * modifier email
     * modifier pseudo
     */
    
}
