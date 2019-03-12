package fr.univangers.pacman.score.dao;

public class DAOException extends Exception {

	private static final long serialVersionUID = -2371259972304276718L;
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DAOException(Throwable cause) {
		super(cause);
	}
	
}
