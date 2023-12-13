package model.DAO;

import DB.ConnectionPool;
import model.enums.MessageType;
import model.objects.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private final ConnectionPool pool;

    public MessageDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public int addMessage(int fromId, int toId, MessageType messageType, String message, Timestamp timeSent) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int generatedMessageId = -1; // Default value in case no ID is generated

        try {
            con = pool.getConnection();
            String sql = "INSERT INTO messages (from_id, to_id, message_type, message, time_sent) VALUES (?, ?, ?, ?, ?)";
            statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, fromId);
            statement.setInt(2, toId);
            statement.setString(3, messageType.toString());
            statement.setString(4, message);
            statement.setTimestamp(5, timeSent);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                generatedMessageId = resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }
        return generatedMessageId;
    }

    public Message getMessageById(int messageId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        Message message = null;

        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM messages WHERE message_id = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, messageId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int fromId = resultSet.getInt("from_id");
                int toId = resultSet.getInt("to_id");
                MessageType messageType = MessageType.valueOf(resultSet.getString("message_type"));
                String messageContent = resultSet.getString("message");
                Timestamp timeSent = resultSet.getTimestamp("time_sent");
                message = new Message(messageId, fromId, toId, messageType, messageContent, timeSent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return message;
    }

    public List<Message> getMessagesForUser(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<Message> messageFeed = new ArrayList<>();

        try {
            con = pool.getConnection();
            String sql = "SELECT * FROM messages WHERE to_id = ? ORDER BY time_sent DESC";
            statement = con.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.setMessageId(resultSet.getInt("message_id"));
                message.setFromId(resultSet.getInt("from_id"));
                message.setToId(resultSet.getInt("to_id"));
                message.setMessageType(MessageType.valueOf(resultSet.getString("message_type")));
                message.setMessage(resultSet.getString("message"));
                message.setTimeSent(resultSet.getTimestamp("time_sent"));
                messageFeed.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (con != null) pool.releaseConnection(con);
        }

        return messageFeed;
    }

    public void removeMessage(int messageID) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = pool.getConnection();
            String sql = "delete FROM messages WHERE message_id = ?;";
            statement = con.prepareStatement(sql);
            statement.setInt(1, messageID);
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