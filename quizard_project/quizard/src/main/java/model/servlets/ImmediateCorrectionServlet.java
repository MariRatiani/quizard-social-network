package model.servlets;

import model.DAO.QuestionDAO;
import model.objects.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ImmediateCorrectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        String userAnswer = request.getParameter("answer");
        QuestionDAO questionDAO = (QuestionDAO) request.getServletContext().getAttribute("questionDAO");
        Question question = null;
        try {
            question = questionDAO.getQuestion(questionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> answers = question.getAnswers();
        String feedback = "Incorrect";
        for (String ans : answers) {
            if (userAnswer.equalsIgnoreCase(ans)) {
                feedback = "Correct";
                break;
            }
        }
        response.setContentType("text/plain");
        response.getWriter().write(feedback);
        request.getRequestDispatcher("/quiz-taking.jsp").forward(request, response);
    }
}

