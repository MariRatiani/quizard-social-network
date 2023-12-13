package model.DAO;

import DB.ConnectionPool;
import model.objects.Quiz;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO implements QuizDaoInterface {
    private final ConnectionPool pool;

    public QuizDAO(ConnectionPool pool) {
        this.pool = pool;
    }
//    //gets quiz with given quizID
    @Override
    public Quiz getQuiz(int quizID) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Quiz quiz = null;

        try {
            connection = pool.getConnection();
            String query = "select * from Quizes where quiz_id = ? ;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, quizID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                quiz = new Quiz(resultSet.getInt("quiz_id"), resultSet.getString("quiz_name"), resultSet.getString("description"),
                        resultSet.getString("category"), resultSet.getInt("creator_id"), resultSet.getBoolean("random_order"),
                        resultSet.getBoolean("multiple_pages"), resultSet.getBoolean("immediate_correction"), resultSet.getBoolean("practice_mode"), resultSet.getTimestamp("time_created").toLocalDateTime(),
                        resultSet.getInt("time_limit"), resultSet.getInt("amount_taken"), resultSet.getInt("max_points"));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }

        return quiz;
    }

    @Override
    public List<Quiz> getAllQuizzes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Quiz> quizzes = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM Quizes;";
            statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                quizzes.add(new Quiz(resultSet.getInt("quiz_id"), resultSet.getString("quiz_name"), resultSet.getString("description"),
                        resultSet.getString("category"), resultSet.getInt("creator_id"), resultSet.getBoolean("random_order"),
                        resultSet.getBoolean("multiple_pages"), resultSet.getBoolean("immediate_correction"), resultSet.getBoolean("practice_mode"), resultSet.getTimestamp("time_created").toLocalDateTime(),
                        resultSet.getInt("time_limit"), resultSet.getInt("amount_taken"), resultSet.getInt("max_points")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }

        return quizzes;
    }


    //gets List of Quizzes created by user with id -> creator_id
    @Override
    public List<Quiz> getCreatorQuizzes(int creatorId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Quiz> creatorQuizzes = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM Quizes WHERE creator_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, creatorId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                creatorQuizzes.add(new Quiz(resultSet.getInt("quiz_id"), resultSet.getString("quiz_name"), resultSet.getString("description"),
                        resultSet.getString("category"), resultSet.getInt("creator_id"), resultSet.getBoolean("random_order"),
                        resultSet.getBoolean("multiple_pages"), resultSet.getBoolean("immediate_correction"), resultSet.getBoolean("practice_mode"), resultSet.getTimestamp("time_created").toLocalDateTime(),
                        resultSet.getInt("time_limit"), resultSet.getInt("amount_taken"), resultSet.getInt("max_points")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }

        return creatorQuizzes;
    }

    @Override
    public List<Quiz> getPopularQuizzes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Quiz> popularQuizzes = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM Quizes ORDER BY amount_taken DESC LIMIT 3";
            statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                popularQuizzes.add(new Quiz(resultSet.getInt("quiz_id"), resultSet.getString("quiz_name"), resultSet.getString("description"),
                        resultSet.getString("category"), resultSet.getInt("creator_id"), resultSet.getBoolean("random_order"),
                        resultSet.getBoolean("multiple_pages"), resultSet.getBoolean("immediate_correction"), resultSet.getBoolean("practice_mode"), resultSet.getTimestamp("time_created").toLocalDateTime(),
                        resultSet.getInt("time_limit"), resultSet.getInt("amount_taken"), resultSet.getInt("max_points")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }

        return popularQuizzes;
    }
    @Override
    public List<Quiz> getRecentlyCreatedQuizzes() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Quiz> recentlyCreatedQuizzes = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM Quizes ORDER BY time_created DESC LIMIT 3";
            statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                recentlyCreatedQuizzes.add(new Quiz(resultSet.getInt("quiz_id"), resultSet.getString("quiz_name"), resultSet.getString("description"),
                        resultSet.getString("category"), resultSet.getInt("creator_id"), resultSet.getBoolean("random_order"),
                        resultSet.getBoolean("multiple_pages"), resultSet.getBoolean("immediate_correction"), resultSet.getBoolean("practice_mode"), resultSet.getTimestamp("time_created").toLocalDateTime(),
                        resultSet.getInt("time_limit"), resultSet.getInt("amount_taken"), resultSet.getInt("max_points")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }

        return recentlyCreatedQuizzes;
    }
    //returns inserted quiz's id
    @Override
    public int addQuiz(String quizName, String quizDescription, String categoryName, int creatorID,
                       boolean randomOrder, boolean multiplePages, boolean correctImmediatly, boolean practiceMode, LocalDateTime timeCreated,
                       int maxPoints, int amountTaken, int timeLimit) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int newId = -1;

        try {
            con = pool.getConnection();

            // Insert quiz information
            String sql = "INSERT INTO Quizes (quiz_name, description, category, creator_id, amount_taken, random_order, time_created, multiple_pages, practice_mode, max_points, immediate_correction, time_limit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, quizName);
            statement.setString(2, quizDescription);
            statement.setString(3, categoryName);
            statement.setInt(4, creatorID);
            statement.setInt(5, amountTaken);
            statement.setBoolean(6, randomOrder);
            statement.setTimestamp(7, Timestamp.valueOf(timeCreated));
            statement.setBoolean(8, multiplePages);
            statement.setBoolean(9, practiceMode);
            statement.setInt(10, maxPoints);
            statement.setBoolean(11, correctImmediatly);
            statement.setInt(12, timeLimit);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newId = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
        return newId;
    }

    @Override
    public void removeQuiz(int quizID) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = pool.getConnection();
            statement = con.prepareStatement("delete from Quizes where quiz_id = ?;");
            statement.setInt(1, quizID);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }

}