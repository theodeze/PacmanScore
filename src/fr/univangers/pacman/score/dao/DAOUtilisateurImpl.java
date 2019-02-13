package fr.univangers.pacman.score.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.univangers.pacman.score.beans.Utilisateur;
import static fr.univangers.pacman.score.dao.DAOUtilitaire.*;

public class DAOUtilisateurImpl implements DAOUtilisateur {

    private DAOFactory daoFactory;
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS "
    		+ TABLE_NAME 		+ "("
    		+ COLUMN_ID			+ " INTEGER NOT NULL PRIMARY KEY, "
    		+ COLUMN_PSEUDO		+ " TEXT NOT NULL, "
    		+ COLUMN_EMAIL		+ " TEXT NOT NULL, "
    		+ COLUMN_PASSWORD	+ " TEXT NOT NULL, "
    		+ COLUMN_DATE_INS	+ " TIMESTAMP NOT NULL)";
    private static final String SQL_INSERT = "INSERT INTO "
    		+ TABLE_NAME 		+ "("
    		+ COLUMN_EMAIL 		+ ", "
    		+ COLUMN_PASSWORD 	+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_DATE_INS	+ ") VALUES(?, ?, ?, CURRENT_TIMESTAMP)";
    private static final String SQL_SELECT_PAR_IDENTIFIANT = "SELECT "
    		+ COLUMN_ID 		+ ", "
    		+ COLUMN_EMAIL 		+ ", "
    		+ COLUMN_PASSWORD 	+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_DATE_INS	+ " FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_PSEUDO		+ " = ? OR "
    	    + COLUMN_EMAIL		+ " = ?";
    private static final String SQL_DELETE_PAR_EMAIL = "DELETE FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_EMAIL		+ " = ?";
	private static final String SQL_UPDATE_PSEUDO = "UPDATE "
			+ TABLE_NAME		+ " SET "
			+ COLUMN_PSEUDO		+ " = ? WHERE "
		    + COLUMN_ID			+ " = ?";
	private static final String SQL_UPDATE_EMAIL = "UPDATE "
			+ TABLE_NAME		+ " SET "
			+ COLUMN_EMAIL		+ " = ? WHERE "
			+ COLUMN_ID			+ " = ?";
	private static final String SQL_UPDATE_PASSWORD = "UPDATE "
			+ TABLE_NAME		+ " SET "
			+ COLUMN_PASSWORD	+ " = ? WHERE "
			+ COLUMN_ID			+ " = ?";

    public DAOUtilisateurImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_CREATE, false);
	        int statut = preparedStatement.executeUpdate();
	        if(statut != 0) {
	            throw new DAOException("Echec de la création de la table partie.");
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(preparedStatement, connexion);
	    }
    }	

	@Override
	public void creer(Utilisateur utilisateur) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getMotDePasse(), utilisateur.getPseudo());
	        int statut = preparedStatement.executeUpdate();
	        if(statut == 0) {
	            throw new DAOException("Echec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
	        }
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if(valeursAutoGenerees.next()) {
	            utilisateur.setId(valeursAutoGenerees.getLong(1));
	        } else {
	            throw new DAOException("Echec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	    }
	}

	@Override
	public Utilisateur trouver(String identifiant) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Utilisateur utilisateur = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_IDENTIFIANT, false, identifiant, identifiant);
	        resultSet = preparedStatement.executeQuery();
	        if(resultSet.next()) {
	            utilisateur = map(resultSet);
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	    
	    return utilisateur;
	}

	@Override
	public void modifierEmail(Utilisateur utilisateur, String email) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_EMAIL, false, 
	        		email, utilisateur.getId());
	        preparedStatement.executeUpdate();
	        utilisateur.setEmail(email);
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(preparedStatement, connexion);
	    }
	}

	@Override
	public void modifierPseudo(Utilisateur utilisateur, String pseudo) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_PSEUDO, false, 
	        		pseudo, utilisateur.getId());
	        preparedStatement.executeUpdate();
	        utilisateur.setPseudo(pseudo);
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(preparedStatement, connexion);
	    }
	}

	@Override
	public void modifierPass(Utilisateur utilisateur, String motDePasse) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_PASSWORD, false, 
	        		motDePasse, utilisateur.getId());
	        preparedStatement.executeUpdate();
	        utilisateur.setMotDePasse(motDePasse);
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(preparedStatement, connexion);
	    }
	}

	@Override
	public void supprimer(String email) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PAR_EMAIL, false, email);
	        preparedStatement.executeUpdate();
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	    }
	}

	private static Utilisateur map(ResultSet resultSet) throws SQLException {
	    Utilisateur utilisateur = new Utilisateur();
	    utilisateur.setId(resultSet.getLong(COLUMN_ID));
	    utilisateur.setEmail(resultSet.getString(COLUMN_EMAIL));
	    utilisateur.setMotDePasse(resultSet.getString(COLUMN_PASSWORD));
	    utilisateur.setPseudo(resultSet.getString(COLUMN_PSEUDO));
	    utilisateur.setDateInscription(parseDate(resultSet.getString(COLUMN_DATE_INS)));
	    return utilisateur;
	}
	
}
