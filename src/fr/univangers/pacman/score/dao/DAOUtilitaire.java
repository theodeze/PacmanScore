package fr.univangers.pacman.score.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DAOUtilitaire {
	
    private static final Logger LOGGER = LogManager.getLogger("DAO Utilitaire");

    private DAOUtilitaire() {}
    
	public static void fermetureSilencieuse(ResultSet resultSet) {
	    if(resultSet != null) {
	        try {
	            resultSet.close();
	        } catch(SQLException e) {
	        	LOGGER.warn("Échec de la fermeture du ResultSet : " + e.getMessage());
	        }
	    }
	}

	public static void fermetureSilencieuse(Statement statement) {
	    if(statement != null) {
	        try {
	            statement.close();
	        } catch(SQLException e) {
	        	LOGGER.warn("Échec de la fermeture du Statement : " + e.getMessage());
	        }
	    }
	}

	public static void fermetureSilencieuse(Connection connexion) {
	    if(connexion != null) {
	        try {
	            connexion.close();
	        } catch(SQLException e) {
	        	LOGGER.warn("Échec de la fermeture de la connexion : " + e.getMessage());
	        }
	    }
	}

	public static void fermeturesSilencieuses(Statement statement, Connection connexion) {
	    fermetureSilencieuse(statement);
	    fermetureSilencieuse(connexion);
	}

	public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) {
	    fermetureSilencieuse(resultSet);
	    fermetureSilencieuse(statement);
	    fermetureSilencieuse(connexion);
	}
	
    public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for(int i = 0; i < objets.length; i++) {
            preparedStatement.setObject(i + 1, objets[i]);
        }
        return preparedStatement;
    }
    
    public static Timestamp parseDate(String date) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long time = 0;
    	try {
    		time = formatter.parse(date).getTime();
		} catch (ParseException e) {
			LOGGER.warn("Problème format date");
		}
		return new Timestamp(time);
    }

}
