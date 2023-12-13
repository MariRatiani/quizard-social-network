package model.DAO;
import DB.ConnectionPool;
import model.objects.Announcement;

import java.sql.*;
import java.util.ArrayList;

public class AnnouncementDAO {
    private final ConnectionPool pool;
    public AnnouncementDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public int addAnnouncement(int userID, String title,
                               String description, Timestamp timePosted) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        int newId = -1;
        try {
            connection = pool.getConnection();
            String sqlQuery = "INSERT INTO announcements (user_id, title, description, time_posted) VALUES (?, ?, ?, ?)";
            stmnt = connection.prepareStatement(sqlQuery, stmnt.RETURN_GENERATED_KEYS);
            stmnt.setInt(1, userID);
            stmnt.setString(2, title);
            stmnt.setString(3, description);
            stmnt.setTimestamp(4, timePosted);

            stmnt.executeUpdate();
            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            if (generatedKeys.next()) {
                newId = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return newId;
    }

    public ArrayList<Announcement> getAnnouncementsOfAUser(int user_id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        ArrayList<Announcement> announcements = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String currQuery = "SELECT * FROM announcements WHERE user_id = ?";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setInt(1, user_id);
            ResultSet resultSet = stmnt.executeQuery();

            while (resultSet.next())
                announcements.add(new Announcement(resultSet.getInt("announcement_id"), user_id,
                        resultSet.getString("title"), resultSet.getString("description"), resultSet.getTimestamp("time_posted")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return announcements;
    }

    public void deleteAnnouncement(int announcement_id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;

        try {
            connection = pool.getConnection();
            String query = "DELETE FROM announcements WHERE announcement_id = ?";
            stmnt = connection.prepareStatement(query);
            stmnt.setInt(1, announcement_id);
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    public ArrayList<Announcement> getAnnouncements() throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        ArrayList<Announcement> announcements = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM announcements order by time_posted desc;";
            stmnt = connection.prepareStatement(query);

            ResultSet resSet = stmnt.executeQuery();
            while (resSet.next())
                announcements.add(new Announcement(resSet.getInt("announcement_id"),
                        resSet.getInt("user_id"),
                        resSet.getString("title"),
                        resSet.getString("description"),
                        resSet.getTimestamp("time_posted")));
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return announcements;
    }

    public ArrayList<Announcement> getNewAnnouncements() throws SQLException {
        ArrayList<Announcement> res = getAnnouncements();
        if (res.size() > 3) {
            ArrayList<Announcement> firstThree = new ArrayList<>();
            for (int i=0; i<3; i++)
                firstThree.add(res.get(i));
            return firstThree;
        }
        return res;
    }

}
