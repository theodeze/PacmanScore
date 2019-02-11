package fr.univangers.pacman.score.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.univangers.pacman.score.config.InitialisationDaoFactory;
import fr.univangers.pacman.score.dao.DAOFactory;
import fr.univangers.pacman.score.dao.DAOPartie;
import fr.univangers.pacman.score.forms.FormPartie;

@WebServlet("/Partie/*")
public class Partie extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger("Partie Servlet");
    
    private DAOPartie daoPartie;
	
    @Override
    public void init() throws ServletException {
    	super.init();
        this.daoPartie = ((DAOFactory) getServletContext().getAttribute(
        		InitialisationDaoFactory.ATT_DAO_FACTORY)).getDaoPartie();
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
        FormPartie form = new FormPartie(daoPartie);
        
		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){
			sendJson(response, form.get(request));
			return;
		}

		String[] splits = pathInfo.split("/");
		
		if(splits.length != 2) {
			sendError(response, HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int id = 0;
		try {
			id = Integer.parseInt(splits[1]);
		} catch(NumberFormatException e) {
			sendError(response, HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String json = form.get(id);
		if(json.equals("null")) {	
			sendError(response, HttpServletResponse.SC_NOT_FOUND);
			return;
		}
        sendJson(response, json);
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FormPartie form = new FormPartie(daoPartie);
        sendJson(response, form.post(request));
	}

    @Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Modify value */
	}

    @Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FormPartie form = new FormPartie(daoPartie);

		String pathInfo = request.getPathInfo();

		String[] splits = pathInfo.split("/");
		if(splits.length != 2) {
			sendError(response, HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int id = 0;
		try {
			id = Integer.parseInt(splits[1]);
		} catch(NumberFormatException e) {
			sendError(response, HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String json = form.get(id);
		if(json.equals("null")) {	
			sendError(response, HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		form.delete(id);
		sendJson(response, json);
	}

}
