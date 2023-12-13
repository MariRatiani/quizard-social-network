package DAOTest;

import DB.ConnectionPool;
import model.DAO.MessageDAO;
import model.DAO.UserDAO;
import model.enums.MessageType;
import model.objects.Message;
import model.objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;

public class MessageDAOTest {
    private MessageDAO messageDAO;
    private ConnectionPool pool;
    private UserDAO userDAO;
    private int userId1;
    private int userId2;
    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        pool = new ConnectionPool(true);
        messageDAO = new MessageDAO(pool);
        userDAO = new UserDAO(pool);
        userId1 = userDAO.addUser("marizmzho", "mzhor20@freeuni.edu.ge", "asd");
        userId2 = userDAO.addUser("mrati", "mrati20@freeuni.edu.ge", "asd");
    }

    @After
    public void tearDown() throws SQLException {
        userDAO.removeAll(); // will delete messages too
        pool.close();
    }

    @Test
    public void testAddMessage() throws SQLException {
        Timestamp currentTime = Timestamp.from(Instant.now());
        int message_id = messageDAO.addMessage(userId1,userId2,MessageType.NOTE, "hello",currentTime);

        assertTrue(message_id > -1);

        Message testMessage = messageDAO.getMessageById(message_id);
        assertNotNull(testMessage); // Make sure retrieved message is not null
        assertEquals(userId1, testMessage.getFromId());
        assertEquals(userId2, testMessage.getToId());
        assertEquals(MessageType.NOTE, testMessage.getMessageType());
        assertEquals("hello",testMessage.getMessage());
    }
    @Test
    public void testGetMessagesForUser() throws SQLException {
        MessageType messageType = MessageType.NOTE;
        String messageText = "Hello";
        Timestamp currentTime = Timestamp.from(Instant.now());

        messageDAO.addMessage(userId1, userId2, messageType, messageText, currentTime);
        messageDAO.addMessage(userId1, userId2, messageType, "Another message", currentTime);

        List<Message> messages = messageDAO.getMessagesForUser(userId2);

        assertNotNull(messages);
        assertEquals(2, messages.size());

        Message firstMessage = messages.get(0);
        assertEquals(userId1, firstMessage.getFromId());
        assertEquals(userId2, firstMessage.getToId());
        assertEquals(messageType, firstMessage.getMessageType());
        assertEquals(messageText, firstMessage.getMessage());

        Message secondMessage = messages.get(1);
        assertEquals(userId1, secondMessage.getFromId());
        assertEquals(userId2, secondMessage.getToId());
        assertEquals(messageType, secondMessage.getMessageType());
        assertEquals("Another message", secondMessage.getMessage());
    }

    @Test
    public void testDelete() throws SQLException {
        MessageType messageType = MessageType.NOTE;
        String messageText = "Hello";
        Timestamp currentTime = Timestamp.from(Instant.now());

        int id = messageDAO.addMessage(userId1, userId2, messageType, messageText, currentTime);
        assertEquals(messageDAO.getMessagesForUser(userId2).size(), 1);
        messageDAO.removeMessage(id);
        assertEquals(messageDAO.getMessagesForUser(userId2).size(), 0);
    }

}
