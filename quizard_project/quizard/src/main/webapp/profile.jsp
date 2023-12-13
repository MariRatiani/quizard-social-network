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

        .top-bar {
            background-color: #428bca;
            display: flex;
            color: white;
            padding 10px 0;
            align-items: center;
            height: 70px;
        }

        .section-title {
            font-size: 24px;
            margin-bottom: 20px;
            font-weight: bold;
            color: #333;
            background-color: #f2f2f2;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
        }

        .button-style, .button-style-send {
            background-color: #A62745;
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .button-style-send {
            background-color: #3498db;
        }

        .button-style:hover, button-style-send:hover {
            background-color: #2980b9;
        }

        .user-info-container {
            display: block;
            background-color: lightblue;
            width: 50%;
            margin; 20px auto;
            padding: 10px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }

        .user-info {
            margin-bottom: 10px;
            padding: 8px;
            border-radius: 5px;
            background-color: #86B5D1;
            box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
            width: 95%;
        }

        .one-info {
            margin: 10px;
            border: 1px solid #ddd;
            padding: 10px;
            width: 90%;
            text-align: center;
            background-color: lightblue;
        }

        .info-label {
            border: 1px solid #ddd;
            font-weight: bold;
            background-color: #86B5D1;
        }

        .info-value {
            margin-top: 5px;
            border: 1px solid #ddd;
            background-color: #99E8B8;
        }

        .additional-container, .profile-container {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .friend-list-segment, .achievements-segment, .quizzes-segment, .contact-container {
            display: block;
            background-color: #00B894;
            min-width: 300px;
            max-width: 400px;
            min-height: 400px;
            margin; 20px auto;
            padding: 10px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }

        .contact-container {
            width: 44%;
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

        .input-style {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

    </style>

</head>
    <%
        ServletContext context = request.getServletContext();
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
        User user = (User) session.getAttribute("user");
        AnnouncementDAO announcementDAO = (AnnouncementDAO) context.getAttribute("announcementDAO");
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
        User profileOwner = userDAO.getUser(Integer.parseInt(request.getParameter("user_id")));
        boolean areFriends = (userDAO.getFriends(profileOwner.getUser_id())).contains(user.getUser_id());
        boolean sentRequest = (userDAO.getAllFriendRequests(profileOwner.getUser_id())).contains(user.getUser_id());
        boolean gotRequest = (userDAO.getAllFriendRequests(user.getUser_id())).contains(profileOwner.getUser_id());
        // user.isAdmin = true;
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
<% if (user.getUser_id() != profileOwner.getUser_id()) { %>

    <!-- information of owner -->

    <div class="top-bar">
        <h2 class="section-title"> Profile owner <%= profileOwner.getName()%> </h2>
    </div>
    <div class="profile-container">
    <div class="user-info-container">
        <h2 class="section-title"> User information </h2>
        <div class="user-info">
            <div class="one-info">
                <div class="info-label"> Username: </div>
                <div class="info-value"> <%=profileOwner.getName() %> </div>
            </div>
            <% if (areFriends) { %>
            <div class="one-info">
                <div class="info-label"> Email: </div>
                <% if (profileOwner.getEmail().equals("")) { %>
                    <div class="info-value"> Not specified </div>
                <% } else { %>
                    <div class="info-value"> <%=profileOwner.getEmail()%> </div>
                <% } %>
            </div>
            <% } %>
            <div class="one-info">
                <div class="info-label"> Admin status: </div>
                <% if (profileOwner.isAdmin()) { %>
                    <div class="info-value"> User is admin on this page </div>
                <% } else { %>
                    <div class="info-value"> Not admin </div>
                <% } %>
            </div>
        </div>
    </div>
    <div class="contact-container">
        <%
            if (areFriends) {
        %>
            <h2 class="section-title"> Are friends </h2>
            <form action="profileServlet" method="post">
                <label for="note"><div class="info-label"> Send note: </div></label>
                <input type="text" class="input-style" id="note" name="note" optional><br>
                <input type="hidden" name="id" value="<%=profileOwner.getUser_id()%>">
                <input type="submit" name = "submitButton" value="Send message" class="button-style-send">
                <input type="submit" name = "submitButton" value="Remove friend" class="button-style">
            </form>
        <% } else { %>
            <% if (gotRequest) { %>
                <form action="profileServlet" method="post">
                    <h2 class="section-title"> Not friends, got request </h2>
                    <input type="hidden" name="id" value="<%=profileOwner.getUser_id()%> ">
                    <input type="submit" name = "submitButton" value="Accept request" class="button-style-send">
                </form>
            <% } else { %>
                    <% if (sentRequest == false) { %>
                        <form action="profileServlet" method="post">
                            <h2 class="section-title"> Not friends </h2>
                            <input type="hidden" name="id" value="<%=profileOwner.getUser_id()%> ">
                            <input type="submit" name = "submitButton" value="Send friend request" class="button-style-send">
                        </form>
                    <% } else { %>
                            <h2 class="section-title"> Not friends, request sent </h2>
                       <% } %>
              <% } %>
        <% } %>
    </div>
    </div>
    <div class="additional-container">
        <div class="achievements-segment">
            <h2 class="section-title">User achievements</h2>
            <div class="blocks-container">
            <%
                 List<String> achievements = userDAO.getAllAchievments(profileOwner.getUser_id());
                 for (String s : achievements) { %>
                     <div class="single-block">
                        <h2 class="section-title"><%= s %></h2>
                     </div>
            <% } %>
             </div>
        </div>
        <% if (areFriends) { %>
        <div class="friend-list-segment">
            <h2 class="section-title">Friends</h2>
            <div class="blocks-container">
            <%
                 Set<Integer> friends = userDAO.getFriends(profileOwner.getUser_id());
                 for (int i : friends) { %>
                 <div class="single-block">
                 <p> Username: <%= userDAO.getUser(i).getName() %> </p>
                 <p>See profile <a href="profile.jsp?user_id=<%= i %>"> <%= userDAO.getUser(i).getName() %></a> <p>
                 </div>
             <% } %>
             </div>
        </div>
        <% } else { %>
            <div class="friend-list-segment">
                <h2 class="section-title">Friends</h2>
                <h2 class="section-title">List available for friends only</h2>
            </div>
        <% } %>

        <div class="quizzes-segment">
            <h2 class="section-title">Quizzes <%=  profileOwner.getName() %> created</h2>
            <div class="blocks-container">
            <%
                 List<Quiz> quizzes = quizDAO.getCreatorQuizzes(profileOwner.getUser_id());
                 for (Quiz q : quizzes) { %>
                 <div class="single-block">
                 <p>Quiz name: <a href="quiz-taking.jsp?quiz_id=<%= q.getQuizId() %>"> <%= q.getName() %></a> </p>
                 <form action="profileServlet" method="post">
                     <input type="hidden" name="id" value="<%=profileOwner.getUser_id()%> ">
                     <input type="hidden" name="quizID" value="<%=q.getQuizId()%> ">
                     <input type="submit" name = "submitButton" value="Remove quiz" class="button-style" >
                 </form>
                 </div>
             <% } %>
             </div>
        </div>
    </div>
    <% } else { %>
        <div class="own-profile">

            <div class="top-bar">
                <h2 class="section-title"> This is your profile <%= profileOwner.getName()%> </h2>
                <form action="profileServlet" method="post">
                    <input type="hidden" name="id" value="<%=profileOwner.getUser_id()%> ">
                    <input type="submit" name = "submitButton" value="Delete account" class="button-style">
                </form>
            </div>

            <div class="user-info-container">
                <h2 class="section-title"> Your information </h2>
                <div class="user-info">
                    <div class="one-info">
                        <div class="info-label"> Your username: </div>
                        <div class="info-value"> <%=profileOwner.getName() %> </div>
                    </div>
                    <div class="one-info">
                        <div class="info-label"> Email: </div>
                        <% if (profileOwner.getEmail().equals("")) { %>
                            <div class="info-value"> Not specified </div>
                        <% } else { %>
                            <div class="info-value"> <%=profileOwner.getEmail()%> </div>
                        <% } %>
                    </div>
                    <div class="one-info">
                        <div class="info-label"> Admin status: </div>
                        <% if (profileOwner.isAdmin()) { %>
                            <div class="info-value"> You are admin on this page </div>
                        <% } else { %>
                            <div class="info-value"> Not admin </div>
                        <% } %>
                    </div>
                </div>
            </div>

            <div class="additional-container">
                <div class="achievements-segment">
                    <h2 class="section-title">Your achievements</h2>
                    <div class="blocks-container">
                    <%
                         List<String> achievements = userDAO.getAllAchievments(profileOwner.getUser_id());
                         for (String s : achievements) { %>
                             <div class="single-block">
                                <h2 class="section-title"><%= s %></h2>
                             </div>
                    <% } %>
                     </div>
                </div>

                <div class="friend-list-segment">
                    <h2 class="section-title">Your friends</h2>
                    <div class="blocks-container">
                    <%
                         Set<Integer> friends = userDAO.getFriends(profileOwner.getUser_id());
                         for (int i : friends) { %>
                         <div class="single-block">
                         <p> Username: <%= userDAO.getUser(i).getName() %> </p>
                         <p>See profile <a href="profile.jsp?user_id=<%= i %>"> <%= userDAO.getUser(i).getName() %></a> <p>
                         </div>
                     <% } %>
                     </div>
                </div>

                <div class="quizzes-segment">
                    <h2 class="section-title">Quizzes you created</h2>
                    <div class="blocks-container">
                    <%
                         List<Quiz> quizzes = quizDAO.getCreatorQuizzes(profileOwner.getUser_id());
                         for (Quiz q : quizzes) { %>
                         <div class="single-block">
                         <p>Quiz name: <a href="quiz-taking.jsp?quiz_id=<%= q.getQuizId() %>"> <%= q.getName() %></a> </p>
                         <form action="profileServlet" method="post">
                             <input type="hidden" name="id" value="<%=profileOwner.getUser_id()%> ">
                             <input type="hidden" name="quizID" value="<%=q.getQuizId()%> ">
                             <input type="submit" name = "submitButton" value="Remove quiz" class="button-style" >
                         </form>
                         </div>
                     <% } %>
                     </div>
                </div>
            </div>
        </div>
    <% } %>
</body>
</html>