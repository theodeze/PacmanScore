package fr.univangers.pacman.score.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.univangers.pacman.score.beans.Utilisateur;
import static fr.univangers.pacman.score.dao.DAOUtilitaire.*;

public class DAOUtilisateurImpl implements DAOUtilisateur {

    private DAOFactory daoFactory;
    private static final String SQL_INSERT = "INSERT INTO "
    		+ TABLE_NAME 		+ "("
    		+ COLUMN_EMAIL 		+ ", "
    		+ COLUMN_PASSWORD 	+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_DATE_INS	+ ") VALUES(?, ?, ?, NOW())";
    private static final String SQL_SELECT_PAR_EMAIL = "SELECT "
    		+ COLUMN_ID 		+ ", "
    		+ COLUMN_EMAIL 		+ ", "
    		+ COLUMN_PASSWORD 	+ ", "
    		+ COLUMN_PSEUDO		+ ", "
    		+ COLUMN_DATE_INS	+ " FROM "
    		+ TABLE_NAME		+ " WHERE "
    		+ COLUMN_EMAIL		+ " = ?";

    public DAOUtilisateurImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
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
	            throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
	        }
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if(valeursAutoGenerees.next()) {
	            utilisateur.setId(valeursAutoGenerees.getLong(1));
	        } else {
	            throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
	        }
	    } catch(SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
	    }
	}

	@Override
	public Utilisateur trouver(String email) throws DAOException {
	    Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Utilisateur utilisateur = null;

	    try {
	        connexion = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_EMAIL, false, email);
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

	private static Utilisateur map(ResultSet resultSet) throws SQLException {
	    Utilisateur utilisateur = new Utilisateur();
	    utilisateur.setId(resultSet.getLong(COLUMN_ID));
	    utilisateur.setEmail(resultSet.getString(COLUMN_EMAIL));
	    utilisateur.setMotDePasse(resultSet.getString(COLUMN_PASSWORD));
	    utilisateur.setPseudo(resultSet.getString(COLUMN_PSEUDO));
	    utilisateur.setDateInscription(resultSet.getTimestamp(COLUMN_DATE_INS));
	    return utilisateur;
	}
	
}
