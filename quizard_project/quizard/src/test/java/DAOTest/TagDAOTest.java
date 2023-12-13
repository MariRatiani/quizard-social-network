package DAOTest;

import DB.ConnectionPool;
import model.DAO.QuizCategoryDAO;
import model.DAO.QuizDAO;
import model.DAO.TagDAO;
import model.DAO.UserDAO;
import model.objects.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TagDAOTest {
    private ConnectionPool pool;
    private TagDAO tagDAO;
    private UserDAO userDAO;
    private QuizCategoryDAO quizCategoryDAO;
    private QuizDAO quizDAO;
    private int userId;
    private int quizId1;
    private int quizId2;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        pool = new ConnectionPool(true);
        tagDAO = new TagDAO(pool);
        userDAO = new UserDAO(pool);
        quizCategoryDAO = new QuizCategoryDAO(pool);
        quizDAO = new QuizDAO(pool);
        userId = userDAO.addUser("user1", "", "user1");
        quizCategoryDAO.addQuizCategory("Geography");
        quizId1 = quizDAO.addQuiz("first", "first quiz", "Geography", userId,
                false, false, false, false, LocalDateTime.now(), 10, 0, 100);
        quizId2 = quizDAO.addQuiz("second", "second quiz", "Geography", userId,
                false, false, false, false, LocalDateTime.now(), 10, 0, 100);
    }

    @After
    public void tearDown() throws SQLException {
        tagDAO.deleteAllTags();
        quizCategoryDAO.deleteAllCategories();
        userDAO.removeAll();
        pool.close();
    }

    @Test
    public void testAddQuizTag() throws SQLException {
        String tagName1 = "Test tag1";
        int a = tagDAO.addQuizTag(quizId1, tagName1);
        String tagName2 = "Test tag2";
        int b = tagDAO.addQuizTag(quizId2, tagName2);
        List<Tag> tags1 = tagDAO.getTagsOfQuiz(quizId1);
        assertNotNull(tags1);
        assertEquals(tags1.get(0).getTagName(), tagName1);
        assertEquals(tags1.get(0).getQuizID(), quizId1);
        List<Tag> tags2 = tagDAO.getTagsOfQuiz(quizId2);
        assertNotNull(tags2);
        assertEquals(tags2.get(0).getTagName(), tagName2);
        assertEquals(tags2.get(0).getTagID(), b);
        assertEquals(tagDAO.getAllQuizTags().size(), 2);
    }
    @Test
    public void testDelete() throws SQLException {
        String tagName1 = "Test tag3";
        int a = tagDAO.addQuizTag(quizId1, tagName1);
        String tagName2 = "Test tag4";
        int b = tagDAO.addQuizTag(quizId2, tagName2);
        assertNotNull(tagDAO.getQuizTagById(a));
        tagDAO.deleteQuizTag(a);
        Tag t1 = tagDAO.getQuizTagById(a);
        assertNull(t1);
        assertNotNull(tagDAO.getQuizTagById(b));
        tagDAO.deleteQuizTag(quizId2, tagName2);
        assertNull(tagDAO.getQuizTagById(b));
    }

    @Test
    public void testDeleteAll() throws SQLException {
        String tagName1 = "Test tag5";
        int a = tagDAO.addQuizTag(quizId1, tagName1);
        String tagName2 = "Test tag6";
        int b = tagDAO.addQuizTag(quizId2, tagName2);
        assertEquals(tagDAO.getAllQuizTags().size(), 2);
        String tagName3 = "Test tag7";
        int c = tagDAO.addQuizTag(quizId2, tagName3);
        String tagName4 = "Test tag8";
        int d = tagDAO.addQuizTag(quizId2, tagName4);
        assertEquals(tagDAO.getTagsOfQuiz(quizId2).size(), 3);
        tagDAO.deleteAllTagsOfQuiz(quizId2);
        assertEquals(tagDAO.getTagsOfQuiz(quizId2).size(), 0);
        assertEquals(tagDAO.getAllQuizTags().size(), 1);
        tagDAO.deleteAllTags();
        assertEquals(tagDAO.getAllQuizTags().size(), 0);
    }

}
