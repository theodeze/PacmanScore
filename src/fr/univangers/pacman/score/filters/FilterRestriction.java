package fr.univangers.pacman.score.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/FilterRestriction")
public class FilterRestriction implements Filter {

	private static final String ATT_SESSION_USER = "";
	private static final String ACCES_PUBLIC	 = "";
	
    public FilterRestriction() { /* */ }

    @Override
	public void destroy() { /* */ }

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        if (session.getAttribute(ATT_SESSION_USER) == null) {
            response.sendRedirect(request.getContextPath() + ACCES_PUBLIC);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
	public void init(FilterConfig fConfig) throws ServletException { /* */ }

}
