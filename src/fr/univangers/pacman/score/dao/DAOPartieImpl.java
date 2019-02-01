package fr.univangers.pacman.score.dao;

import static fr.univangers.pacman.score.dao.DAOUtilitaire.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.univangers.pacman.score.beans.Partie;

public class DAOPartieImpl implements DAOPartie {

	private DAOFactory daoFactory;
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS "
    		+ TABLE_NAME 		+ "("
    		+ COLUMN_ID			+ " INTEGER NOT NULL PRIMARY KEY, "
    		+ COLUMN_PSEUDO		+ " TEXT NOT NULL, "
    		+ COLUMN_SCORE		+ " INT NOT NULL, "
    		+ COLUMN_VICTORY	+ " BOOLEAN NOT NULL, "
    		+ COLUMN_DATE		+ " TIMESTAMP NOT NULL)";
	private static final String SQL_INSERT = "INSERT INTO "
    		+ TABLE_NAME 		+ "("
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_SCORE		+ ", "
    		+ COLUMN_VICTORY	+ ", "
    		+ COLUMN_DATE		+ ") VALUES(?, ?, ?, CURRENT_TIMESTAMP)";
    private static final String SQL_SELECT_PAR_PSEUDO = "SELECT "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_SCORE		+ ", "
    		+ COLUMN_VICTORY	+ ", "
    		+ COLUMN_DATE		+ " FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_PSEUDO		+ " = ?";
    
    public DAOPartieImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_CREATE, false);
	        int statut = preparedStatement.executeUpdate();
	        if(statut != 0) {
	            throw new DAOException("Échec de la création de la table partie.");
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(preparedStatement, connexion);
	    }
	}
    
	@Override
	public void creer(Partie partie) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, partie.getPseudo(), partie.getScore(), partie.isVictoire());
	        int statut = preparedStatement.executeUpdate();
	        if(statut == 0) {
	            throw new DAOException("Échec de la création de la partie, aucune ligne ajoutée dans la table.");
	        }
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if(valeursAutoGenerees.next()) {
	            partie.setId(valeursAutoGenerees.getLong(1));
	        } else {
	            throw new DAOException("Échec de la création de la partie en base, aucun ID auto-généré retourné.");
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	    }
	}
	
	@Override
	public Partie trouver(String pseudo) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Partie partie = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_PSEUDO, false, pseudo);
	        resultSet = preparedStatement.executeQuery();
	        if(resultSet.next()) {
	        	partie = map(resultSet);
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    
	    return partie;
	}

	private static Partie map(ResultSet resultSet) throws SQLException {
		Partie partie = new Partie();
		partie.setId(resultSet.getLong(COLUMN_ID));
		partie.setScore(resultSet.getInt(COLUMN_SCORE));
		partie.setVictoire(resultSet.getBoolean(COLUMN_VICTORY));
	    partie.setPseudo(resultSet.getString(COLUMN_PSEUDO));
	    partie.setDate(parseDate(resultSet.getString(COLUMN_DATE)));
	    return partie;
	}
	
}
