package model.servlets;

import model.DAO.QuizDAO;
import model.objects.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import model.DAO.QuestionDAO;
import model.objects.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class QuizStartingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = user.getUser_id();

        QuestionDAO questionDAO = (QuestionDAO) getServletContext().getAttribute("questionDAO");
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        request.setAttribute("quizId", quizId);
        LocalDateTime startTime = LocalDateTime.now();
        session.setAttribute("start_time", startTime);
        Quiz quiz = null;
        try {
            quiz = (Quiz) quizDAO.getQuiz(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        boolean multiple_pages = quiz.displayOnMultiplePages();
        boolean random_order = quiz.isRandomOrder();

        String practiceMode = request.getParameter("practiceMode");
        boolean isPracticeMode = Boolean.parseBoolean(practiceMode);

        ArrayList<Integer> questions = null;
        try {
            questions = (ArrayList<Integer>) questionDAO.getQuestionsOfQuiz(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (questions.isEmpty()) {
            //request.getRequestDispatcher("/addQuestion").forward(request, response);
        } else {
            if (isPracticeMode) {
                HashMap<Integer, Integer> questionRepeatNum = new HashMap<Integer, Integer>();
                int frequency = 3;
                for (int i : questions) {
                    questionRepeatNum.put(i, frequency);
                }
                session.setAttribute("questionRepeatNum", questionRepeatNum);
            }
            if (random_order) Collections.shuffle(questions);
            request.setAttribute("questions", questions);

            request.getRequestDispatcher("quiz-taking.jsp").forward(request, response);
        }
    }
}
