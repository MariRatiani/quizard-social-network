package model.filters;
import model.DAO.UserDAO;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ProfileFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //System.out.println("got in profile filter");
        try {
            if (request.getParameter("user_id") == null || ((UserDAO)request.getServletContext().getAttribute("userDAO")).getUser(Integer.parseInt(request.getParameter("user_id"))) == null) {
                ((HttpServletResponse)response).sendRedirect("homepage.jsp");
            } else {
                chain.doFilter(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            ((HttpServletResponse)response).sendRedirect("error.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}