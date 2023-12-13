package model.servlets;

import model.DAO.QuizPerformanceDAO;
import model.objects.QuizPerformance;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "QuizView", value = "/quizzes/view")
public class QuizSummaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       req.getRequestDispatcher("/quiz-summary.jsp").forward(req, res);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clickedButton = request.getParameter("clickedButton");
        ServletContext context = request.getServletContext();
        QuizPerformanceDAO quizQuizPerformanceDAO = (QuizPerformanceDAO)context.getAttribute("quizPerformanceDAO");

        List<QuizPerformance> list = null;
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        int userId = user.getUser_id();
        int userId = 1;
//        int quizId = (int) request.getAttribute("quizId");
        int quizId = 1;

        if ("past-performances".equals(clickedButton)) {
            try {
                list = quizQuizPerformanceDAO.getUserLastPerformances(userId, 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("top-performances".equals(clickedButton)) {
            try {
                list = quizQuizPerformanceDAO.getBestPerformancesInTimeRange(userId,LocalDateTime.now().minusYears(1), LocalDateTime.now(), 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("top-last-day".equals(clickedButton)) {
            try {
                list = quizQuizPerformanceDAO.getBestPerformancesInTimeRange(quizId, LocalDateTime.now().minusDays(1), LocalDateTime.now(), 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("recent-quiz-taking".equals(clickedButton)) {
            try {
                list = quizQuizPerformanceDAO.getRecentPerformances(quizId, 10);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if ("statistics".equals(clickedButton)) {
            try {
                list = quizQuizPerformanceDAO.getPerformances(userId, quizId, true, true, true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("/quiz-lists.jsp").forward(request, response);
    }
}