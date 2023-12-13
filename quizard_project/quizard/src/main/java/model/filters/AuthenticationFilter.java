package model.filters;
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

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //System.out.println("got in authentication filter");
        if (((HttpServletRequest)request).getSession().getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).sendRedirect("login.jsp");
        }
    }
    @Override
    public void destroy() {
    }
}