<%@ page import="model.enums.QuestionType" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.DAO.QuizDAO" %>
<%@ page import="model.DAO.QuestionDAO" %>
<%@ page import="model.objects.Quiz" %>
<%@ page import="model.objects.Question" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>take quiz</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/quiz-taking.css">
    <link rel="script" href="script/quiz-taking.js">
</head>

<body>

<!-- HEADER PART, SHOULD BE COPIED IN EVERY PAGE -->

<div class="header" style="
      display: flex;
      flex-direction: row;
      align-items: center;
    ">
    <div class="left-section">
        <button class="quizard"> Quizard</button>
    </div>
    <div class="middle-section">
        <button class="create-button" onclick="window.location.href = 'view-quizzes.jsp';">quizzes</button>
        <button class="create-button" onclick="window.location.href = 'quiz-creation.jsp';">create</button>
    </div>
    <div class="right-section">
        <button class="messages-button">
            <img class="image-message" src="images/message-2.png">
        </button>
        <button class="User-button">U</button>
    </div>
</div>

<!-- until here -->

<%
    List<Integer> questions = (ArrayList<Integer>) request.getAttribute("questions");
    QuizDAO quizDAO = (QuizDAO) request.getServletContext().getAttribute("quizDAO");
    QuestionDAO questionDAO = (QuestionDAO) request.getServletContext().getAttribute("questionDAO");

    int quizId = (Integer) request.getAttribute("quizId");
    Quiz quiz = quizDAO.getQuiz(quizId);
    String quizName = quiz.getName();
    int maxScore = quiz.getMaxPoints();
    int score = 0;
%>


<div class="display" style="
  display: flex;">

    <div class="left">

    </div>

        <div class="main-part">
            <p><%=quizName%>
            </p>
            <%
                String questionTxt = null;
                for (int questionId : questions) {
                    Question question = questionDAO.getQuestion(questionId);
                    QuestionType questionType = question.getQuestionType();
                    questionTxt = question.getQuestionTxt();
            %>

            <div class="quiz-types" id="quiz-types">

                <%
                    if (questionType.equals(QuestionType.Question_Response)) {

                %>

                <form action="/immediateCorrection" method="POST">
                    <div>
                        <p><%=questionTxt%>
                        </p>
                        <p></p>answer: <input type="text" id="answer" name="answer"/></p>
                        <input type="hidden" name="questionId" value="<%= questionId %>">
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                    </div>
                    <button class="button" type="submit"> check</button>
                </form>
                <br>
                <br>


                <% } else if (questionType.equals(QuestionType.Fill_In_The_Blank)) {
                %>

                <form>
                    <div id="fill-blank">
                        <%
                            String[] parts = questionTxt.split("\\$");
                            String firstPart = parts[0];
                            String secondPart = parts[1];
                        %>
                        <p>fill in the blank </p>
                        <label><%=firstPart%>
                        </label>
                        <input type="text" placeholder="write answer" name="userAnswer">
                        <label><%=secondPart%>
                        </label>
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                    </div>
                </form>
                <div id="ajaxImmediateCorrectionResponse"></div>

                <% } else if (questionType.equals(QuestionType.Multiple_Choice)) {
                %>

                <form>
                    <div id="multi-choice">
                        <label><%=questionTxt%>
                        </label>
                        <label>Select one option:</label>

                        <input type="radio" id="option11" name="choice" value="option1">
                        <label for="option11">Option 1</label><br>
                        <input type="radio" id="option22" name="choice" value="option2">
                        <label for="option22">Option 2</label><br>
                        <input type="radio" id="option33" name="choice" value="option3">
                        <label for="option33">Option 3</label><br>
                        <input type="radio" id="option44" name="choice" value="option4">
                        <label for="option44">Option 4</label><br>
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                    </div>
                </form>
                <div id="ajaxImmediateCorrectionResponse"></div>

                <% } else if (questionType.equals(QuestionType.Picture_Response)) {
                %>
                <form>
                    <div id="picture-resp">
                        <img src="image.jpg" alt="upload image here">
                        <br>
                        <input type="text" placeholder="answer">
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                    </div>
                    <button class="button">check</button>
                </form>
                <div id="ajaxImmediateCorrectionResponse"></div>

                <% } else if (questionType.equals(QuestionType.Multi_Answer)) {
                %>
                <form>
                    <div id="multi_answer">
                        <p>write Question here</p>
                        <input type="text" placeholder="write answer">
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                    </div>
                </form>
                <div id="ajaxImmediateCorrectionResponse"></div>

                <% } else if (questionType.equals(QuestionType.Multiple_Choice_Multiple_Answers)) {
                %>
                <form>
                    <div id="multi-choice_multi-ans">
                        <p>select any answers you think is right</p>

                        <input type="checkbox" id="option1" name="choices" value="option1">
                        <label for="option1">Option 1</label><br>

                        <input type="checkbox" id="option2" name="choices" value="option2">
                        <label for="option2">Option 2</label><br>

                        <input type="checkbox" id="option3" name="choices" value="option3">
                        <label for="option3">Option 3</label><br>

                        <input type="checkbox" id="option4" name="choices" value="option4">
                        <label for="option4">Option 4</label><br>
                        <input type="hidden" name="quizId" value="<%= quizId %>">
                    </div>
                </form>
                <div id="ajaxImmediateCorrectionResponse"></div>
                <%
                        }
                    }
                %>

            </div> <!-- "quiz-types" done -->
            <button class="button">End Quiz</button>
        </div>

</div>

<div class="right">
</div>

</body>
</html>