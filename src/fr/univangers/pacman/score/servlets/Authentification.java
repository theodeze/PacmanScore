package fr.univangers.pacman.score.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import fr.univangers.pacman.score.config.InitialisationDaoFactory;
import fr.univangers.pacman.score.dao.DAOFactory;
import fr.univangers.pacman.score.dao.DAOUtilisateur;
import fr.univangers.pacman.score.forms.FormAuthentification;

/**
 * Servlet implementation class Authentification
 */
@WebServlet("/Authentification")
public class Authentification extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger("Authentification Servlet");
    
    private DAOUtilisateur daoUtilisateur;
	
    @Override
    public void init() throws ServletException {
    	super.init();
        this.daoUtilisateur = ((DAOFactory) getServletContext().getAttribute(
        		InitialisationDaoFactory.ATT_DAO_FACTORY)).getDaoUtilisateur();
    }
    
    public Authentification() {
        super();
    }
    
    private void sendJson(HttpServletResponse response, String json) {
        try {
        	response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			LOGGER.warn("Problème envoie Json");
		}
    }
    
    private void sendError(HttpServletResponse response, int sc) {
		try {
			response.sendError(sc);
		} catch (IOException e) {
			LOGGER.warn("Problème envoie erreur");
		}
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendJson(response, new Gson().toJson("API Authentification"));
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FormAuthentification form = new FormAuthentification(daoUtilisateur);
		String token = form.post(request);
		if(token == null) {
			sendError(response, HttpServletResponse.SC_NOT_FOUND);
			return;
		}
        sendJson(response, token);
	}

}
