package fr.univangers.pacman.score.dao;

import fr.univangers.pacman.score.beans.Utilisateur;

public interface DAOUtilisateur {

	public static final String TABLE_NAME = "Utillisateur";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PASSWORD = "mot_de_passe";
	public static final String COLUMN_PSEUDO = "pseudo";
	public static final String COLUMN_DATE_INS = "date_inscription";
	
    void creer(Utilisateur utilisateur) throws DAOException;
    Utilisateur trouver(String identifiant) throws DAOException;
    void modifierEmail(Utilisateur utilisateur, String email) throws DAOException;
    void modifierPseudo(Utilisateur utilisateur, String pseudo) throws DAOException;
    void modifierPass(Utilisateur utilisateur, String motDePasse) throws DAOException;
    void supprimer(String email) throws DAOException;
	
}
