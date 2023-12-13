package model.servlets;

import model.DAO.UserDAO;
import model.objects.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        ServletContext context = request.getServletContext();
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
        //response.sendRedirect("register.jsp?error=username_taken");
        try {
            int registrationResult = userDAO.addUser(username, email, password);
            /// addUser needs extra logic for below commented code
            /*
            if (registrationResult == userDAO.username_taken) {
                response.sendRedirect("register.jsp?error=username_taken");
                return;
            }
            if (registrationResult == userDAO.email_taken) {
                response.sendRedirect("register.jsp?error=email_taken");
                return;
            }
             */
            if (registrationResult == -1) {
                response.sendRedirect("register.jsp?error=username_taken");
                return;
            }
            User added = userDAO.getUser(registrationResult);
            //HttpSession session = request.getSession();
            //session.setAttribute("user", added);
            response.sendRedirect("login.jsp?congratulations="+added.getName());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
