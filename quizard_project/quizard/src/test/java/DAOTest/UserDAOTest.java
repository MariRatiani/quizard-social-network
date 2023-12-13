package DAOTest;

import DB.ConnectionPool;
import model.DAO.AnnouncementDAO;
import model.DAO.MessageDAO;
import model.DAO.UserDAO;
import model.enums.MessageType;
import model.objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import static junit.framework.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class UserDAOTest {

    private ConnectionPool pool;
    private UserDAO userDAO;
    private  int first;
    private  int Qeiti;
    private  int third;
    private MessageDAO messageDAO;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        // Set up the ConnectionPool and AnnouncementDAO before each test
        pool = new ConnectionPool(true);
        userDAO = new UserDAO(pool);
        messageDAO = new MessageDAO(pool);
        String email = "arati19@freeuni.edu.ge";
        String userName = "AnaR";
        String pass = "MariIsTheBestCoder";

        String email2 = "ketevani20@freeuni.edu.ge";
        String userName2 = "ILoveMR!";
        String pass2 = "GigiBandzia";

        String email3 = "nkovz20@freeuni.edu.ge";
        String userName3 = "kovzira";
        String pass3 = "ILoveMarebi";

        first = userDAO.addUser(userName, email, pass);
        Qeiti = userDAO.addUser(userName2, email2, pass2);
        third = userDAO.addUser(userName3, email3, pass3);
    }

    @After
    public void tearDown() throws SQLException {
        // Release any remaining connections and clean up after each test
        deleteAllUsers();
        pool.close();
    }
    private void deleteAllUsers() throws SQLException {
        userDAO.removeAll();
    }
    @Test
    public void testAddGetUser() throws SQLException, NoSuchAlgorithmException {
        int userId = userDAO.addUser("mr", "user@gmail.com", "paroleparole");
        User user = userDAO.getUser(userId);
        assertEquals("mr", user.getName());
    }

    @Test
    public void TestAddAndGetUser() throws SQLException, NoSuchAlgorithmException {
        String email = "mrati20@freeuni.edu.ge";
        String userName = "MariR";
        String pass = "MariIsTheBestCoder";
        int userId = userDAO.addUser(userName, email, pass);
        User curUser = new User(email, userName, pass, userId, false);
        User gotUser = userDAO.getUser(userId);
        User withPassword = userDAO.getUserWithPassword(userName, pass);
        Assertions.assertEquals(curUser.getUser_id(), gotUser.getUser_id());
        Assertions.assertEquals(curUser.getName(), gotUser.getName());
        Assertions.assertEquals(curUser.isAdmin(), gotUser.isAdmin());
        Assertions.assertEquals(curUser.getEmail(), gotUser.getEmail());
        assertEquals(curUser.getUser_id(), withPassword.getUser_id());
    }

    @Test
    public void testIsAdmin() throws SQLException, NoSuchAlgorithmException {
        String email = "gmirz20@freeuni.edu.ge";
        String userName = "MariR";
        String pass = "MariIsTheBestCoder";

        String email2 = "mzhor20@freeuni.edu.ge";
        String userName2 = "ILoveMR";
        String pass2 = "mehCoder";
        int userId = userDAO.addUser(userName, email, pass);
        int secondUserId = userDAO.addUser(userName2, email2, pass2);
        User fisrtUser = new User(email, userName, pass, userId, true);
        User secondUser = new User(email2, userName2, pass2, secondUserId, false);

        User gotFirstUser = userDAO.getUser(userId);
        User gotsecUser = userDAO.getUser(secondUserId);
        Assertions.assertEquals(gotFirstUser.isAdmin(), userDAO.isAdmin(userId));
        Assertions.assertEquals(gotsecUser.isAdmin(), userDAO.isAdmin(secondUserId));
        userDAO.makeAdmin(secondUser.getName());
        assertEquals(true, userDAO.isAdmin(secondUser.getUser_id()));
    }

    @Test
    public void testFriendFunctions() throws SQLException, NoSuchAlgorithmException {
        LocalDateTime since = LocalDateTime.now();
        userDAO.addFriend(first, Qeiti, since);
        Set<Integer> friends = userDAO.getFriends(first);
        boolean isFriendOfKeti = friends.contains(Qeiti);
        boolean isFriendOfNika = friends.contains(third);
        Assertions.assertTrue(isFriendOfKeti);
        Assertions.assertTrue(!isFriendOfNika);
        userDAO.removeFriend(first, Qeiti);
        assertEquals(false, userDAO.getFriends(first).contains(Qeiti));
    }

    @Test
    public void testFriendRequests() throws SQLException, NoSuchAlgorithmException {
        userDAO.sendFriendRequest(third, Qeiti);
        Set<Integer> requests = userDAO.getAllFriendRequests(third);
        boolean hasReq = requests.contains(Qeiti);
        Assertions.assertTrue(hasReq);
    }
    @Test
    public void testInvalidUserRetrieval() throws SQLException, NoSuchAlgorithmException {
        // Test fetching a non-existent user
        User nonExistentUser = userDAO.getUser(-1);
        Assertions.assertNull(nonExistentUser);
    }
    @Test
    public void testUpdateAfterRequestResponseAccept() throws SQLException {
        // Test accepting a friend request
        userDAO.sendFriendRequest(third, Qeiti);
        userDAO.updateAfterRequestResponse(Qeiti, third, true);
        Set<Integer> friendsOfQeiti = userDAO.getFriends(Qeiti);
        Set<Integer> friendsOfThird = userDAO.getFriends(third);
        Assertions.assertTrue(friendsOfQeiti.contains(third));
        Assertions.assertTrue(friendsOfThird.contains(Qeiti));
    }
    @Test
    public void testUpdateAfterRequestResponseDecline() throws SQLException {
        // Test declining a friend request
        userDAO.sendFriendRequest(third, Qeiti);
        userDAO.updateAfterRequestResponse(Qeiti, third, false);
        Set<Integer> friendRequests = userDAO.getAllFriendRequests(third);
        Assertions.assertFalse(friendRequests.contains(Qeiti));
    }
    @Test
    public void testAddNewAchievement() throws SQLException {
        // Test adding a new achievement
        String achievment = "Completed Quiz";
        userDAO.addNewAchievment(Qeiti, achievment);
        ArrayList<String> a = userDAO.getAllAchievments(Qeiti);
        assertEquals(achievment, a.get(0));
        // You can add further assertions or checks here if needed
    }
    @Test
    public void testSearchUsers_SuccessfulSearch() throws SQLException {
        String searchQuery = "AnaR"; // Assuming this is a valid search query
        List<User> users = userDAO.searchUsers(searchQuery);
        System.out.println(users.size());
        // Assert that the list is not null
        Assertions.assertNotNull(users);

        if (!users.isEmpty()) {
            User user = users.get(0);
            Assertions.assertEquals("AnaR", user.getName());
        }
    }
    @Test
    public void testTakenQuizCount_UserHasNotTakenQuizzes() throws SQLException {
        int quizCount = userDAO.takenQuizCount(Qeiti);
        assertEquals(0, quizCount);
    }

    @Test
    public void testGetAllAndRemove() throws SQLException {
        assertEquals(userDAO.getAllUsers().size(), 3);
        userDAO.removeUser("AnaR");
        assertEquals(userDAO.getAllUsers().size(),2);
    }

    @Test
    public void testGetAllTypeOfMessages() throws SQLException {
        assertEquals(userDAO.getChallengesForUser(first).size(), 0);
        assertEquals(userDAO.getNotesForUser(Qeiti).size(), 0);
        assertEquals(userDAO.getAllFriendRequests(third).size(), 0);
        messageDAO.addMessage(first, Qeiti, MessageType.NOTE, "hello", new Timestamp(System.currentTimeMillis()));
        assertEquals(userDAO.getNotesForUser(Qeiti).get(0).getMessage(), "hello");
        messageDAO.addMessage(Qeiti, third, MessageType.CHALLENGE, "i challenge you", new Timestamp(System.currentTimeMillis()));
        assertEquals(userDAO.getChallengesForUser(third).get(0).getMessage(), "i challenge you");
    }

}
