package model.DAO;

import model.objects.Message;
import model.objects.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public interface UserDaoInterface {
    User getUser(int id) throws SQLException;

    User getUserWithPassword(String username, String password) throws SQLException;

    int addUser(String name, String email, String password) throws SQLException, NoSuchAlgorithmException;

    boolean isAdmin(int user_id) throws SQLException;

    Set<Integer> getFriends(int user_id) throws SQLException;

    void addFriend(int firstUser, int secondUser, LocalDateTime sinceDate) throws SQLException;

    void sendFriendRequest(int to, int from) throws SQLException;

    Set<Integer> getAllFriendRequests(int user_id) throws SQLException;

    List<Message> getNotesForUser(int user_id) throws  SQLException;

    List<Message> getChallengesForUser(int user_id) throws  SQLException;

    void updateAfterRequestResponse(int from, int to, boolean acceptRequest) throws SQLException;

    void addNewAchievment(int user_id, String achievment) throws SQLException;

    ArrayList<String> getAllAchievments(int user) throws SQLException;

    int takenQuizCount(int userId) throws SQLException;

    List<User> searchUsers(String searchQuery) throws SQLException;

    void makeAdmin(String username) throws SQLException;

    void removeUser(String username) throws SQLException;

    void removeFriend(int user1, int user2) throws  SQLException;

    ArrayList<User> getAllUsers() throws  SQLException;

    void removeAll() throws  SQLException;

}