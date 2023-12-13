package model.servlets;
import model.DAO.AnnouncementDAO;
import model.DAO.QuizCategoryDAO;
import model.DAO.UserDAO;
import model.objects.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.http.HttpSession;

public class AdminOrRemove extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("admin-page.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryName = request.getParameter("categoryname");
        String option = request.getParameter("submitButton");
        ServletContext context = request.getServletContext();
        int adminID = ((User)request.getSession().getAttribute("user")).getUser_id();
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
        AnnouncementDAO announcementDAO = (AnnouncementDAO) context.getAttribute("announcementDAO");
        QuizCategoryDAO quizCategoryDAO = (QuizCategoryDAO) context.getAttribute("categoryDAO");
        try {
            if (option.equals("make admin")) {
                userDAO.makeAdmin(username);
            }
            if (option.equals("delete user")){
                boolean is = false;
                if (((User) request.getSession().getAttribute("user")).getName().equals(username)) is = true;
                userDAO.removeUser(username);
                if (is) {
                    request.getSession().invalidate();
                    response.sendRedirect("login.jsp");
                    return;
                }
            }
            if (option.equals("remove announcement")){
                if (request.getParameter("toremove").equals("")) {
                    response.sendRedirect("admin-page.jsp?error=true");
                    return;
                }
                int announcementID = Integer.parseInt(request.getParameter("toremove"));
                announcementDAO.deleteAnnouncement(announcementID);
            }
            if (option.equals("add announcement")){
                if (title.equals("") || description.equals("")) {
                    response.sendRedirect("admin-page.jsp?error=true");
                    return;
                }
                announcementDAO.addAnnouncement(adminID, title, description, new Timestamp(System.currentTimeMillis()));
            }
            if (option.equals("add category")){
                quizCategoryDAO.addQuizCategory(categoryName);
            } else {
                quizCategoryDAO.deleteQuizCategory(categoryName);
            }
            response.sendRedirect("admin-page.jsp?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            // response.sendRedirect("error.jsp");
        }
    }
}