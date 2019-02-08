package fr.univangers.pacman.score.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DAOFactory {

    private static final String FICHIER_PROPERTIES       = "fr/univangers/pacman/score/dao/DAO.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
    private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";
	
	private DataSource datasource;
	
	private DAOFactory(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);
        
        if(fichierProperties == null) {
            throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
        }

        try {
            properties.load(fichierProperties);
            url = properties.getProperty(PROPERTY_URL);
            driver = properties.getProperty(PROPERTY_DRIVER);
            nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
            motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
        } catch(IOException e) {
            throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
        }
        
        try {
            Class.forName(driver);
        } catch(ClassNotFoundException e) {
            throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
        }
        
        PoolProperties p = new PoolProperties();
        p.setUrl(url);
        p.setDriverClassName(driver);
        p.setUsername(nomUtilisateur);
        p.setPassword(motDePasse);
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        
        DAOFactory instance = new DAOFactory(datasource);

        return instance;
	}
	
    Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
	
    public DAOUtilisateur getDaoUtilisateur() {
        return new DAOUtilisateurImpl(this);
    }
	
    public DAOPartie getDaoPartie() {
        return new DAOPartieImpl(this);
    }
    
}
