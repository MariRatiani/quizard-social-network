package model.filters;
import model.objects.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //System.out.println("got in admin filter");
        if (((User)(((HttpServletRequest)request).getSession().getAttribute("user"))).isAdmin()) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).sendRedirect("homepage.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}