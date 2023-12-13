package model.filters;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter("/*")
public class CacheControlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //System.out.println("got in cache control filter");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.addHeader("Pragma", "no-cache");
        httpServletResponse.addHeader("Expires", "0");

        chain.doFilter(request, httpServletResponse);
    }
    @Override
    public void destroy() {
    }
}