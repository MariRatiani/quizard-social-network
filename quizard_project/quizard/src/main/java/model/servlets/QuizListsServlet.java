package model.servlets;

import com.sun.net.httpserver.HttpContext;
import model.DAO.QuizDAO;
import model.DAO.QuizPerformanceDAO;
import model.objects.Quiz;
import model.objects.QuizPerformance;
import model.objects.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Past;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuizListsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = user.getUser_id();

        int quizId = Integer.parseInt(request.getParameter("quizId"));

        String listType = request.getParameter("buttonClicked");
        List<QuizPerformance> quizList = new ArrayList<>();
        ServletContext context = getServletContext();
        QuizPerformanceDAO quizPerformanceDAO = (QuizPerformanceDAO) context.getAttribute("quizPerformanceDAO");
        String listName = "";
        if ("past-performances".equals(listType)) {
            try {
                quizList = quizPerformanceDAO.getUserLastPerformances(userId, 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            listName = "Your Past Performances";
        } else if ("top-performances".equals(listType)) {
            try {
                quizList = quizPerformanceDAO.getBestPerformancesInTimeRange(userId, LocalDateTime.now().minusYears(1), LocalDateTime.now(), 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            listName = "Top Performances";

        } else if ("top-last-day".equals(listType)) {
            try {
                quizList = quizPerformanceDAO.getBestPerformancesInTimeRange(quizId, LocalDateTime.now().minusDays(1), LocalDateTime.now(), 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            listName = "Top Performances of Last Day";

        } else if ("recent-quiz-taking".equals(listType)) {
            try {
                quizList = quizPerformanceDAO.getRecentPerformances(quizId, 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            listName = "Recent Quiz Taking Activities";

        } else if ("statistics".equals(listType)) {
            try {
                quizList = quizPerformanceDAO.getPerformances(userId, quizId, true, true, true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            listName = "Quiz Statistics";
        }
        request.setAttribute("performancelist", quizList);
        request.setAttribute("listName", listName);
        request.getRequestDispatcher("quiz-lists.jsp").forward(request, response);
    }
}
