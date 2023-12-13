package model.DAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DB.ConnectionPool;
import model.enums.MessageType;
import model.objects.Message;
import model.objects.User;

public class UserDAO implements UserDaoInterface {
    private final ConnectionPool pool;

    public UserDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public User getUser(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        User result = null;

        try {
            connection = pool.getConnection();
            String currQuery = "SELECT * FROM Users WHERE user_id=?";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setInt(1, userId);
            ResultSet resSet = stmnt.executeQuery();

            if (resSet.next()) {
                result = new User(resSet.getString("user_email"), resSet.getString("user_name"),
                        resSet.getString("user_password_hashcode"), resSet.getInt("user_id"),
                        resSet.getBoolean("is_admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return result;
    }

    @Override
    public User getUserWithPassword(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        User result = null;

        try {
            connection = pool.getConnection();
            String currQuery = "SELECT * FROM Users WHERE user_name=? and user_password_hashcode=?";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setString(1, username);
            stmnt.setString(2, hashing(password));
            ResultSet resSet = stmnt.executeQuery();

            if (resSet.next()) {
                result = new User(resSet.getString("user_email"), resSet.getString("user_name"),
                        resSet.getString("user_password_hashcode"), resSet.getInt("user_id"),
                        resSet.getBoolean("is_admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return result;
    }


    @Override
    public int addUser(String name, String email, String password) throws SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        int user_id = -1;
        try {
            connection = pool.getConnection();
            boolean closed = connection.isClosed();
            String passwordHashCode = hashing(password);
            String currQuery = "INSERT INTO Users (user_email, user_name, user_password_hashcode) VALUES (?,?,?)";
            stmnt = connection.prepareStatement(currQuery, Statement.RETURN_GENERATED_KEYS);
            stmnt.setString(1, email);
            stmnt.setString(2, name);
            stmnt.setString(3, passwordHashCode);
            stmnt.executeUpdate();

            ResultSet generetedKeys = stmnt.getGeneratedKeys();
            if (generetedKeys.next()) {
                user_id = generetedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return user_id;
    }

    private String hashing(String s) throws NoSuchAlgorithmException {
        byte[] bytes = null;
        MessageDigest mesDig = MessageDigest.getInstance("SHA");
        bytes = mesDig.digest(s.getBytes());
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val < 16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    @Override
    public boolean isAdmin(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        boolean res = false;
        try {
            connection = pool.getConnection();
            String currQuery = "SELECT is_admin FROM Users WHERE user_id=?";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setInt(1, userId);
            ResultSet resSet = stmnt.executeQuery();

            if (resSet.next()) {
                res = resSet.getBoolean("is_admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return res;
    }

    @Override
    public Set<Integer> getFriends(int userId) throws SQLException {
        Set<Integer> friends = new HashSet<>();
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = pool.getConnection();
            String currQuery = "SELECT * FROM friends WHERE friend1_id=? OR friend2_id = ?;";
            stm = con.prepareStatement(currQuery);
            stm.setInt(1, userId);
            stm.setInt(2, userId);
            ResultSet resSet = stm.executeQuery();
            while (resSet.next()) {
                int first = resSet.getInt("friend1_id");
                int second = resSet.getInt("friend2_id");
                if (first == userId)
                    friends.add(second);
                else friends.add(first);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stm != null) stm.close();
            if (con != null) pool.releaseConnection(con);
        }
        return friends;
    }

    @Override
    public void addFriend(int firstUser, int secondUser, LocalDateTime sinceDate) throws SQLException {
        Connection connection = pool.getConnection();
        String deleteQuery = "DELETE FROM messages where from_id=? AND to_id=? AND message_type=?";
        PreparedStatement statement = connection.prepareStatement(deleteQuery);
        try {
            statement.setInt(1, secondUser);
            statement.setInt(2, firstUser);
            statement.setString(3, "FRIEND_REQUEST");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String currQuery = "INSERT INTO friends (friend1_id, friend2_id, friends_since) VALUES(?,?, ?)";
        PreparedStatement stmnt = connection.prepareStatement(currQuery);
        try {
            stmnt.setInt(1, firstUser);
            stmnt.setInt(2, secondUser);
            stmnt.setTimestamp(3, Timestamp.valueOf(sinceDate));
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    @Override
    public void sendFriendRequest(int to, int from) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = pool.getConnection();
            String query = "INSERT INTO messages (from_id, to_id, message_type, message, time_sent) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(dt);
            statement.setInt(1, from);
            statement.setInt(2, to);
            statement.setString(3, "FRIEND_REQUEST");
            statement.setString(4, "You have a new friend request");
            statement.setString(5, date);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    @Override
    public Set<Integer> getAllFriendRequests(int user_id) throws SQLException {
        Set<Integer> friendRequests = new HashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.getConnection();
            String query = "SELECT from_id FROM messages WHERE to_id = ? AND message_type = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setString(2, "FRIEND_REQUEST");

            ResultSet resultSet = statement.executeQuery();

            // Iterate through the results and add the sender IDs to the list
            while (resultSet.next()) {
                int senderId = resultSet.getInt("from_id");
                friendRequests.add(senderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return friendRequests;
    }

    @Override
    public List<Message> getNotesForUser(int user_id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Message> notes = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM messages WHERE to_id = ? AND message_type = ? order by time_sent desc";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setString(2, "NOTE");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                notes.add(new Message(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
                        MessageType.NOTE, resultSet.getString(5), resultSet.getTimestamp(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return notes;
    }

    @Override
    public List<Message> getChallengesForUser(int user_id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Message> challenges = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM messages WHERE to_id = ? AND message_type = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user_id);
            statement.setString(2, "CHALLENGE");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                challenges.add(new Message(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3),
                        MessageType.NOTE, resultSet.getString(5), resultSet.getTimestamp(6)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return challenges;
    }

    @Override
    public void updateAfterRequestResponse(int from, int to, boolean acceptRequest) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        try {
            connection = pool.getConnection();
            String deleteSql = "DELETE FROM messages WHERE from_id = ? AND to_id = ? AND message_type = ?";
            statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, from);
            statement.setInt(2, to);
            statement.setString(3, "FRIEND_REQUEST");
            statement.executeUpdate();

            if (acceptRequest) {
                String addFriendQuery = "INSERT INTO friends (friend1_id, friend2_id, friends_since) VALUES (?, ?, ?)";
                statement2 = connection.prepareStatement(addFriendQuery);
                statement2.setInt(1, to);
                statement2.setInt(2, from);
                statement2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                statement2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (statement2 != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    @Override
    public void addNewAchievment(int user_id, String achievement) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;

        try {
            connection = pool.getConnection();
            String currQuery = "INSERT INTO achievements (user, achievment) VALUES(?, ?)";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setInt(1, user_id);
            stmnt.setString(2, achievement);
            stmnt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in the reverse order of acquisition
            if (stmnt != null) {
                stmnt.close();
            }
            if (connection != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public ArrayList<String> getAllAchievments(int user) throws SQLException {
        ArrayList<String> res = new ArrayList<>();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT DISTINCT achievment FROM achievements WHERE user=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String currAchievment = resultSet.getString("achievment");
                res.add(currAchievment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                pool.releaseConnection(connection);
            }
        }

        return res;
    }

    @Override
    public int takenQuizCount(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;

        try {
            connection = pool.getConnection();
            String query = "SELECT COUNT(*) FROM Quiz_history WHERE user_id  = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                count = resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return count;
    }

    @Override
    public List<User> searchUsers(String searchQuery) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        List<User> users = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String query = "SELECT * FROM Users WHERE user_name LIKE ? ORDER BY user_id";
            statement = connection.prepareStatement(query);
            statement.setString(1, searchQuery);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                users.add(new User(resultSet.getString("user_email"), resultSet.getString("user_name"),
                        resultSet.getString("user_password_hashcode"), resultSet.getInt("user_id"), resultSet.getBoolean("is_admin")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return users;
    }

    @Override
    public void makeAdmin(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;

        try {
            connection = pool.getConnection();
            String currQuery = "UPDATE Users SET is_admin=true WHERE user_name = ?;";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setString(1, username);
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    @Override
    public void removeUser(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;

        try {
            connection = pool.getConnection();
            String currQuery = "DELETE FROM Users WHERE user_name = ?;";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setString(1, username);
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    @Override
    public void removeFriend(int user1, int user2) throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;

        try {
            connection = pool.getConnection();
            String currQuery = "DELETE FROM friends WHERE (friend1_id = ? and friend2_id = ?) or (friend1_id = ? and friend2_id = ?);";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.setInt(1, user1);
            stmnt.setInt(2, user2);
            stmnt.setInt(3, user2);
            stmnt.setInt(4, user1);
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

    @Override
    public ArrayList<User> getAllUsers() throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;
        ArrayList<User> users = new ArrayList<>();

        try {
            connection = pool.getConnection();
            String currQuery = "select * FROM Users;";
            stmnt = connection.prepareStatement(currQuery);
            ResultSet resultSet = stmnt.executeQuery();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("user_email"), resultSet.getString("user_name"),
                        resultSet.getString("user_password_hashcode"), resultSet.getInt("user_id"), resultSet.getBoolean("is_admin")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
        return users;
    }

    @Override
    public void removeAll() throws SQLException {
        Connection connection = null;
        PreparedStatement stmnt = null;

        try {
            connection = pool.getConnection();
            String currQuery = "delete from Users;";
            stmnt = connection.prepareStatement(currQuery);
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (stmnt != null) stmnt.close();
            if (connection != null) pool.releaseConnection(connection);
        }
    }

}