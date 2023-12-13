<%@ page import="model.objects.Quiz" %>
<%@ page import="model.DAO.QuizDAO" %>
<%@ page import="model.DAO.UserDAO" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.objects.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        Quiz Overview
    </title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/quiz-summary.css">
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
        <button class="quizzes-button">quizzes</button>
        <button class="create-button">create</button>
    </div>
    <div class="right-section">
        <button class="messages-button">
            <img class="image-message" src="images/message-2.png">
        </button>
        <button class="User-button">U</button>
    </div>
</div>

<%
    ServletContext context = request.getServletContext();
    QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
    UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");

    Quiz quiz = null;
    int quizId = Integer.parseInt(request.getParameter("quizId"));
    quiz = (Quiz) quizDAO.getQuiz(quizId);

    String name = quiz.getName();
    String description = quiz.getDescription();
    String category = quiz.getCategory();
    int creatorId = quiz.getCreatorID();
    User auth;
    try {
        auth = userDAO.getUser(creatorId);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    String creator = auth.getName();
    int timeLimit = quiz.getTimeLimit();
%>
<div class="display">
    <!-- <div class="left-section2"></div> -->
    <div class="quiz-info">
        <h1 class="quiz-name"><%=name%>
        </h1>
        <div>
            <p class="quiz-desc"><%=description%>
            </p>
            <p>Creator: <%=creator%>
            </p>
            <p>Category: <%=category%>
            </p>
            <p>time Limit: <%=timeLimit%>
            </p>
        </div>
        <div class="buttons">
            <form id="startForm" action="../quizStart" method="post">
                <input type="hidden" name="quizId" id="quizId" value="<%=quizId%>"/>
                <input type="hidden" name="practiceMode" id="practiceMode" value=""/>
                <button class="start-button" onclick="setPracticeMode('false')">start</button>
                <button class="practice-mode-button" onclick="setPracticeMode('true')">practice mode</button>
            </form>
        </div>
    </div>

    <form id="buttonForm" action="../quizLists" method="post">
        <div class="right-section2" style="
        display: flex;
        flex-direction: column;
    ">
            <button class="past-performances" onclick="setButtonValue('past-performances')">Your Past Performances
            </button>
            <button class="top-performances" onclick="setButtonValue('top-performances')">Top Performances</button>
            <button class="top-last-day" onclick="setButtonValue('top-last-day')">Top Performances of Last Day</button>
            <button class="recent-quiz-taking" onclick="setButtonValue('recent-quiz-taking')">Recent Quiz Taking Activities
            </button>
            <button class="statistics" onclick="setButtonValue('statistics')">Statistics</button>
            <input type="hidden" name="quizId" id="quizId" value="<%=quizId%>"/>
            <input type="hidden" id="clickedButton" name="buttonClicked" value=""/>
        </div>
    </form>

    <script>
        function setPracticeMode(practiceMode) {
            document.getElementById('practiceMode').value = practiceMode;
            document.getElementById('startForm').submit();
        }
    </script>

    <script>
        function setButtonValue(buttonName) {
            document.getElementById('buttonClicked').value = buttonName;
            document.getElementById('buttonForm').submit();
        }
    </script>

</div>
</body>
</html>