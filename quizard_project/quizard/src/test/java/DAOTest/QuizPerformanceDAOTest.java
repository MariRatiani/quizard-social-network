package DAOTest;

import DB.ConnectionPool;
import model.DAO.QuizCategoryDAO;
import model.DAO.QuizDAO;
import model.DAO.QuizPerformanceDAO;
import model.DAO.UserDAO;
import model.objects.QuizPerformance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuizPerformanceDAOTest {
    private ConnectionPool pool;
    private QuizPerformanceDAO quizPerformanceDAO;
    private UserDAO userDAO;
    private QuizDAO quizDAO;
    private QuizCategoryDAO quizCategoryDAO;
    private int userId1;
    private int userId2;
    private int quizId1;
    private int quizId2;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        pool = new ConnectionPool(true);
        quizPerformanceDAO = new QuizPerformanceDAO(pool);
        userDAO = new UserDAO(pool);
        quizCategoryDAO = new QuizCategoryDAO(pool);
        quizDAO = new QuizDAO(pool);
        userId1 = userDAO.addUser("user1", "", "user1");
        userId2 = userDAO.addUser("user2", "", "user2");
        quizCategoryDAO.addQuizCategory("history");
        quizId1 = quizDAO.addQuiz("first", "first quiz", "history", userId1,
                false, false, false, false, LocalDateTime.now(), 10, 0, 100);
        quizId2 = quizDAO.addQuiz("second", "second quiz", "history", userId2,
                false, false, false, false, LocalDateTime.now(), 10, 0, 100);
    }

    @After
    public void tearDown() throws SQLException {
        userDAO.removeAll(); // will delete performances too
        quizCategoryDAO.deleteAllCategories();
        pool.close();
    }
    @Test
    public void testGetAllPerformancesWithNoData() throws SQLException {
        List<QuizPerformance> performances = quizPerformanceDAO.getAllPerformances();
        assertTrue(performances.isEmpty());
    }
    @Test
    public void testAddAndGetPerformances() throws SQLException {
        LocalDateTime startTime = LocalDateTime.now();
        QuizPerformance performance = new QuizPerformance(userId1, quizId1, 0, 100, startTime, startTime.plusMinutes(10));
        int newId = quizPerformanceDAO.addPerformanceOnStart(quizId1, userId1, startTime, 100);

        List<QuizPerformance> addedPerformances = quizPerformanceDAO.getAllPerformances();
        assertNotNull(addedPerformances);
        QuizPerformance retrievedPerformance = addedPerformances.get(0);

        assertEquals(performance.getUserId(), retrievedPerformance.getUserId());
        assertEquals(performance.getQuizId(), retrievedPerformance.getQuizId());
        assertEquals(performance.getScore(), retrievedPerformance.getScore());
        assertEquals(performance.getPossibleMaxScore(), retrievedPerformance.getPossibleMaxScore());

        quizPerformanceDAO.UpdateAfterFinishing(userId1, startTime.plusMinutes(10), 90);
        List<QuizPerformance> updatedPerformances = quizPerformanceDAO.getAllPerformances();
        QuizPerformance updatedPerformance = updatedPerformances.get(0);
        assertEquals(90, updatedPerformance.getScore());
    }
    @Test
    public void testGetUserPerformances() throws SQLException {
        List<QuizPerformance> performances = quizPerformanceDAO.getUserPerformances(100);
        assertTrue(performances.isEmpty());
        LocalDateTime startTime = LocalDateTime.now();
        QuizPerformance performance = new QuizPerformance(userId1, quizId1, 0, 100, startTime, startTime.plusMinutes(10));
        int newId = quizPerformanceDAO.addPerformanceOnStart(quizId1, userId1, startTime, 100);
        assertEquals(quizPerformanceDAO.getUserPerformances(userId1).size(), 1);
        assertEquals(quizPerformanceDAO.getUserLastPerformances(userId1, 3).size(), 1);
    }
    @Test
    public void testGetQuizPerformances() throws SQLException {
        List<QuizPerformance> performances = quizPerformanceDAO.getQuizPerformances(-5);
        assertTrue(performances.isEmpty());
        LocalDateTime startTime = LocalDateTime.now();
        QuizPerformance performance = new QuizPerformance(userId1, quizId1, 0, 100, startTime, startTime.plusMinutes(10));
        int newId = quizPerformanceDAO.addPerformanceOnStart(quizId1, userId1, startTime, 100);
        assertEquals(quizPerformanceDAO.getQuizPerformances(quizId1).size(), 1);
    }
    @Test
    public void testGetRecentPerformances() throws SQLException {
        List<QuizPerformance> performances = quizPerformanceDAO.getRecentPerformances(1, 5);
        assertTrue(performances.isEmpty());
        LocalDateTime startTime = LocalDateTime.now();
        QuizPerformance performance = new QuizPerformance(userId2, quizId2, 0, 100, startTime, startTime.plusMinutes(10));
        int newId = quizPerformanceDAO.addPerformanceOnStart(quizId2, userId1, startTime, 100);
        assertEquals(quizPerformanceDAO.getRecentPerformances(quizId2, 3).size(), 1);
    }
    @Test
    public void testGetBestPerformancesInTimeRange() throws SQLException, InterruptedException {
        LocalDateTime fromTime = LocalDateTime.now().minusDays(1);
        LocalDateTime untilTime = LocalDateTime.now();
        List<QuizPerformance> performances = quizPerformanceDAO.getBestPerformancesInTimeRange(quizId1, fromTime, untilTime, 5);
        assertTrue(performances.isEmpty());

        LocalDateTime newTime = LocalDateTime.now().minusHours(1);
        QuizPerformance performance = new QuizPerformance(userId1, quizId1, 0, 100, newTime, newTime.plusMinutes(10));
        int newId = quizPerformanceDAO.addPerformanceOnStart(quizId1, userId1, newTime, 100);
        assertEquals(quizPerformanceDAO.getBestPerformancesInTimeRange(quizId1, fromTime, newTime.plusMinutes(10), 3).size(), 1);
    }
    @Test
    public void testGetPerformances() throws SQLException {
        LocalDateTime startTime = LocalDateTime.now();
        QuizPerformance performance = new QuizPerformance(userId1, quizId1, 0, 100, startTime, startTime.plusMinutes(10));
        int newId = quizPerformanceDAO.addPerformanceOnStart(quizId1, userId1, startTime, 100);
        QuizPerformance performance2 = new QuizPerformance(userId2, quizId2, 10, 10, startTime, startTime.plusMinutes(10));
        int newId2 = quizPerformanceDAO.addPerformanceOnStart(quizId2, userId2, startTime, 10);
        List<QuizPerformance> performances = quizPerformanceDAO.getPerformances(userId1, quizId1, true, false, false);
        List<QuizPerformance> performances2 = quizPerformanceDAO.getPerformances(userId2, quizId2, false, true, false);
        List<QuizPerformance> shouldBeEmpty = quizPerformanceDAO.getPerformances(userId1, quizId2, false, false, true);
        assertTrue(shouldBeEmpty.isEmpty());
        assertEquals(performances.size(), 1);
        assertEquals(performances.get(0).getPossibleMaxScore(), performance.getPossibleMaxScore());
        assertEquals(performances2.size(), 1);
        assertEquals(performances2.get(0).getPossibleMaxScore(), performance2.getPossibleMaxScore());
    }
}
