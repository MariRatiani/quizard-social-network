package model.DAO;

import DB.ConnectionPool;
import model.objects.QuizCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizCategoryDAO {
    private static ConnectionPool pool;

    public QuizCategoryDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public  List<QuizCategory> getAllQuizCategories() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<QuizCategory> quizCategories = new ArrayList<>();

        try {
            con = pool.getConnection();
            String query = "SELECT * FROM quiz_categories order by category_id;";
            statement = con.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                quizCategories.add(new QuizCategory(resultSet.getInt("category_id"), resultSet.getString("category_name")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return quizCategories;
    }

    public QuizCategory getQuizCategoryById(int categoryId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        QuizCategory quizCategory = null;

        try {
            con = pool.getConnection();
            String query = "SELECT * FROM quiz_categories WHERE category_id = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(1, categoryId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                quizCategory = new QuizCategory(categoryId, resultSet.getString("category_name"));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return quizCategory;
    }

    public int addQuizCategory(String categoryName) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int newCategoryId = -1;

        try {
            con = pool.getConnection();
            String query = "INSERT INTO quiz_categories (category_name) VALUES (?);";
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, categoryName);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newCategoryId = generatedKeys.getInt(1);
            } //else {
                //throw new SQLException("Failed to get the new category_id.");
            //}
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return newCategoryId;
    }

    public void deleteQuizCategory(int categoryId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_categories WHERE category_id = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(1, categoryId);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }
    public void deleteQuizCategory(String categoryName) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_categories WHERE category_name = ?;";
            statement = con.prepareStatement(query);
            statement.setString(1, categoryName);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }

    public void deleteAllCategories() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_categories;";
            statement = con.prepareStatement(query);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }

}
