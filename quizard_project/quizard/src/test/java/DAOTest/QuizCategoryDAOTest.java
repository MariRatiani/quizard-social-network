package DAOTest;
import DB.ConnectionPool;
import model.DAO.AnnouncementDAO;
import model.DAO.QuizCategoryDAO;
import model.DAO.UserDAO;
import model.objects.Announcement;
import model.objects.QuizCategory;
import model.objects.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizCategoryDAOTest {
    private ConnectionPool pool;
    private QuizCategoryDAO quizCategoryDAO;

    @Before
    public void setUp() throws SQLException {
        pool = new ConnectionPool(true);
        quizCategoryDAO = new QuizCategoryDAO(pool);
    }

    @After
    public void tearDown() throws SQLException {
        // Release any remaining connections and clean up after each test
        quizCategoryDAO.deleteAllCategories();
        pool.close();
    }

    @Test
    public void testAddQuizCategory() throws SQLException {
        // Add a new quiz category
        String categoryName = "Test Category";
        int a = quizCategoryDAO.addQuizCategory(categoryName);
        String categoryName2 = "Test Category2";
        int b = quizCategoryDAO.addQuizCategory(categoryName2);
        List<QuizCategory> categories = quizCategoryDAO.getAllQuizCategories();
        assertNotNull(categories);
        Assert.assertEquals(categories.size(), 2);
        QuizCategory first = quizCategoryDAO.getQuizCategoryById(a);
        Assertions.assertEquals(first.getCategoryId(), a);
        assertEquals(first.getCategoryName(), categoryName);
    }
    @Test
    public void testDelete() throws SQLException {
        String categoryName = "Test Categor3";
        int a = quizCategoryDAO.addQuizCategory(categoryName);
        String categoryName2 = "Test Category4";
        int b = quizCategoryDAO.addQuizCategory(categoryName2);
        quizCategoryDAO.deleteQuizCategory(a);
        QuizCategory q = quizCategoryDAO.getQuizCategoryById(a);
        assertNull(q);
        quizCategoryDAO.deleteQuizCategory(categoryName2);
        QuizCategory w = quizCategoryDAO.getQuizCategoryById(a);
        assertNull(w);
    }

    @Test
    public void testDeleteAll() throws SQLException {
        String categoryName = "Test Category5";
        int a = quizCategoryDAO.addQuizCategory(categoryName);
        String categoryName2 = "Test Category6";
        int b = quizCategoryDAO.addQuizCategory(categoryName2);
        assertEquals(quizCategoryDAO.getAllQuizCategories().size(), 2);
        quizCategoryDAO.deleteAllCategories();
        assertEquals(quizCategoryDAO.getAllQuizCategories().size(), 0);
    }

}
