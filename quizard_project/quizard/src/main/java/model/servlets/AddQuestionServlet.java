package model.servlets;

import model.DAO.QuestionDAO;
import model.DAO.QuizDAO;
import model.enums.QuestionType;
import model.objects.Quiz;
import model.objects.User;
;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static model.enums.QuestionType.*;

public class AddQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        if (session != null && user != null) {
            req.getRequestDispatcher("addQuestion.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
        req.setAttribute("creatorId", user.getUser_id());
        req.getRequestDispatcher("/addQuestion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = user.getUser_id();
        int creatorId = Integer.parseInt(request.getParameter("creatorId"));
        if (userId != creatorId) {
            response.sendRedirect("quiz-creation.jsp");
            return;
        }
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        String questionTypeStr = request.getParameter("questionType");
        QuestionType questionType = QuestionType.valueOf(questionTypeStr);
        List<String> answers = new ArrayList<>();
        Set<String> correctAnswersSet = new HashSet<>();
        answers.add(request.getParameter("answer"));
        String questionText = "";
        String answer = "";
        switch (questionType) {
            case Question_Response:
                answer = request.getParameter("answer");
                answers.add(answer);
                correctAnswersSet.add(answer);
                questionText = request.getParameter("questionText");
                break;
            case Fill_In_The_Blank:
                answer = request.getParameter("answer");
                answers.add(answer);
                correctAnswersSet.add(answer);
                questionText = request.getParameter("firsthalf");
                questionText += "$";
                questionText += request.getParameter("secondhalf");
                break;
            case Multiple_Choice:

                questionText = request.getParameter("questionText");

                String[] answerChoices = request.getParameterValues("answerChoice");
                String[] correctAnswers = request.getParameterValues("checkBoxMultiChoice");

                for (int i = 0; i < answerChoices.length; i++) {
                    String answerChoice = answerChoices[i];
                    String isCorrect = "false";
                    if (correctAnswers != null && i < correctAnswers.length) {
                        if (correctAnswers[i] != null) {
                            correctAnswersSet.add(answerChoice);
                        }
                    }
                    answers.add(answerChoice);
                }
                break;
            case Picture_Response:
                answers.add(request.getParameter("answer"));
                break;
            case Multi_Answer:
                answers.add(request.getParameter("answer"));
                break;
            case Multiple_Choice_Multiple_Answers:
                answers.add(request.getParameter("answer"));
                break;
            case Matching:
                break;
        }
        ServletContext context = getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
        Quiz quiz;
        try {
            quiz = quizDAO.getQuiz(quizId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        QuestionDAO questionDAO = (QuestionDAO) context.getAttribute("questionDAO");
        try {
            questionDAO.addQuestion(quizId, questionText, questionType.toString(), answers, correctAnswersSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("quizId", quizId);
        request.setAttribute("title", quiz.getName());
        request.setAttribute("category", quiz.getCategory());
        request.setAttribute("creatorId", creatorId);

        request.getRequestDispatcher("add-question.jsp").forward(request, response);
    }
}


