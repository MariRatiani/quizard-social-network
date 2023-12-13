package model.DAO;

import DB.ConnectionPool;
import model.objects.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TagDAO {
    private static ConnectionPool pool;

    public TagDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public  List<Tag> getAllQuizTags() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<Tag> quizTags = new ArrayList<>();

        try {
            con = pool.getConnection();
            String query = "SELECT * FROM quiz_tags order by tag_id;";
            statement = con.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                quizTags.add(new Tag(resultSet.getInt("quiz_id"), resultSet.getString("tag_name"), resultSet.getInt("tag_id")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return quizTags;
    }

    public Tag getQuizTagById(int tagId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        Tag quizTag = null;

        try {
            con = pool.getConnection();
            String query = "SELECT * FROM quiz_tags WHERE tag_id = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(1, tagId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                quizTag = new Tag(resultSet.getInt("quiz_id"), resultSet.getString("tag_name"), resultSet.getInt("tag_id"));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return quizTag;
    }

    public List<Tag> getTagsOfQuiz(int quiz_id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<Tag> quizTags = new ArrayList<>();

        try {
            con = pool.getConnection();
            String query = "SELECT * FROM quiz_tags where quiz_id = ? order by tag_id;";
            statement = con.prepareStatement(query);
            statement.setInt(1, quiz_id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                quizTags.add(new Tag(resultSet.getInt("quiz_id"), resultSet.getString("tag_name"), resultSet.getInt("tag_id")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return quizTags;
    }

    public int addQuizTag(int quiz_id, String tagName) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int newTagId = -1;

        try {
            con = pool.getConnection();
            String query = "INSERT INTO quiz_tags (quiz_id, tag_name) VALUES (?, ?);";
            statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, quiz_id);
            statement.setString(2, tagName);

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newTagId = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return newTagId;
    }

    public void deleteQuizTag(int tagId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_tags WHERE tag_id = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(1, tagId);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }
    public void deleteQuizTag(int quiz_id, String tagName) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_tags WHERE quiz_id = ? and tag_name = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(1, quiz_id);
            statement.setString(2, tagName);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }

    public void deleteAllTagsOfQuiz(int quiz_id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_tags where quiz_id = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(1, quiz_id);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
    }

    public void deleteAllTags() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = pool.getConnection();
            String query = "DELETE FROM quiz_tags;";
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
