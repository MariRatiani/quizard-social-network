package model.DAO;

import model.objects.Quiz;
import model.objects.QuizPerformance;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceDAOInterface {

    //void addPerformance(QuizPerformance performance);
    int addPerformanceOnStart(int quizId, int userId, LocalDateTime startTime, int quizMaxScore) throws SQLException;

    void UpdateAfterFinishing(int user_id, LocalDateTime endTime, int score) throws SQLException;
    List<QuizPerformance> getAllPerformances() throws SQLException;
    //returns every quizPerformance given user has taken
    List<QuizPerformance> getUserPerformances(int user_id) throws SQLException;
    List<QuizPerformance> getUserLastPerformances(int user_id, int selectionNum) throws SQLException;

    //returns all the performances on the given quiz
    List<QuizPerformance> getQuizPerformances(int quiz_id) throws SQLException;

    //returns the best performances on this quiz
    // (results of last day or of all time)
    List<QuizPerformance> getRecentPerformances(int quiz_id, int selectionSize) throws SQLException;

    List<QuizPerformance> getBestPerformancesInTimeRange(int quiz_id, LocalDateTime fromTimer, LocalDateTime
            untilDateTime, int selectionSize) throws SQLException;
    //ordered by date, percent correct, amountTaken.
    List<QuizPerformance> getPerformances(int user_id, int quiz_id, boolean orderByDate, boolean
            percentCorrect, boolean amountTaken) throws SQLException;
}