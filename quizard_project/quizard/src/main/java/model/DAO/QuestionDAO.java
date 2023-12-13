package model.DAO;

import DB.ConnectionPool;
import model.objects.Question;
import model.enums.QuestionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestionDAO {
    private final ConnectionPool pool;

    public QuestionDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public int addQuestion(int quizID, String questionTxt, String questionType, List<String> answers, Set<String> correctAnswers) throws SQLException {
        Connection con = null;
        PreparedStatement questionStatement = null;
        PreparedStatement answerStatement = null;
        int question_id = -1;

        try {
            con = pool.getConnection();
            String questionSql = "INSERT INTO Questions (quiz_id, question, question_type) VALUES (?, ?, ?)";
            questionStatement = con.prepareStatement(questionSql, Statement.RETURN_GENERATED_KEYS);
            questionStatement.setInt(1, quizID);
            questionStatement.setString(2, questionTxt);
            questionStatement.setString(3, questionType);

            questionStatement.executeUpdate();
            ResultSet generatedKeys = questionStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                /// checking if answers is not null or empty should not be done after query execution
                question_id = generatedKeys.getInt(1);
                addAnswers(con, answerStatement, question_id, answers, correctAnswers);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (questionStatement != null) questionStatement.close();
            if (answerStatement != null) answerStatement.close();
            if (con != null) pool.releaseConnection(con);
        }
        return question_id;
    }

    private void addAnswers(Connection con, PreparedStatement answerStatement, int question_id, List<String> answers, Set<String> correctAnswers) throws SQLException {
        for (int i=0; i<answers.size(); i++) {
            String answerSql = "INSERT INTO Answers (question_id, answer, is_correct) VALUES (?, ?, ?)";
            answerStatement = con.prepareStatement(answerSql);
            answerStatement.setInt(1, question_id);
            answerStatement.setString(2, answers.get(i));
            if (correctAnswers.contains(i))
                answerStatement.setBoolean(3, true);
            else
                answerStatement.setBoolean(3, false);
            answerStatement.executeUpdate();
        }
    }

    public Question getQuestion(int question_id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        Question question = null;

        try {
            con = pool.getConnection();
            String query = "SELECT question, question_type, quiz_id FROM Questions WHERE question_id = ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, question_id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                /// connection is already done and was redone in method
                // List<String> answers = getAnswersForQuestion(question_id);
                question = new Question(resultSet.getInt("quiz_id"), question_id,
                        resultSet.getString("question"), QuestionType.valueOf(resultSet.getString("question_type")),
                        getAnswersForQuestion(question_id, con));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return question;
    }

    private List<String> getAnswersForQuestion(int question_id, Connection con) throws SQLException {
        List<String> answers = new ArrayList<>();
        PreparedStatement statement = null;

        try {
            String query = "SELECT answer FROM Answers WHERE question_id = ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, question_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                answers.add(resultSet.getString("answer"));
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }

        return answers;
    }
    private List<String> getCorrectAnswersForQuestion(int question_id, Connection con) throws SQLException {
        List<String> answers = new ArrayList<>();
        PreparedStatement statement = null;

        try {
            String query = "SELECT answer FROM Answers WHERE question_id = ? AND is_correct = true";
            statement = con.prepareStatement(query);
            statement.setInt(1, question_id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                answers.add(resultSet.getString("answer"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public List<Integer> getQuestionsOfQuiz(int quizId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<Integer> questions = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "SELECT question_id from Questions WHERE quiz_id = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, quizId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                questions.add(resultSet.getInt("question_id"));
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
        return questions;
    }
    public List<Question> getAllQuestions(int quizId) throws SQLException {
        List<Question> result = new ArrayList<>();
        List<Integer> questions = getQuestionsOfQuiz(quizId);
        for(int i = 0; i < questions.size(); i++) {
            result.add(getQuestion(questions.get(i)));
        }
        return result;
    }
}