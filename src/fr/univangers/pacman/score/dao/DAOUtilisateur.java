package fr.univangers.pacman.score.dao;

import fr.univangers.pacman.score.beans.Utilisateur;

public interface DAOUtilisateur {
	
    void creer(Utilisateur utilisateur) throws DAOException;
    Utilisateur trouver(String identifiant) throws DAOException;
	Utilisateur trouverToken(String token) throws DAOException;
    void modifierEmail(Utilisateur utilisateur, String email) throws DAOException;
    void modifierPseudo(Utilisateur utilisateur, String pseudo) throws DAOException;
    void modifierPass(Utilisateur utilisateur, String motDePasse) throws DAOException;
	void modifierToken(Utilisateur utilisateur, String token) throws DAOException;
    void supprimer(String email) throws DAOException;
	
}
