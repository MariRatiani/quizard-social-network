<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="model.DAO.*" %>
<%@ page import="model.objects.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>

<html>
<head>
    <title>Quizard</title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/homepage.css">
    <link rel="stylesheet" href="styles/input-write.css">
    <style>

        .messages-container {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .friend-request-segment, .notes-segment, .challenges-segment{
            display: block;
            background-color: lightblue;
            min-width: 300px;
            max-width: 400px;
            margin; 20px auto;
            padding: 10px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }

        .section-title {
            font-size: 24px;
            margin-bottom: 20px;
            font-weight: bold;
            color: #333;
            background-color: #00BFBF;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
        }

        .top-bar {
            background-color: #428bca;
            display: flex;
            justify-content: center;
            color: white;
            padding 10px 0;
            align-items: center;
            height: 70px;
        }

        .blocks-container {
            max-height: 500px;
            overflow-y: auto;
        }

        .single-block {
            border: 1px solid #ccc;
            background-color: lightyellow;
            padding: 10px;
            margin: 10px;
        }

        .single-block-challenge {
            border: 1px solid #ccc;
            background-color: #ffd763;
            padding: 10px;
            margin: 10px;
        }

        .note-container {
            margin-bottom: 10px;
            padding: 8px;
            border-radius: 5px;
            background-color: white;
            box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
        }

        .note-sender {
            font-weight: bold;
            color: #1877F2;
            margin-right: 5px;
            font-size: 22px;
        }

        .note-content {
            color: #333;
        }

        .note-timeSent {
            font-size: 13px;
            color: #888;
            margin-left: 10px;
        }

        .input-style {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        .button-style {
            background-color: #00B894;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }

        .button-style:hover {
            background-color: #2980b9;
        }

    </style>

</head>
    <%
        ServletContext context = request.getServletContext();
        User user = (User) session.getAttribute("user");
        MessageDAO MessageDAO = (MessageDAO) context.getAttribute("messageDAO");
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
    %>
<body>
<div class="header" style="
    display: flex;
    flex-direction: row;
    align-items: center;
    ">
    <div class="left-section">
        <button class="quizard"> Quizard</button>
    </div>
    <h2><a href = "homepage.jsp"> Homepage </a></h2>
    <div class="middle-section">
        <input type="text" class="input-write">
        <button class="search-button">S</button>
    </div>
    <div class="right-section">
        <h2><a href="profile.jsp?user_id=<%= user.getUser_id() %>"> Profile </a><h2>
    </div>
    <%
        if (user.isAdmin()) {
    %>
    <div class="admin-section">
        <h2><a href="admin-page.jsp"> admin </a></h2>
    </div>
    <% } %>
    <form action="logout" method="get">
        <button type="submit" style="background-color: #428bca; color: white; border: none; padding: 10px 20px; cursor: pointer;">Logout</button>
    </form>
</div>

<!-- messages section, no need to be separated into three parts here though,
    but still can be done, will require additional logic for storing each type seperated,
    messageDao will be used additionally, depends what kind of logic is needed here,
    for example, for messages that are note type, user should be able to respond, this can be done
    in either adding servlet for message, or user could go to profile of sender and there will
    be send message, either way, servlet may be needed, or javascript may work,
    search feature also could be added, without page reload will require javascript here too -->

<div class="top-bar">
    <h2 class="section-title">Your messages</h2>
</div>

<div class="messages-container">

<!-- hyperlinks and thus pages can be eliminated if all messages gets presented here,
    otherwise, either each type of message will need its own page, or all of them can be
    placed in single page, which is more plausible -->
<div class="friend-request-segment">
<h2 class="section-title">Friend requests</h2>
    <% boolean noRequests = false; %>
    <!-- here again, returning String names from method is plausible alternative,
        also list is probably better option here, as from select friend requests could be ordered by
        time sent, and on homepage only the most recent one, or ones could be presented -->
        <div class="blocks-container">
    <%
        Set<Integer> requesterIDs = userDAO.getAllFriendRequests(user.getUser_id());
        if (requesterIDs.size() == 0) noRequests = true;
        for (Integer i : requesterIDs) {
            String requesterName = (userDAO.getUser(i)).getName(); %>
            <div class="single-block">
            <% out.print("Request from " + requesterName); %>
    <!-- profile link of requester should be placed here,
        so profile give an name will display neccessary information of user -->
    <p>See profile: <a href="profile.jsp?user_id=<%= i %>"> <%= requesterName %> </a></p>
    <form action="userMessagesServlet" method="post">
        Not friends
        <input type="hidden" name="id" value="<%=i%> ">
        <input type="hidden" name="messageID" value="0">
        <input type="hidden" name="quizID" value="0">
        <input type="submit" name = "submitButton" value="Accept request" class="button-style">
        <input type="submit" name = "submitButton" value="Decline" class="button-style">
    </form>
    </div>
    <% } %>
    <% if (noRequests) { %>
        <p> No requests. </p>
    <% } %>
    </div>
</div>


<div class="notes-segment">

<h2 class="section-title">Notes</h2>
    <%  boolean noNotes = false; %>
    <!-- here too, displaying all of them is not plausible,
        listing only the latest ones will be better, so order by may be needed in method,
        or getNewMessages in dao will do -->
        <div class="blocks-container">
    <%
        List<Message> notes = userDAO.getNotesForUser(user.getUser_id());
        if (notes.size()==0) noNotes = true;
        for (Message m : notes) {
            String from = (userDAO.getUser(m.getFromId())).getName();
            String timeSent = (m.getTimeSent()).toString();
            String text = m.getMessage(); %>
            <div class="note-container">
            <span class="note-sender"> <%= from %> </span>
            <span class="note-timeSent"> <%= timeSent %> </span> <br>
            <span class="note-content"> <%= text %> </span> <br>
            <p> __ </p>
            <form action="userMessagesServlet" method="post">
                <label for="note">reply:</label>
                <input type="text" class="input-style" id="note" name="note" optional><br>
                <input type="hidden" name="id" value="<%=m.getFromId()%>">
                <input type="hidden" name="messageID" value="<%=m.getMessageId()%>">
                <input type="hidden" name="quizID" value="0">
                <input type="submit" name = "submitButton" value="Send message" class="button-style">
                <input type="submit" name = "submitButton" value="Delete message" class="button-style">
            </form>
            <p>See profile: <a href="profile.jsp?user_id=<%=m.getFromId()%>"> <%= from %> </a></p>
          </div>
      <%  } %>
      <% if (noNotes) { %>
          <p> No notes. </p>
      <% } %>
      </div>
</div>

<div class="challenges-segment">
<h2 class="section-title">Challenges</h2>
    <% boolean noChallenges = false; %>
    <!-- basically is just who challenged, in what quiz, and quiz hyperlink -->
    <div class="blocks-container">
    <%
        List<Message> challenges = userDAO.getChallengesForUser(user.getUser_id());
        if (challenges.size() == 0) noChallenges = true;
        for (Message m : challenges) {
            int fromID = m.getFromId();
            String fromName = (userDAO.getUser(m.getFromId())).getName();
            String timeSent = (m.getTimeSent()).toString();
            int quiz_id = Integer.parseInt(m.getMessage());
            String quizName = (quizDAO.getQuiz(quiz_id)).getName();
    %>
    <div class="single-block-challenge">
    <span class="note-sender"> <%= fromName %> </span>
    <span class="note-timeSent"> <%= timeSent %> </span> <br>
    challenged you to do quiz: <p><a href="quiz-taking?quiz_id=<%= quiz_id %>"> <%= quizName %> </a></p>
    <form action="userMessagesServlet" method="post">
        <input type="hidden" name="id" value="<%=m.getFromId()%>">
        <input type="hidden" name="messageID" value="<%=m.getMessageId()%>">
        <input type="hidden" name="quizID" value="<%= quiz_id %>">
        <input type="submit" name = "submitButton" value="Accept challenge" class="button-style">
        <input type="submit" name = "submitButton" value="Delete message" class="button-style">
    </form>
    <p>See profile: <a href="profile?user_id=<%=fromID%>"> <%= fromName %> </a></p>
    </div>
    <% } %>
    <% if (noChallenges) { %>
        <p> No challenges. </p>
    <% } %>
    </div>
</div>
</div>
<!-- here ends messages section -->

</body>
</html>