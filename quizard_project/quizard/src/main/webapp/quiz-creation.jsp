<%@ page import="model.objects.User" %>
<!DOCTYPE html>

<html class="html">
<head>
    <title>create Quiz</title>
    <link rel="stylesheet" href="styles/quiz.css">
    <link rel="stylesheet" href="styles/header.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
</head>
<body class="body" style="padding-bottom: 1000px;">
<!-- HEADER PART, SHOULD BE COPIED IN EVERY PAGE -->
<%
    HttpSession httpSession = (HttpSession) request.getSession();
    User user = (User) httpSession.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
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
<div class="display">
    <div class="left">left</div>
    <div class="create-quiz">
        <h1 class="Quiz-in-title">create Quiz</h1>
        <form action="/createQuiz" name="creationForm" method="POST"
              onsubmit="return validateQuizCreateForm()"
              style="    display: flex;
        flex-direction: column">
            <div class="form-without-button" style="display: flex;
          flex-direction: column;">
                <div class="title-div" style="display: flex;
            flex-direction: row;
            align-items: center;">
                    <label class="title">Title:</label>
                    <input class="input-write" type="text" name="title">
                </div>

                <div class="description-div">
                    <label>Description: </label>
                    <input class="input-write" type="text" name="description">
                </div>

                <div class="category-div">
                    <label class="category-name">Category</label>
                    <input class="input-write" type="text" name="category">
                </div>

                <div class="time-div">
                    <label>Time Limit(seconds): </label>
                    <input type="number" name="timeLimit" value="0">
                </div>

                <div class="questions-one-page-div">
                    <label> Questions appear on one page</label>
                    <input class="checkbox1" type="checkbox" name="QuestionsOnOnePage" value="true" required
                           style="float: right;">
                </div>

                <div class="order-div">
                    <label>Random order of questions</label>
                    <input type="checkbox" name="randomOrder" value="true">
                </div>

                <div class="immediateCorrection-div">
                    <label>correct immediatly</label>
                    <input type="checkbox" name="immediateCorrection" value="true">
                </div>

                <div class="practice-mode-div">
                    <label>can be taken in practice mode</label>
                    <input type="checkbox" name="practiceMode" value="true">
                </div>
            </div>

            <div class="button-div">
                <button class="button" type="submit">Add Quiz</button>
            </div>
        </form>

    </div>
    <div class="right">right
    </div>
</div>
</body>
</html>