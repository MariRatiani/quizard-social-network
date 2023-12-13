package model.DAO;

import model.objects.Quiz;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface QuizDaoInterface {

    //returns inserted quiz's id
    public int addQuiz(String quizName, String quizDescription, String categoryName, int creatorID,
                       boolean randomOrder, boolean multiplePages, boolean correctImmediatly, boolean practiceMode, LocalDateTime timeCreated,
                       int maxPoints, int amountTaken, int timeLimit) throws SQLException;

    //gets quiz with given quizID
    public Quiz getQuiz(int quizID) throws SQLException;

    //gets List of Quizzes created by user with id -> creator_id
    public List<Quiz> getCreatorQuizzes(int creatorId) throws SQLException;

    //returns list of all Quizzes
    public List<Quiz> getAllQuizzes() throws SQLException;

    //returns List of popular quizzes
    public List<Quiz> getPopularQuizzes() throws SQLException;

    //returns List of popular quizzes
    public List<Quiz> getRecentlyCreatedQuizzes()throws SQLException;

    void removeQuiz(int quizID) throws SQLException;
}

