<%@ page import="model.DAO.UserDAO" %>
<%@ page import="model.DAO.QuizDAO" %>
<%@ page import="model.objects.Quiz" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <link rel="stylesheet" href="styles/header.css">
    <style>
        /* Reset default margin and padding for all elements */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Styles for the header */
        .header {
            display: flex;
            flex-direction: row;
            align-items: center;
            background-color: #3498db;
            color: white;
            padding: 10px 20px;
        }

        .quizard {
            background-color: transparent;
            border: none;
            font-size: 24px;
            color: white;
            cursor: pointer;
        }

        .middle-section button {
            background-color: transparent;
            border: none;
            font-size: 18px;
            color: white;
            cursor: pointer;
            margin-right: 20px;
        }

        .middle-section button:last-child {
            margin-right: 0;
        }

        /* Styles for the quiz list */
        .display {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f0f0;
        }

        .left-section2 {
            display: flex;
            flex-direction: column;
            width: 100%;
            align-items: center;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            overflow-y: scroll; /* Add scrollbar for overflow content */
            max-height: 80vh; /* Set maximum height for the scrollbar */
        }

        .list {
            display: flex;
            flex-direction: column;
            width: 100%;
            padding: 20px;
        }

        .one-performance {
            background-color: #f5f5f5;
            border: none;
            padding: 10px;
            margin-bottom: 10px;
            text-align: left;
            width: 100%;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s ease-in-out;
        }

        .one-performance:hover {
            background-color: #e0e0e0;
        }

        /* Hide scrollbar but allow scrolling */
        .left-section2::-webkit-scrollbar {
            width: 0.5em;
        }

        .left-section2::-webkit-scrollbar-thumb {
            background-color: darkgrey;
        }

        /* Show scrollbar on hover */
        .left-section2:hover::-webkit-scrollbar-thumb {
            background-color: grey;
        }
    </style>
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
    ServletContext context = (ServletContext) request.getServletContext();
    UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
    QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
    List<Quiz> quizList = quizDAO.getAllQuizzes();

%>

<div class="display">
    <!-- <div class="left-section2"></div> -->
    <div class="left-section2" style="
          display: flex;
          flex-direction: column;
          width: 100%;
          align-items: center;
        ">

        <div class="list" style="
            display: flex;
            flex-direction: column;
            width: 100%;
          ">
            <!-- AQ 10 LISTI FOR-IT RO WAMOIGO -->
            <% for (Quiz quiz : quizList) {
                String quizName = quiz.getName();
                String category = quiz.getCategory();
                int quizId = quiz.getQuizId();
            %>
            <form method="post" action="/quiz-summary.jsp">
                <input type="hidden" name="quizId" value="<%=quizId%>">
                <button type="submit" class="one-performance">
                    <ul>
                        <li><%=quizName%>
                        </li>
                        <li><%=category%>
                        </li>
                    </ul>
                </button>
            </form>
            <%}%>
        </div>
    </div>
</div>

</body>

</html>