package fr.univangers.pacman.score.dao;

import java.sql.Timestamp;
import java.util.List;

import fr.univangers.pacman.score.beans.Partie;

public interface DAOPartie {

	public final static String TABLE_NAME = "Partie";
	public final static String COLUMN_ID = "id";
	public final static String COLUMN_SCORE = "score";
	public final static String COLUMN_PSEUDO = "pseudo";
	public final static String COLUMN_DATE = "date";
	public final static String COLUMN_VICTORY = "victoire";

    void creer(Partie partie) throws DAOException;
    Partie trouver(String pseudo) throws DAOException;
    List<Partie> trouverParDate(Timestamp date) throws DAOException;
    List<Partie> tous() throws DAOException;
    
}
