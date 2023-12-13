package model.DAO;

import DB.ConnectionPool;
import model.objects.QuizPerformance;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuizPerformanceDAO implements PerformanceDAOInterface {
    private final ConnectionPool pool;

    public QuizPerformanceDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public int addPerformanceOnStart(int quizId, int userId, LocalDateTime startTime, int quizMaxScore) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int newId = -1;
        try {
            con = pool.getConnection();
            String query = "INSERT INTO Quiz_history (quiz_id, user_id, score, start_time, end_time, quiz_max_score) VALUES (?,?,?,?,?,?);";
            statement = con.prepareStatement(query, statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, quizId);
            statement.setInt(2, userId);
            statement.setInt(3, 0);
            statement.setTimestamp(4, Timestamp.valueOf(startTime));
            statement.setTimestamp(5, Timestamp.valueOf(startTime));
            statement.setInt(6, quizMaxScore);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    newId = resultSet.getInt(1);
                } else {
                    //throw new SQLException("Failed to get the new quiz_history_id.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return newId;
    }

    @Override
    public void UpdateAfterFinishing(int user_id, LocalDateTime endTime, int score) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "UPDATE Quiz_history SET end_time = ?, score = ? WHERE user_id = ?";
            statement = con.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(endTime));
            statement.setInt(2, score);
            statement.setInt(3, user_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
    }
    @Override
    public List<QuizPerformance> getAllPerformances() throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            String query = "SELECT user_id, quiz_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history";
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int quizId = resultSet.getInt("quiz_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(userId, quizId, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }
    //returns every quizPerformance given user has taken
    @Override
    public List<QuizPerformance> getUserPerformances(int user_id) throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            String query = "SELECT quiz_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history WHERE user_id = ? " +
                    "ORDER BY end_time DESC";
            statement = con.prepareStatement(query);
            statement.setInt(1, user_id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int quizId = resultSet.getInt("quiz_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(user_id, quizId, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }
    @Override
    public List<QuizPerformance> getUserLastPerformances(int user_id, int selectionNum) throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            String query = "SELECT quiz_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history WHERE user_id = ? " +
                    "ORDER BY end_time DESC " +
                    "LIMIT ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setInt(2, selectionNum);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int quizId = resultSet.getInt("quiz_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(user_id, quizId, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }
    @Override
    public List<QuizPerformance> getQuizPerformances(int quiz_id) throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            String query = "SELECT user_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history WHERE quiz_id = ? " +
                    "ORDER BY end_time DESC";
            statement = con.prepareStatement(query);
            statement.setInt(1, quiz_id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(userId, quiz_id, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }

    @Override
    public List<QuizPerformance> getRecentPerformances(int quiz_id, int selectionSize) throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            String query = "SELECT user_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history WHERE quiz_id = ? " +
                    "ORDER BY end_time DESC " +
                    "LIMIT ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, quiz_id);
            statement.setInt(2, selectionSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(userId, quiz_id, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }
    @Override
    public List<QuizPerformance> getBestPerformancesInTimeRange(int quiz_id, LocalDateTime fromTime, LocalDateTime untilTime, int selectionSize) throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            String query = "SELECT user_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history " +
                    "WHERE quiz_id = ? AND start_time >= ? AND end_time <= ? " +
                    "ORDER BY score DESC, end_time - start_time DESC " +
                    "LIMIT ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, quiz_id);
            statement.setTimestamp(2, Timestamp.valueOf(fromTime));
            statement.setTimestamp(3, Timestamp.valueOf(untilTime));
            statement.setInt(4, selectionSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(userId, quiz_id, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }

    @Override
    public List<QuizPerformance> getPerformances(int user_id, int quiz_id, boolean orderByDate, boolean percentCorrect, boolean amountTaken) throws SQLException {
        List<QuizPerformance> performances = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = pool.getConnection();
            StringBuilder queryBuilder = new StringBuilder("SELECT user_id, score, start_time, end_time, quiz_max_score " +
                    "FROM Quiz_history WHERE user_id = ? AND quiz_id = ?");

            if (orderByDate) {
                queryBuilder.append(" ORDER BY start_time ASC");
            } else if (percentCorrect) {
                queryBuilder.append(" ORDER BY (score * 100 / quiz_max_score) DESC");
            } else if (amountTaken) {
                queryBuilder.append(" ORDER BY end_time - start_time ASC");
            }

            statement = con.prepareStatement(queryBuilder.toString());
            statement.setInt(1, user_id);
            statement.setInt(2, quiz_id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
                int quizMaxScore = resultSet.getInt("quiz_max_score");

                QuizPerformance performance = new QuizPerformance(userId, quiz_id, score, quizMaxScore, startTime, endTime);
                performances.add(performance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (con != null) con.close();
        }
        return performances;
    }
}