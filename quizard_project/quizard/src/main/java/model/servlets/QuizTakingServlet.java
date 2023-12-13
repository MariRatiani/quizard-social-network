package model.servlets;

import model.DAO.QuestionDAO;
import model.DAO.QuizDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class QuizTakingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        QuestionDAO questionDAO = (QuestionDAO) getServletContext().getAttribute("questionDAO");
        QuizDAO quizDAO = (QuizDAO) getServletContext().getAttribute("quizDAO");

        int quizId = Integer.parseInt(request.getParameter("quizId"));
        HttpSession session = request.getSession();
        if (request.getSession() == null) {
            throw new NullPointerException();
        }
        ArrayList<Integer> questions = (ArrayList<Integer>) session.getAttribute("questions");
        int score = Integer.parseInt(request.getParameter("score"));
        int maxScore = Integer.parseInt(request.getParameter("maxScore"));
        boolean isPracticeMode = request.getParameter("practiceMode").equals("true");
        boolean isQuizFinished = false;
        HashMap<Integer, Integer> quesFrequency = new HashMap<Integer, Integer>();
        if (isPracticeMode) {
            quesFrequency = (HashMap<Integer, Integer>) session.getAttribute("ques_frequency");
        }
        String timeTakenStr = "";
        if (isQuizFinished) {
            Date startTime = (Date) session.getAttribute("start_time");
            Calendar cal = Calendar.getInstance();
            Date endTime = new Date(cal.getTimeInMillis());
            long diff = endTime.getTime() - startTime.getTime();
            int diffMin = (int) (diff / (60 * 1000));
            int diffSec = (int) ((diff % (60 * 1000)) / 1000);
            timeTakenStr = diffMin + " minutes, " + diffSec + " seconds";
            Timestamp timeTaken = new Timestamp(0, 0, 0, 0, diffMin, diffSec, 0);
            request.setAttribute("timeStamp", timeTaken);
            request.setAttribute("score", score);
            request.setAttribute("maxScore", maxScore);
            request.getRequestDispatcher("/scoring.jsp").forward(request, response);
        }
    }
}