package fr.univangers.pacman.score.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DAOUtilitaire {
	
	public static void fermetureSilencieuse(ResultSet resultSet) {
	    if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException e) {
	            System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
	        }
	    }
	}

	public static void fermetureSilencieuse(Statement statement) {
	    if (statement != null) {
	        try {
	            statement.close();
	        } catch (SQLException e) {
	            System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
	        }
	    }
	}

	public static void fermetureSilencieuse(Connection connexion) {
	    if (connexion != null) {
	        try {
	            connexion.close();
	        } catch (SQLException e) {
	            System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
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

}
