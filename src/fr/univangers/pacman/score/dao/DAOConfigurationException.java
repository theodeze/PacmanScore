package fr.univangers.pacman.score.dao;

public class DAOConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -2041254149340604341L;
	
	public DAOConfigurationException(String message) {
		super(message);
	}
	
	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}

}
