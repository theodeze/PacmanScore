package fr.univangers.pacman.score.beans;

import java.sql.Timestamp;

public class Partie {

	private long id;
	private long score;
	private Timestamp date;
	private String pseudo;
	private boolean victoire;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public boolean isVictoire() {
		return victoire;
	}
	public void setVictoire(boolean victoire) {
		this.victoire = victoire;
	}
	
}
