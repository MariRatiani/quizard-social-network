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

        body {
            margin: 0;
            font-family: Arial, sane-serif;
        }

        .page-wrapper {
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        .left-fixed {
            position: fixed;
            top: 0;
            left: 0;
            width: 25%;
            height: 100vh;
            background-color: #00B894;
            color: white;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-top: 75px;
        }

        .centered-text {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
            max-width: 80%;
            font-family: Arial, sans-serif;
            font-size: 24px;
            color: white;
        }

        .right-flexible {
            position: absolute;
            top: 0;
            left: 25%;
            width: 71%;
            height: 100vh;
            padding: 20px;
            background-color: #f2f2f2;
            background: 1px solid #ccc;
            color: #333;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-top: 75px;
        }

        .top-bar {
            background-color: #428bca;
            display: flex;
            justify-content: center;
            color: white;
            padding 10px 0;
            align-items: center;
            height: 70px;
            position: absolute;
            top: 20px;
            left: 20px;
            right: 20px;
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

        .section-title-for-fixed {
            font-size: 18px;
            margin-bottom: 20px;
            color: #333;
            background-color: #f2f2f2;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            max-width: 80%;
        }

        .announcements-container {
            display: block;
            background-color: lightblue;
            max-height: calc(100vh - 20px);
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-sizing: border-box;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
            position: absolute;
            margin-top: 70px;
            width: 95%;
        }

        .blocks-container {
            margin-top: 3px;
            max-height: 500px;
            overflow-y: auto;
        }

        .announcement-block {
            margin-bottom: 20px;
            padding: 15px;
            background-color: lightyellow;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .announcement-title {
            font-size: 24px;
            margin: 0;
            color: #333;
        }

        .announcement-content {
            font-size: 18px;
            margin: 10px 0;
            color: #333;
        }

        .announcement-author {
            font-weight: bold;
            color: #1877F2;
            margin-right: 5px;
            font-size: 22px;
        }

        .announcement-time-posted {
            font-size: 13px;
            color: #888;
            margin-left: 10px;
        }

        .announcement-block:hover {
            background-color: #e0e0e0;
            transition: background-color 0.3s ease;
        }

    </style>
</head>
    <%
        ServletContext context = request.getServletContext();
        User user = (User) session.getAttribute("user");
        AnnouncementDAO announcementDAO = (AnnouncementDAO) context.getAttribute("announcementDAO");
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

    <div class="page-wrapper">
        <div class="left-fixed">
            <h2 class="section-title-for-fixed">Announcements</h2>
            <h2 class="section-title-for-fixed">
                On Quizard admins of this website try to make page more and more
                entertaining and interesting, thus they add some new features time to time,
                all the news will be posted as announcements and will appear for every user.
            </h2>
            <h2 class="section-title-for-fixed">On the right all the announcements are presented</h2>
        </div>
        <div class="right-flexible">
            <div class="top-bar">
                <h2 class="section-title">Announcements</h2>
            </div>

            <div class="announcements-container">
                <div class="blocks-container">
                    <%
                    List<Announcement> announcements = announcementDAO.getAnnouncements();
                    for (Announcement a : announcements) {
                    %>
                    <!-- let each announcement have its own block -->
                    <div class="announcement-block">
                        <%
                            String title = a.getTitle();
                            String description = a.getDescription();
                            int creatorID = a.getUserId();
                            String name = userDAO.getUser(creatorID).getName();
                            String timePosted = (a.getTime_posted()).toString();
                        %>
                        <span class="announcement-author"> <%= name %> </span>
                        <span class="announcement-time-posted"> <%= timePosted %> </span> <br>
                        <p>See profile: <a href="profile.jsp?user_id=<%=creatorID%>"> <%= name %> </a></p>
                        <h2 class="announcement-title"> <%= title %> </h2>
                        <p class="announcement-description"> <%= description %> </p>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

</body>
</html>