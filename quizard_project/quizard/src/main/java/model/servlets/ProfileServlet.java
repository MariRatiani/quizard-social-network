package model.servlets;
import model.DAO.MessageDAO;
import model.DAO.QuizDAO;
import model.DAO.UserDAO;
import model.enums.MessageType;
import model.objects.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProfileServlet", value = "/profileServlet")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println(request.getParameter("id"));
        int userID = Integer.parseInt(request.getParameter("id").trim());

        String note = request.getParameter("note");
        String option = request.getParameter("submitButton");
        ServletContext context = request.getServletContext();
        //System.out.println(userID);
        MessageDAO messageDAO = (MessageDAO) context.getAttribute("messageDAO");
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
        boolean is = false;
        try {
            if (option.equals("Delete account")) {
                userDAO.removeUser(((User)request.getSession().getAttribute("user")).getName());
                request.getSession().invalidate();
                response.sendRedirect("login.jsp");
                return;
            }
            if (option.equals("Send message")) {
                messageDAO.addMessage(((User)request.getSession().getAttribute("user")).getUser_id(), userID, MessageType.NOTE, note, new Timestamp(System.currentTimeMillis()));
                is = true;
            }
            if (option.equals("Accept request") && !is) {
                userDAO.addFriend(((User)request.getSession().getAttribute("user")).getUser_id(), userID, java.time.LocalDateTime.now());
                is = true;
            }
            if (option.equals("Remove quiz") && !is) {
                int quizID = Integer.parseInt(request.getParameter("quizID"));
                quizDAO.removeQuiz(quizID);
                is = true;
            }
            if (option.equals("Remove friend") && !is) {
                userDAO.removeFriend(((User)request.getSession().getAttribute("user")).getUser_id(), userID);
                is = true;
            } else {
                if (!is)
                    userDAO.sendFriendRequest(userID, ((User)request.getSession().getAttribute("user")).getUser_id());
            }
            response.sendRedirect("profile.jsp?user_id="+userID);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
            response.sendRedirect("error.jsp");
        }
    }

}