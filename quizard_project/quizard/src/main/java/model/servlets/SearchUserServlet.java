package model.servlets;

import model.DAO.UserDAO;
import model.objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
     servlet declaration.
     When a user accesses the URL "/search_users" on our web application,
     the SearchUsersServlet's doGet method will be called
 */
@WebServlet(name = "SearchUserServlet", value = "/search_user")
public class SearchUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = (UserDAO) httpServletRequest.getServletContext().getAttribute("UserDAO");
        String results = httpServletRequest.getParameter("search-input-result");
        List<User> searchResults;
        if(results == null) searchResults = new ArrayList<>();
        else{
            try {
                searchResults = userDAO.searchUsers(results);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        httpServletRequest.setAttribute("searchResults", searchResults);
        httpServletRequest.getRequestDispatcher("webapp/WEB-INF/searchUser.jsp").forward(httpServletRequest,httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doPost(httpServletRequest, httpServletResponse);
    }
}
