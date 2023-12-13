package model.servlets;

import model.DAO.QuizDaoInterface;
import model.DAO.QuizPerformanceDAO;
import model.DAO.UserDAO;
import model.objects.Quiz;
import model.objects.QuizPerformance;
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

@WebServlet(name = "QuizServlet", value = "/quiz")
public class QuizServlet extends HttpServlet {

    private List<User> convertToUser(List<QuizPerformance> list, UserDAO userDAO){
        List<User> res = new ArrayList<>();
        for(QuizPerformance cur : list){
            int id = cur.getUserId();
            User toAdd = null;
            try {
                toAdd = userDAO.getUser(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            res.add(toAdd);
        }
        return res;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Quiz quiz = (Quiz)req.getAttribute("toTakeQuiz");
//        QuizPerformanceDAO quizPerformanceDAO = (QuizPerformanceDAO)req.getServletContext().getAttribute("quizPerformanceDAO");
//        UserDAO userDAO = (UserDAO)req.getServletContext().getAttribute("userDAO");
//
//        List<QuizPerformance> perfs = quizPerformanceDAO.getBestPerformances(quiz.getQuizId(), false);
//        List<QuizPerformance> todayPerfs = quizPerformanceDAO.getBestPerformances(quiz.getQuizId(), true);
//        List<QuizPerformance> recentPerfs = quizPerformanceDAO.getRecentPerformances(quiz.getQuizId());
//
//        //create List of users who had highest performances on this quiz
//        List<User> bestPerformers = convertToUser(perfs, userDAO);
//        //create List of users who had highest performances on this quiz today
//        List<User> todayBestPerfs = convertToUser(todayPerfs, userDAO);
//        //create List of users who has taken this quz recently
//        List<User> recentPerformances = convertToUser(recentPerfs, userDAO);
//
//        req.setAttribute("bestPerformers", bestPerformers);
//        req.setAttribute("bestPerformersOfToday", todayBestPerfs);
//        req.setAttribute("recentPerformances", recentPerformances);
        req.getRequestDispatcher("WEB-INF/quiz.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/quiz.jsp").forward(req, resp);

    }
}
