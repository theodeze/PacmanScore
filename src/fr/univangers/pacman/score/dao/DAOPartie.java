package fr.univangers.pacman.score.dao;

import java.sql.Timestamp;
import java.util.List;

import fr.univangers.pacman.score.beans.Partie;

public interface DAOPartie {

	public static final String TABLE_NAME 		= "Partie";
	public static final String COLUMN_ID 		= "id";
	public static final String COLUMN_SCORE 	= "score";
	public static final String COLUMN_PSEUDO 	= "pseudo";
	public static final String COLUMN_DATE 		= "date";
	public static final String COLUMN_VICTORY 	= "victoire";

    void creer(Partie partie) throws DAOException;
    Partie trouver(long id) throws DAOException;
    List<Partie> trouverParPseudo(String pseudo) throws DAOException;
    void supprimer(long id) throws DAOException;
    
    List<Partie> trouverParDate(Timestamp date) throws DAOException;
    List<Partie> tous() throws DAOException;
    
}
