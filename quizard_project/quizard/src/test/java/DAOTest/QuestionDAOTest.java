package DAOTest;
import DB.ConnectionPool;
import model.DAO.QuestionDAO;
import model.DAO.QuizCategoryDAO;
import model.DAO.QuizDAO;
import model.DAO.UserDAO;
import model.enums.QuestionType;
import model.objects.Question;
import model.objects.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class QuestionDAOTest {
    private QuestionDAO questionDAO;
    private ConnectionPool pool;
    private int userId;
    private int quizId1;
    private int quizId2;
    private UserDAO userDAO;
    private QuizDAO quizDAO;
    private QuizCategoryDAO quizCategoryDAO;

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        pool = new ConnectionPool(true);
        questionDAO = new QuestionDAO(pool);
        userDAO = new UserDAO(pool);
        quizDAO = new QuizDAO(pool);
        quizCategoryDAO = new QuizCategoryDAO(pool);
        userId = userDAO.addUser("user1", "", "user1");
        quizCategoryDAO.addQuizCategory("math");
        quizId1 = quizDAO.addQuiz("first", "first quiz", "math", userId,
                false, false, false, false, LocalDateTime.now(), 10, 0, 100);
        quizId2 = quizDAO.addQuiz("second", "second quiz", "math", userId,
                false, false, false, false, LocalDateTime.now(), 10, 0, 100);
    }

    @After
    public void tearDown() throws SQLException {
        quizCategoryDAO.deleteAllCategories();
        userDAO.removeAll();
        pool.close();
    }

    @Test
    public void testAddAndGetQuestion() throws SQLException {
        String questionText = "What is the capital of France?";
        QuestionType questionType = QuestionType.Multiple_Choice;
        ArrayList<String> answer = new ArrayList<>();
        answer.add("Paris");
        Question q = new Question(quizId1, 1, questionText, questionType, answer);
        int questionId = questionDAO.addQuestion(quizId1, questionText, questionType.toString(), answer, new HashSet<>());
        assertNotEquals(-1, questionId);
        assertTrue(questionId >= 0);
        Question addedQuestion = questionDAO.getQuestion(questionId);
        assertNotNull(addedQuestion);
        assertEquals(questionText, addedQuestion.getQuestionTxt());
        assertEquals(questionType, addedQuestion.getQuestionType());
    }
    @Test
    public void testGetQuestionNonExistent() throws SQLException {
        // Test getting a question that doesn't exist
        Question nonExistentQuestion = questionDAO.getQuestion(-1);
        assertNull(nonExistentQuestion);
    }
    @Test
    public void testAddAndGetQuestionWithAnswers() throws SQLException {
        String questionText = "What is 2 + 2?";
        QuestionType questionType = QuestionType.Question_Response;
        ArrayList<String> answer = new ArrayList<>();
        answer.add("4");
        Question q = new Question(quizId1, 1, questionText, questionType, answer);
        int questionId = questionDAO.addQuestion(quizId1, questionText, questionType.toString(), answer, new HashSet<>());
        assertNotEquals(-1, questionId);
        assertTrue(questionId >= 0);
        Question addedQuestion = questionDAO.getQuestion(questionId);
        assertNotNull(addedQuestion);
        assertEquals(questionText, addedQuestion.getQuestionTxt());
        assertEquals(questionType, addedQuestion.getQuestionType());
        assertEquals(answer, addedQuestion.getAnswers());
    }
    @Test
    public void testGetQuestionsOfQuiz() throws SQLException {
        String questionText = "What is the capital of Italy?";
        QuestionType questionType = QuestionType.Multiple_Choice;
        ArrayList<String> answer = new ArrayList<>();
        answer.add("Rome");
        Question q = new Question(quizId1, 1, questionText, questionType, answer);
        int questionId = questionDAO.addQuestion(quizId1, questionText, questionType.toString(), answer, new HashSet<>());
        List<Integer> questions = questionDAO.getQuestionsOfQuiz(quizId1);
        assertNotNull(questions);
        assertEquals(questions.size(), 1);
    }

    @Test
    public void testGetAll() throws SQLException {
        String questionText = "What is the capital of Germany?";
        QuestionType questionType = QuestionType.Multiple_Choice;
        ArrayList<String> answer = new ArrayList<>();
        answer.add("Berlin");
        Question q = new Question(quizId1, 1, questionText, questionType, answer);
        int questionId = questionDAO.addQuestion(quizId1, questionText, questionType.toString(), answer, new HashSet<>());

        String questionText2 = "What is the capital of England?";
        QuestionType questionType2 = QuestionType.Multiple_Choice;
        ArrayList<String> answer2 = new ArrayList<>();
        answer2.add("London");
        Question q2 = new Question(quizId2, 2, questionText2, questionType2, answer2);
        int questionId2 = questionDAO.addQuestion(quizId2, questionText2, questionType2.toString(), answer2, new HashSet<>());
        assertEquals(questionDAO.getAllQuestions(quizId1).get(0).getQuestionTxt(), "What is the capital of Germany?");
        assertEquals(questionDAO.getAllQuestions(quizId2).get(0).getAnswers().get(0), "London");
    }

}