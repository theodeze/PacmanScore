package fr.univangers.pacman.score.beans;

import java.sql.Timestamp;

public class Utilisateur {

	private long id;
	private String email;
	private String motDePasse;
	private String pseudo;
    private Timestamp dateInscription;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Timestamp getDateInscription() {
		return dateInscription;
	}
	public void setDateInscription(Timestamp dateInscription) {
		this.dateInscription = dateInscription;
	}
	
}
