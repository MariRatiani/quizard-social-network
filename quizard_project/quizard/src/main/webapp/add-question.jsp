<%@ page import="model.DAO.QuizDAO" %>
<%@ page import="model.objects.Quiz" %>
<html>
<head>
    <title>add question</title>
    <link rel="stylesheet" href="styles/add-question.css">
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="script/add-question.js">
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


<div class="display" style="
    display: flex;">
    <div class="left">
        left
    </div>

        <%
        ServletContext context = (ServletContext) request.getServletContext();
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");

        int quizId = (Integer) request.getAttribute("quizId");
        Quiz quiz = quizDAO.getQuiz(quizId);

        String quizName = quiz.getName();
        String quizCategory = quiz.getCategory();
        int creatorId = (Integer) request.getAttribute("creatorId");
    %>
    <div class="main-part">
        <h1 id=""><%=quizName%>
        </h1>
        <h2><%=quizCategory%>
        </h2>
        <div class="question-create-divs">
            <div class="dropdown-div" id="dropdown-div">
                <button id="addQuestion">Add Question</button>
            </div>

            <form action="/addQuestion" name="questionAddForm" method="POST">
                <div id="Question-response" v-if="selectedOption" style="display: none;">
                    <p>standard text question with an appropriate text response. </p>
                    <input type="text" id="question1" placeholder="question" name="questionText"></input>
                    <br>
                    <label for="answer"></label><input type="text" id="answer" placeholder="answer"
                                                       name="answer"></input>
                    <br>
                    <input type="hidden" name="quizId" value=<%=quizId%>>
                    <input type="hidden" name="questionType" value=<%="Question_Response"%>>
                    <input type="hidden" name="creatorId" value=<%=creatorId%>>

                    <button id="submitButton">add this question</button>
                </div>
            </form>


            <form action="/addQuestion" name="questionAddForm" method="POST">
                <div id="fill-in-the-black" v-if="selectedOption" style="display: none;">
                    <p>Fill in the Blank type of question </p>
                    <input type="text" id="answerInput" placeholder="first half" name="firsthalf">
                    <input type="text" id="answer" placeholder="answer" name="answer">
                    <input type="text" id="second-half" placeholder="second half" name="secondhalf">
                    <br>
                    <input type="hidden" name="quizId" value=<%=quizId%>>
                    <input type="hidden" name="questionType" value=<%="Fill_In_The_Blank"%>>
                    <input type="hidden" name="creatorId" value=<%=creatorId%>>
                    <button id="submitButton">add this question</button>
                </div>
            </form>


            <form action="/addQuestion" name="questionAddForm" method="POST">
                <div id="multiple-choice" v-if="selectedOption" style="display: none;">
                    <p>multiple choice question, select correct answer with checkbox.</p>
                    <input type="text" id="question2" name="questionText" placeholder="write question">
                    <br>
                    <label>
                        <input type="radio" name="checkBoxMultiChoice" class="answer-checkbox">
                        <input type="text" name="answerChoice" placeholder="answer choice">
                    </label>
                    <br>
                    <label>
                        <input type="radio" name="checkBoxMultiChoice" class="answer-checkbox">
                        <input type="text" name="answerChoice" placeholder="answer choice">
                    </label>
                    <br>
                    <button id="add-answer-choice">add answer</button>
                    <br>

                    <input type="hidden" name="quizId" value=<%=quizId%>>
                    <input type="hidden" name="questionType" value=<%="Multiple_Choice"%>>
                    <input type="hidden" name="creatorId" value=<%=creatorId%>>
                    <button id="submitButton">add this question</button>
                </div>
            </form>

            <form action="/addQuestion" name="questionAddForm" method="POST">
                <div id="Picture_Response" v-if="selectedOption" style="display: none;">
                    <p>picture response</p>
                    <input type="text" name="pictureLink" placeholder="write picture link">
                    <input type="text" name="questionText" placeholder="question">
                    <input type="text" name="answer" placeholder="answer">
                    <br>

                    <input type="hidden" name="quizId" value=<%=quizId%>>
                    <input type="hidden" name="questionType" value=<%="Picture_Response"%>>
                    <input type="hidden" name="creatorId" value=<%=creatorId%>>
                    <button id="submitButton">add this question</button>
                </div>
            </form>

            <form action="/addQuestion" name="questionAddForm" method="POST">
                <div id="multi-answer" v-if="selectedOption" style="display: none;">
                    <p>This is similar to the standard question-response, except
                        there needs to be more than one text field for responses</p>
                    <br>
                    <input type="text" id="question3" placeholder="question">
                    <br>
                    <input type="text" placeholder="answer">
                    <br>
                    <input type="text" name="answer" placeholder="answer">
                    <br>
                    <button id="add_answer">add answer</button>
                    <br>
                    <input type="hidden" name="quizId" value=<%=quizId%>>
                    <input type="hidden" name="questionType" value=<%="Multi_Answer"%>>
                    <input type="hidden" name="creatorId" value=<%=creatorId%>>
                    <button id="submitButton">add this question</button>
                </div>
            </form>

            <form action="/addQuestion" name="questionAddForm" method="POST">
                <div id="Multiple_Choice_Multiple_Answers" v-if="selectedOption" style="display: none;">
                    <p>This is similar to a standard multiple choice
                        question, except the player can select more than one response. </p>

                    <input type="text" name="questionText" id="question4" placeholder="question">

                    <br>
                    <label>
                        <input type="radio" name="checkBoxMultiChoice" class="answer-checkbox">
                        <input type="text" name="answerChoice" placeholder="answer choice">
                    </label>
                    <br>
                    <label>
                        <input type="radio" name="checkBoxMultiChoice" class="answer-checkbox">
                        <input type="text" name="answerChoice" placeholder="answer choice">
                    </label>
                    <br>
                    <button id="add-answer-choice">add answer</button>
                    <br>

                    <input type="hidden" name="quizId" value=<%=quizId%>>
                    <input type="hidden" name="questionType" value=<%="Multiple_Choice_Multiple_Answers"%>>
                    <input type="hidden" name="creatorId" value=<%=creatorId%>>
                    <button id="submitButton">add this question</button>
                </div>
            </form>

            <div class="button-div">
                <button class="button" type="submit"> add new question</button>
            </div>

        </div>
    </div>
    <div class="right">
    </div>
</body>
<script src="./script/add-question.js"></script>
</html>