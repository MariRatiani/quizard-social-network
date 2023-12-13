<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>
        <!-- take from request, what list is presented -->
        Top Performances
    </title>
    <link rel="stylesheet" href="styles/quizzes.css">
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
    <div class="right-section" >
        <button class="messages-button">
            <img  class="image-message" src="images/message-2.png">
        </button>
        <button class="User-button">U</button>
    </div>
</div>

<!-- until here -->

<div class="display">

    <div class="left">

    </div>

    <div class ="main-part" style="
      display: flex;
      flex-direction: column;
      width: 100%;
      align-items: center;
    ">

        <div class="search-bar">
            <input type="text" id="searchInput" placeholder="Search...">
            <button id="searchButton" class="button">Search</button>
            <!-- amas rom daachers mere  -->
            <button class="button">reload</button>
        </div>

        <div class="list" id="list" style="
        display: flex;
        flex-direction: column;
        width: 100%;
      ">
            <!-- AQ 10 LISTI FOR-IT RO WAMOIGO -->
            <button class="quiz_info" id="quiz_info" style="display: none;">
                to display quiz info
            </button>
        </div>

        <div id="display_quizzes" class="display_quizzes" >
            <button id="one_quiz" class="one_quiz" style="display: none;">
                QuizInfo
                vaaaaax
            </button>
        </div>

    </div>


    <div class="third-section">

    </div>
</div>
</body>

<script src="./script/quizzes.js"></script>

</html>