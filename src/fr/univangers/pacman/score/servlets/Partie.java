package fr.univangers.pacman.score.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.univangers.pacman.score.config.InitialisationDaoFactory;
import fr.univangers.pacman.score.dao.DAOFactory;
import fr.univangers.pacman.score.dao.DAOPartie;
import fr.univangers.pacman.score.forms.FormPartie;

@WebServlet("/Partie")
public class Partie extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DAOPartie daoPartie;
	
	
    @Override
    public void init() throws ServletException {
    	super.init();
        this.daoPartie = ((DAOFactory) getServletContext().getAttribute(
        		InitialisationDaoFactory.ATT_DAO_FACTORY)).getDaoPartie();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FormPartie form = new FormPartie(daoPartie);
        String json = form.get(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FormPartie form = new FormPartie(daoPartie);
        String json = form.post(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
