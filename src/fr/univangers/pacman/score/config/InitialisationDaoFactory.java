package fr.univangers.pacman.score.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.univangers.pacman.score.dao.DAOFactory;

@WebListener
public class InitialisationDaoFactory implements ServletContextListener {

    private static final String ATT_DAO_FACTORY = "daofactory";

    private DAOFactory daoFactory;
	
    public InitialisationDaoFactory() {}

    public void contextDestroyed(ServletContextEvent sce)  {}

    public void contextInitialized(ServletContextEvent sce)  { 
        ServletContext servletContext = sce.getServletContext();
        this.daoFactory = DAOFactory.getInstance();
        servletContext.setAttribute(ATT_DAO_FACTORY, this.daoFactory);
    }
	
}
