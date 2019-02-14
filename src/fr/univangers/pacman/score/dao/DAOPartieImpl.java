package fr.univangers.pacman.score.dao;

import static fr.univangers.pacman.score.dao.DAOUtilitaire.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    		+ COLUMN_ID			+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_SCORE		+ ", "
    		+ COLUMN_VICTORY	+ ", "
    		+ COLUMN_DATE		+ " FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_PSEUDO		+ " = ?";
    private static final String SQL_SELECT_PAR_ID = "SELECT "
    		+ COLUMN_ID			+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_SCORE		+ ", "
    		+ COLUMN_VICTORY	+ ", "
    		+ COLUMN_DATE		+ " FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_ID			+ " = ?";
    private static final String SQL_SELECT_PAR_DATE = "SELECT "
    		+ COLUMN_ID			+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_SCORE		+ ", "
    		+ COLUMN_VICTORY	+ ", "
    		+ COLUMN_DATE		+ " FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_DATE		+ " = ?";
    private static final String SQL_SELECT_TOUS = "SELECT "
    		+ COLUMN_ID			+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_SCORE		+ ", "
    		+ COLUMN_VICTORY	+ ", "
    		+ COLUMN_DATE		+ " FROM "
    		+ TABLE_NAME + " WHERE " + COLUMN_VICTORY + " IS NOT FALSE LIMIT 10";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_ID			+ " = ?";
    
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
	public List<Partie> trouverParPseudo(String pseudo) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Partie> parties = new ArrayList<>();

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_PSEUDO, false, pseudo);
	        resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()) {
	        	parties.add(map(resultSet));
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    
	    return parties;
	}
	
	@Override
	public List<Partie> trouverParDate(Timestamp date) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Partie> parties = new ArrayList<>();

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_DATE, false, date);
	        resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()) {
	        	parties.add(map(resultSet));
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    
	    return parties;
	}

	@Override
	public Partie trouver(long id) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Partie partie = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
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

	@Override
	public void supprimer(long id) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID, false, id);
	        preparedStatement.executeUpdate();
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(preparedStatement, connexion);
	    }
		
	}
	
	@Override
	public List<Partie> tous() throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Partie> parties = new ArrayList<>();

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_TOUS, false);
	        resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()) {
	        	parties.add(map(resultSet));
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    
	    return parties;
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
