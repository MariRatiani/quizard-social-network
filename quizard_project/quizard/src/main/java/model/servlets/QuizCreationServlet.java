package model.servlets;

import model.DAO.QuizDAO;
import model.objects.Quiz;
import model.objects.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
public class QuizCreationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null)
            req.getRequestDispatcher("login.jsp").forward(req, res);

        else
            req.getRequestDispatcher("quiz-creation.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =  request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int creatorId = user.getUser_id();

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String timerParam = request.getParameter("timeLimit");
        int timelimitInSec = timerParam == null ? 0 : Integer.parseInt(timerParam);

        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");

        int quizId;
        try {
            quizId = quizDAO.addQuiz(title, description, category, creatorId,
                    Boolean.getBoolean(request.getParameter("randomOrder")),
                    Boolean.getBoolean(request.getParameter("QuestionsOnOnePage")),
                    Boolean.getBoolean(request.getParameter("immediateCorrection")),
                    Boolean.getBoolean(request.getParameter("practiceMode")),
                    LocalDateTime.now(), 0, 0, timelimitInSec);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("quizId", quizId);
        request.setAttribute("creatorId", creatorId);
        request.getRequestDispatcher("/add-question.jsp").forward(request, response);
    }
}