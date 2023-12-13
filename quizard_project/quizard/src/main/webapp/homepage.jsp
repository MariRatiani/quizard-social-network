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

</head>
    <%
        ServletContext context = request.getServletContext();
        User user = (User) session.getAttribute("user");
        AnnouncementDAO announcementDAO = (AnnouncementDAO) context.getAttribute("announcementDAO");
        QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
        UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
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
        <button class="create-button" onclick="window.location.href = 'profile.jsp?user_id=<%= user.getUser_id() %>';">Profile</button>
    </div>
    <!-- here somewhere in the bar should be a button or hyper link to admin page
         but only if user is admin, user session attribute is already assigned at this point -->
    <%
        if (user.isAdmin()) {
    %>
    <div class="admin-section">
        <button class="create-button" onclick="window.location.href = 'admin-page.jsp';">admin</button>
    </div>
    <% } %>
    <form action="logout" method="get">
        <button type="submit" style="background-color: #428bca; color: white; border: none; padding: 10px 20px; cursor: pointer;">Logout</button>
    </form>

</div>

<!-- data fetching from now on, will need css for better visual -->

    <!-- newly created quizzes section -->
    <div class:"newly-quizzes-container">

        <h2> Check new quizzes </h2><br>
        <!-- daos are present at this point
            single quiz does not need its own segment, could be just hyperlink -->
        <%
            List<Quiz> newQuizzes = quizDAO.getRecentlyCreatedQuizzes();
            for (Quiz q : newQuizzes) {
                String quizName = q.getName();
                int quizID = q.getQuizId();
        %>
        <!-- got quiz q fields here -->
        <h2><a href="quizTaking?quiz_id=<%= quizID %>"> quizName </a></h2>
        <% } %>
        <!-- so css is needed for container -->
    </div>
    <!-- here ends new quizzes section -->

    <!-- popular quizzes section -->
    <div class:"popular-quizzes-container">
        <h2> most popular quizzes </h2><br>
        <!-- same as above -->
        <%
            List<Quiz> popularQuizzes = quizDAO.getPopularQuizzes();
            for (Quiz q : popularQuizzes) {
                String quizName = q.getName();
                int quizID = q.getQuizId();
        %>
        <h2><a href="quizTaking?quiz_id=<%= quizID %>"> quizName </a></h2>
        <% } %>
        <!-- css is needed for container -->
    </div>
    <!-- here ends popular quizzes section -->

    <!-- achievements section -->
    <div class:"achievements-container">
        <!-- list of achievements as strings probably is enough here,
            assuming not too many achievements will be added on website -->
        <%
            List<String> achievements = userDAO.getAllAchievments(user.getUser_id());
            for (String s : achievements) {
            out.write(s+" ");
            }
        %>
        <!-- some styles for container needed->
    </div>
    <!-- here ends achievements section -->

    <!-- above segments probably should be placed somewhere at the middle of the window -->

    <!-- following sections can be distributed to use window space elegantly -->
    <!-- announcements will have its section -->
    <!-- also hyperlink to whole announcements page will be presented -->
    <h2><a href="announcements.jsp"> Announcements </a></h2>
    <div class:"announcements-section">
        <!-- consider dao is present already -->
        <%
        List<Announcement> announcements = announcementDAO.getNewAnnouncements();
        for (Announcement a : announcements) {
        %>
        <!-- let each announcement have its own block ->
        <div class="single-announcement">
            <!-- get announcement info, either here is needed userDAO to get creator name
            or announcements should have name instead of id, still name may not be neccessary (but dao will) -->
            <%
                String title = a.getTitle();
                String description = a.getDescription();
                int creatorID = a.getUserId();
                String name = userDAO.getUser(creatorID).getName();
                String timePosted = (a.getTime_posted()).toString();
                out.print("Title: "+ title+". Description: " + description + ". Announced by: " + name+ "\n");
            %>
            <!-- css styles needed to properly display above information in some block
                 here
                 goes
                 styles
            -->
        </div>
        <!-- here ends single announcement block -->
        <% } %>
    </div>
    <!-- here ends announcements -->

    <!-- messages section, could be separated into three parts: friend request, regular message, challenge -->
    <h2><a href="user-messages.jsp?user_id=<%= user.getUser_id() %>"> See all messages here </a></h2>
    <div class="messages-container">

        <!-- hyperlinks and thus pages can be eliminated if all messages gets presented here,
            otherwise, either each type of message will need its own page, or all of them can be
            placed in single page, which is more plausible -->
        <div class="friend-request-segment">

            <!-- here again, returning String names from method is plausible alternative,
                also list is probably better option here, as from select friend requests could be ordered by
                time sent, and on homepage only the most recent one, or ones could be presented -->
            <%
                Set<Integer> requesterIDs = userDAO.getAllFriendRequests(user.getUser_id());
                for (Integer i : requesterIDs) {
                    String requesterName = (userDAO.getUser(i)).getName();
                    out.print("request from " + requesterName);
            %>
            <!-- profile link of requester should be placed here,
                so profile give an name will display neccessary information of user -->
            <h2><a href="profile.jsp?user_id=<%= i %>"> see profile of <%= requesterName %> </a></h2> <br>
            <form action="profileServlet" method="post">
                Not friends
                <input type="hidden" name="id" value="<%=i%> ">
                <input type="submit" name = "submitButton" value="Accept request" >
            </form>
            <% } %>
        </div>

        <div class="notes-segment">

            <!-- here too, displaying all of them is not plausible,
                listing only the latest ones will be better, so order by may be needed in method,
                or getNewMessages in dao will do -->
            <%
                List<Message> notes = userDAO.getNotesForUser(user.getUser_id());
                for (Message m : notes) {
                    String from = (userDAO.getUser(m.getFromId())).getName();
                    String timeSent = (m.getTimeSent()).toString();
                    String text = m.getMessage();
                    out.print("from: " + from + " time: " +timeSent+ " note: " + text+"\n");
                    %>
                    <h2><a href="profile.jsp?user_id=<%=m.getFromId()%>"> From: <%= from %> </a><h2>
               <% } %>

        </div>

        <div class="challenges-segment">
            <!-- basically is just who challenged, in what quiz, and quiz hyperlink -->
            <%
                List<Message> challenges = userDAO.getChallengesForUser(user.getUser_id());
                for (Message m : challenges) {
                    int fromID = m.getFromId();
                    String fromName = (userDAO.getUser(m.getFromId())).getName();
                    String timeSent = (m.getTimeSent()).toString();
                    int quiz_id = Integer.parseInt(m.getMessage());
                    String quizName = (quizDAO.getQuiz(quiz_id)).getName();
            %>
            <h2><a href="profile.jsp?user_id=<%= fromID %>"> fromName </a><h2>
            challenged you to do quiz: <h2><a href="quizTaking?quiz_id=<%=quiz_id%>"> quizName </a><h2> <br>
            <% } %>
        </div>
    </div>
    <!-- here ends messages section -->

    <!-- below ones need small eegments just for hyperlinks to quizlist page tu show quizes
        and parameter will be added accordingly -->
    <!-- link-container will be same for all three, just segment to hold one hyperlink -->

    <div class="link-container">
        <h2><a href="quizzes.jsp?showlist=usercreated"> Quizzes you created </a></h2>
    </div>
    <div class="link-container">
        <h2><a href="quizzes.jsp?showlist=usertook"> Quizzes you took </a></h2>
    </div>
    <div class="link-container">
        <h2><a href="quizzes.jsp?showlist=friendstook"> See your friends activites </a></h2>
    </div>

    <!-- whole window container may also be needed -->

</body>
</html>