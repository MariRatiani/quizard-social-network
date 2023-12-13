<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 21.08.2023
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>
        score
    </title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/scoring.css">
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
    <div class="right-section" >
        <button class="messages-button">
            <img  class="image-message" src="images/message-2.png">
        </button>
        <button class="User-button">U</button>
    </div>
</div>

<div class="display" style="
    display: flex;
    flex-direction: row;">
    <div class="left"></div>

    <div class="main-part">
        <!-- Quizzes will be scored on the basis of how many questions a user got right. They should also
  keep track of how long a user took to complete the quiz. For multi-answer questions, treat
  each blank as a separate answer for grading purposes. For example if the multi-answer
  question was ―List the five largest cities in the US‖ it would count as 5 separate answers for
  grading purposes, and a user could receive anywhere from 1-5 points for the question
  depending on their answer -->
        <div class="score">
            <h1><%=request.getAttribute("quizName")%></h1>
            <%
            String result = (Integer) request.getAttribute("score") + "/" + (Integer) request.getAttribute("maxScore");
            %>
            <p>score: <%=result%></p>

            <p>user took "<%=(Integer) request.getAttribute("timeStamp")%>"minute to complete the quiz</p>
        </div>
    </div>
    <div class="right"></div>
</div>

</body>
</html>