<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="model.objects.QuizPerformance" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.DAO.QuizPerformanceDAO" %>
<%@ page import="model.DAO.UserDAO" %>
<%@ page import="model.DAO.QuizDAO" %>
<%@ page import="model.objects.Quiz" %><%--<%@ page import="java.util.Arrays" %>--%>
<%--<%@ page import="java.util.List" %>&lt;%&ndash;<!DOCTYPE html>&ndash;%&gt;--%>
<%--&lt;%&ndash;<html>&ndash;%&gt;--%>
<%--&lt;%&ndash;<head>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <title>Top Performances</title>&ndash;%&gt;--%>
<%--&lt;%&ndash;    <link rel="stylesheet" href="styles/quiz-lists.css">&ndash;%&gt;--%>
<%--&lt;%&ndash;    <link rel="stylesheet" href="styles/header.css">&ndash;%&gt;--%>
<%--&lt;%&ndash;    <style>&ndash;%&gt;--%>
<%--&lt;%&ndash;        body {&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin: 0;&ndash;%&gt;--%>
<%--&lt;%&ndash;            font-family: Arial, sans-serif;&ndash;%&gt;--%>
<%--&lt;%&ndash;            display: flex;&ndash;%&gt;--%>
<%--&lt;%&ndash;            flex-direction: column;&ndash;%&gt;--%>
<%--&lt;%&ndash;            height: 100vh;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;        .header {&ndash;%&gt;--%>
<%--&lt;%&ndash;            display: flex;&ndash;%&gt;--%>
<%--&lt;%&ndash;            justify-content: space-between;&ndash;%&gt;--%>
<%--&lt;%&ndash;            align-items: center;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 10px 20px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            background-color: #f0f0f0;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;        .content {&ndash;%&gt;--%>
<%--&lt;%&ndash;            display: flex;&ndash;%&gt;--%>
<%--&lt;%&ndash;            flex-grow: 1;&ndash;%&gt;--%>
<%--&lt;%&ndash;            overflow: hidden;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;        .quiz-list {&ndash;%&gt;--%>
<%--&lt;%&ndash;            flex: 2;&ndash;%&gt;--%>
<%--&lt;%&ndash;            max-height: calc(100vh - 60px); /* Adjust based on your header height */&ndash;%&gt;--%>
<%--&lt;%&ndash;            overflow-y: auto;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 20px;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;        .quiz-item {&ndash;%&gt;--%>
<%--&lt;%&ndash;            margin-bottom: 15px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 10px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            background-color: #f0f0f0;&ndash;%&gt;--%>
<%--&lt;%&ndash;            border-radius: 5px;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;        .buttons {&ndash;%&gt;--%>
<%--&lt;%&ndash;            flex: 1;&ndash;%&gt;--%>
<%--&lt;%&ndash;            padding: 20px;&ndash;%&gt;--%>
<%--&lt;%&ndash;            background-color: #f0f0f0;&ndash;%&gt;--%>
<%--&lt;%&ndash;            overflow-y: auto;&ndash;%&gt;--%>
<%--&lt;%&ndash;        }&ndash;%&gt;--%>
<%--&lt;%&ndash;    </style>&ndash;%&gt;--%>
<%--&lt;%&ndash;</head>&ndash;%&gt;--%>
<%--&lt;%&ndash;<body>&ndash;%&gt;--%>

<%--&lt;%&ndash;<div class="header">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <div class="header" style="&ndash;%&gt;--%>
<%--&lt;%&ndash;          display: flex;&ndash;%&gt;--%>
<%--&lt;%&ndash;          flex-direction: row;&ndash;%&gt;--%>
<%--&lt;%&ndash;          align-items: center;&ndash;%&gt;--%>
<%--&lt;%&ndash;          ">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <div class="left-section">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <button class="quizard"> Quizard</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <div class="middle-section">&ndash;%&gt;--%>
<%--&lt;%&ndash;                <button class="quizzes-button">quizzes</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <button class="create-button">create</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <div class="right-section" >&ndash;%&gt;--%>
<%--&lt;%&ndash;                <button class="messages-button">&ndash;%&gt;--%>
<%--&lt;%&ndash;                    <img  class="image-message" src="images/message-2.png">&ndash;%&gt;--%>
<%--&lt;%&ndash;                </button>&ndash;%&gt;--%>
<%--&lt;%&ndash;                <button class="User-button">U</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>

<%--&lt;%&ndash;<div class="content">&ndash;%&gt;--%>
<%--   <div class="quiz-list">&ndash;%&gt;--%>
<%--<h1>Top 10 Performances</h1>&ndash;%&gt;&ndash;%&gt;--%>
<%-- &lt;%&ndash;%>--%>
<%--        List<String> quizList = Arrays.asList("ram", "pam", "tan", "ram", "pam", "tan", "ram", "pam", "tan");&ndash;%&gt;--%>
<%--       for(String quizName : quizList) {&ndash;%&gt;--%>
<%--   <div class="quiz-item">&ndash;%&gt;--%>
<%--            <ul>--%>
<%--                <li><%=quizName%></li>--%>
<%--                <li>kide ragacebi, rac sachiroa</li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--        &lt;%&ndash;%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--&lt;%&ndash;    <div class="buttons">&ndash;%&gt;--%>
<%--&lt;%&ndash;        <!-- Add your buttons here -->&ndash;%&gt;--%>
<%--&lt;%&ndash;        <div class="right-section2" style="&ndash;%&gt;--%>
<%--&lt;%&ndash;          display: flex;&ndash;%&gt;--%>
<%--&lt;%&ndash;          flex-direction: column;&ndash;%&gt;--%>
<%--&lt;%&ndash;          ">&ndash;%&gt;--%>
<%--&lt;%&ndash;            <!-- <img class="photo" src="images/background-blue.jpg" alt=""> -->&ndash;%&gt;--%>
<%--&lt;%&ndash;            <button class="past-performances">Your Past Performances</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <button class="top-performances">Top Performances</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <button class="top-last-day">Top Performances of Last Day</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <button class="recent-quiz-taking">recent Quiz taking activities</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;            <button class="statistics">statistics</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;        </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;    </div>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>

<%--&lt;%&ndash;</body>&ndash;%&gt;--%>
<%--&lt;%&ndash;</html>&ndash;%&gt;--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Top Performances</title>--%>
<%--    <link rel="stylesheet" href="styles/quiz-lists.css">--%>
<%--    <link rel="stylesheet" href="styles/header.css">--%>
<%--    <style>--%>
<%--        body {--%>
<%--            margin: 0;--%>
<%--            font-family: Arial, sans-serif;--%>
<%--            display: flex;--%>
<%--            flex-direction: column;--%>
<%--            height: 100vh;--%>
<%--        }--%>
<%--        .header {--%>
<%--            display: flex;--%>
<%--            justify-content: space-between;--%>
<%--            align-items: center;--%>
<%--            padding: 10px 20px;--%>
<%--            background-color: #f0f0f0;--%>
<%--        }--%>
<%--        .content {--%>
<%--            display: flex;--%>
<%--            flex-grow: 1;--%>
<%--            overflow: hidden;--%>
<%--        }--%>
<%--        .quiz-list {--%>
<%--            flex: 2;--%>
<%--            max-height: 80vh; /* Adjust based on your design */--%>
<%--            overflow-y: auto;--%>
<%--            padding: 20px;--%>
<%--        }--%>
<%--        .quiz-item {--%>
<%--            margin-bottom: 15px;--%>
<%--            padding: 10px;--%>
<%--            background-color: #f0f0f0;--%>
<%--            border-radius: 5px;--%>
<%--        }--%>
<%--        .buttons {--%>
<%--            flex: 1;--%>
<%--            padding: 20px;--%>
<%--            background-color: #f0f0f0;--%>
<%--            overflow-y: auto;--%>
<%--            display: flex;--%>
<%--            flex-direction: column;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>

<%--<div class="header">--%>
<%--    <div class="left-section">--%>
<%--        <button class="quizard">Quizard</button>--%>
<%--    </div>--%>
<%--    <div class="middle-section">--%>
<%--        <button class="quizzes-button">quizzes</button>--%>
<%--        <button class="create-button">create</button>--%>
<%--    </div>--%>
<%--    <div class="right-section">--%>
<%--        <button class="messages-button">--%>
<%--            <img class="image-message" src="images/message-2.png">--%>
<%--        </button>--%>
<%--        <button class="User-button">U</button>--%>
<%--    </div>--%>
<%--</div>--%>

<%--<div class="content">--%>
<%--    <div class="quiz-list">--%>
<%--&lt;%&ndash;        <h1>Top 10 Performances</h1>&ndash;%&gt;--%>
<%--        <%--%>
<%--            List<String> quizList = Arrays.asList("ram", "pam", "tan", "ram", "pam", "tan", "ram", "pam", "tan");--%>
<%--            for(String quizName : quizList) {--%>
<%--        %>--%>
<%--        <div class="quiz-item">--%>
<%--            <ul>--%>
<%--                <li><%=quizName%></li>--%>
<%--                <li>kide ragacebi, rac sachiroa</li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--        <%--%>
<%--            }--%>
<%--        %>--%>
<%--    </div>--%>
<%--    <div class="buttons">--%>
<%--        <!-- Add your buttons here -->--%>
<%--        <div class="right-section2" style="--%>
<%--          display: flex;--%>
<%--          flex-direction: column;--%>
<%--          ">--%>
<%--            <!-- <img class="photo" src="images/background-blue.jpg" alt=""> -->--%>
<%--            <button class="past-performances">Your Past Performances</button>--%>
<%--            <button class="top-performances">Top Performances</button>--%>
<%--            <button class="top-last-day">Top Performances of Last Day</button>--%>
<%--            <button class="recent-quiz-taking">recent Quiz taking activities</button>--%>
<%--            <button class="statistics">statistics</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>


<%--<html>--%>
<%--<head>--%>
<%--    <title>--%>
<%--        <!-- take from request, what list is presented -->--%>
<%--        Top Performances--%>
<%--    </title>--%>
<%--    <link rel="stylesheet" href="styles/quiz-lists.css">--%>
<%--    <link rel="stylesheet" href="styles/header.css">--%>


<%--</head>--%>
<%--<body>--%>

<%--<!-- HEADER PART, SHOULD BE COPIED IN EVERY PAGE -->--%>

<%--<div class="header" style="--%>
<%--      display: flex;--%>
<%--      flex-direction: row;--%>
<%--      align-items: center;--%>
<%--      ">--%>
<%--    <div class="left-section">--%>
<%--        <button class="quizard"> Quizard</button>--%>
<%--    </div>--%>
<%--    <div class="middle-section">--%>
<%--        <button class="quizzes-button">quizzes</button>--%>
<%--        <button class="create-button">create</button>--%>
<%--    </div>--%>
<%--    <div class="right-section" >--%>
<%--        <button class="messages-button">--%>
<%--            <img  class="image-message" src="images/message-2.png">--%>
<%--        </button>--%>
<%--        <button class="User-button">U</button>--%>
<%--    </div>--%>
<%--</div>--%>

<%--<!-- until here -->--%>

<%--<div class="display">--%>
<%--    <!-- <div class="left-section2"></div> -->--%>
<%--    <div class ="left-section2" style="--%>
<%--          display: flex;--%>
<%--          flex-direction: column;--%>
<%--          width: 100%;--%>
<%--          align-items: center;--%>
<%--        ">--%>
<%--        <div class="title-of-list">--%>
<%--            <h1>--%>
<%--                Top 10 Performances--%>
<%--            </h1>--%>
<%--        </div>--%>

<%--        <div class="list" style="--%>
<%--            display: flex;--%>
<%--            flex-direction: column;--%>
<%--            width: 100%;--%>
<%--          ">--%>
<%--            <!-- AQ 10 LISTI FOR-IT RO WAMOIGO -->--%>
<%--            <button class="one-performance">--%>
<%--                <ul>--%>
<%--                    <li>Quiz Name</li>--%>
<%--                    <li>kide ragacebi, rac sachiroa</li>--%>
<%--                </ul>--%>
<%--            </button>--%>
<%--        </div>--%>

<%--    <div class="buttons">--%>
<%--        <!-- Add your buttons here -->--%>
<%--        <div class="right-section2" style="--%>
<%--          display: flex;--%>
<%--          flex-direction: column;--%>
<%--          ">--%>
<%--            <!-- <img class="photo" src="images/background-blue.jpg" alt=""> -->--%>
<%--            <button class="past-performances">Your Past Performances</button>--%>
<%--            <button class="top-performances">Top Performances</button>--%>
<%--            <button class="top-last-day">Top Performances of Last Day</button>--%>
<%--            <button class="recent-quiz-taking">recent Quiz taking activities</button>--%>
<%--            <button class="statistics">statistics</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>

<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 19.08.2023
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>
    </title>
    <link rel="stylesheet" href="styles/quiz-lists.css">
    <link rel="stylesheet" href="styles/header.css">


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

<!-- until here -->

<div class="display">
    <div class="left-section2" style="
          display: flex;
          flex-direction: column;
          width: 100%;
          align-items: center;
        ">
        <div class="title-of-list">
            <h1>
                <%=(String) request.getAttribute("listName")%>
            </h1>
        </div>

        <div class="list" style="display: flex;flex-direction: column;width: 70%;">
            <%
                List<QuizPerformance> performanceList;
                performanceList = (List<QuizPerformance>) request.getAttribute("performancelist");
                ServletContext context = request.getServletContext();
                UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
                QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");

                for (QuizPerformance qp : performanceList) {
                    int quizId = qp.getQuizId();
                    Quiz quiz = quizDAO.getQuiz(quizId);
                    String quizName = quiz.getName();
                    String category = quiz.getCategory();
                    int score = qp.getScore();
                    int maxScore = qp.getPossibleMaxScore();
                    String username = userDAO.getUser(qp.getUserId()).getName();
                    String result = "" + score + "/" + maxScore;
            %>
            <button class="one-performance">
                <div>
                    <span><%=quizName%></span>
                    <span><%=category%></span>
                </div>
                <div>
                    <span><%=username%></span>
                    <span><%=result%></span>
                </div>
            </button>
            <%}%>
        </div>

    </div>

    <div class="buttons">
        <div class="right-section2" style="display: flex; flex-direction: column;">
            <form action="/quizLists" method="post">
                <button class="past-performances" name="buttonClicked" value="past">Your Past Performances</button>
                <button class="top-performances" name="buttonClicked" value="top">Top Performances</button>
                <button class="top-last-day" name="buttonClicked" value="last">Top Performances of Last Day</button>
                <button class="recent-quiz-taking" name="buttonClicked" value="recent">Recent Quiz Taking Activities
                </button>
                <button class="statistics" name="buttonClicked" value="statistics">Statistics</button>
            </form>
        </div>
    </div>
    <div class="third-section">

    </div>
</div>
</body>

</html>