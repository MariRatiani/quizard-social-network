package DAOTest;
import com.mysql.cj.jdbc.Driver;
import DB.ConnectionPool;
import junit.framework.TestCase;
import model.DAO.AnnouncementDAO;
import model.DAO.UserDAO;
import model.objects.Announcement;
import model.objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class AnnouncementDAOTest extends TestCase {
    private ConnectionPool pool;
    private AnnouncementDAO announcementDAO;
    private UserDAO userDAO;
    private int userId1;
    private int userId2;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        pool = new ConnectionPool(true);
        announcementDAO = new AnnouncementDAO(pool);
        userDAO = new UserDAO(pool);
        userId1 = userDAO.addUser("user1", "", "user1");
        userId2 = userDAO.addUser("user2", "", "user2");
    }

    @After
    public void tearDown() throws SQLException {
        userDAO.removeAll();
        pool.close();
    }
    @Test
    public void testWithNoAnnouncements() throws SQLException {
        ArrayList<Announcement> a = announcementDAO.getAnnouncements();
        System.out.println(a.size());
        assert(a.isEmpty());
    }
    @Test
    public void testAddAndGetAnnouncement() throws SQLException {
        Timestamp timePosted = Timestamp.valueOf("2023-07-30 12:34:56");
        Announcement announcement = new Announcement(1, userId1,"Test Announcement", "This is a test announcement.", timePosted);
        int newId = announcementDAO.addAnnouncement(userId1,"Test Announcement", "This is a test announcement.", timePosted);
        Announcement announcement2 = new Announcement(2, userId1,"Test Announcement", "This is a test announcement.", timePosted);
        int newId2 = announcementDAO.addAnnouncement(userId1,"Test Announcement", "This is a test announcement.", timePosted);
        ArrayList<Announcement> addedAnnouncements = announcementDAO.getAnnouncements();
        assertNotNull(addedAnnouncements);
        Announcement a = addedAnnouncements.get(0);
        Announcement b = addedAnnouncements.get(1);
        assertEquals(announcement.getTitle(), a.getTitle());
        assertEquals(announcement.getDescription(),a.getDescription());
        assertEquals(announcement.getTime_posted(), a.getTime_posted());
        assertEquals(announcement2.getTitle(), b.getTitle());
        assertEquals(announcement2.getDescription(),b.getDescription());
        assertEquals(announcement2.getTime_posted(), b.getTime_posted());
        announcementDAO.deleteAnnouncement(newId);
        announcementDAO.deleteAnnouncement(newId2);
        assert(announcementDAO.getAnnouncements().isEmpty());
    }
    @Test
    public void testAddAndGetAnnouncementById() throws SQLException {
        Timestamp timePosted = Timestamp.valueOf("2023-07-30 12:34:56");
        Announcement announcement = new Announcement(1, userId1,"Test Announcement", "This is a test announcement.", timePosted);
        int newId = announcementDAO.addAnnouncement(userId1,"Test Announcement", "This is a test announcement.", timePosted);
        Announcement announcement2 = new Announcement(2, userId1,"Test Announcement", "This is a test announcement.", timePosted);
        int newId2 = announcementDAO.addAnnouncement(userId1,"Test Announcement", "This is a test announcement.", timePosted);
        Announcement announcement3 = new Announcement(3, userId2,"Test Announcement", "This is a test announcement.", timePosted);
        int newId3 = announcementDAO.addAnnouncement(userId2,"Test Announcement", "This is a test announcement.", timePosted);
        ArrayList<Announcement> addedAnnouncements = announcementDAO.getAnnouncementsOfAUser(userId1);
        assertNotNull(addedAnnouncements);
        Announcement a = addedAnnouncements.get(0);
        Announcement b = addedAnnouncements.get(1);
        assertEquals(announcement.getTitle(), a.getTitle());
        assertEquals(announcement.getDescription(),a.getDescription());
        assertEquals(announcement.getTime_posted(), a.getTime_posted());
        assertEquals(announcement2.getTitle(), b.getTitle());
        assertEquals(announcement2.getDescription(),b.getDescription());
        assertEquals(announcement2.getTime_posted(), b.getTime_posted());
        assertEquals(announcementDAO.getNewAnnouncements().size(), 3);
        Announcement announcement4 = new Announcement(4, userId2,"Test Announcement", "This is a test announcement.", timePosted);
        int newId4 = announcementDAO.addAnnouncement(userId2,"Test Announcement", "This is a test announcement.", timePosted);
        ArrayList<Announcement> shouldBeThree = announcementDAO.getNewAnnouncements();
        assertEquals(shouldBeThree.size(), 3);
        announcementDAO.deleteAnnouncement(newId);
        announcementDAO.deleteAnnouncement(newId2);
        announcementDAO.deleteAnnouncement(newId3);
        announcementDAO.deleteAnnouncement(newId4);
        assert(announcementDAO.getAnnouncements().isEmpty());
    }

}
