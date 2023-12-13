<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ page import="model.DAO.*" %>
<%@ page import="model.objects.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>

<!-- user is really admin here -->

<html>
<head>
    <title>Quizard</title>
    <link rel="stylesheet" href="styles/header.css">
    <link rel="stylesheet" href="styles/homepage.css">
    <link rel="stylesheet" href="styles/input-write.css">
    <style>

        .window-container {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .admin-or-remove-container, .add-announcement-container, .add-category-container{
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

        .user-info-block {
            border: 1px solid #ccc;
            background-color: lightyellow;
            padding: 10px;
            margin: 10px;
        }

        .table-container {
            max-height: 300px;
            overflow-y: auto;
        }

        .user-info-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 16px;
        }

        .user-info-table th, .user-info-table td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
        }

        .user-info-table th {
            background-color: #f2f2f2;
        }

        .user-info-table td {
            background-color: lightyellow;
        }

        .user-info-table thead {
            position: sticky;
            top: 0;
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
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .button-style:hover {
            background-color: #2980b9;
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

        .top-bar {
            background-color: #428bca;
            display: flex;
            justify-content: center;
            color: white;
            padding 10px 0;
            align-items: center;
            height: 70px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

    </style>
</head>
<%
    ServletContext context = request.getServletContext();
    User user = (User) session.getAttribute("user");
    AnnouncementDAO announcementDAO = (AnnouncementDAO) context.getAttribute("announcementDAO");
    QuizDAO quizDAO = (QuizDAO) context.getAttribute("quizDAO");
    UserDAO userDAO = (UserDAO) context.getAttribute("userDAO");
    QuizCategoryDAO categoryDAO = (QuizCategoryDAO) context.getAttribute("categoryDAO");
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
    <div class="admin-section">
        <h2><a href="admin-page.jsp"> admin </a></h2>
    </div>
    <form action="logout" method="get">
        <button type="submit" style="background-color: #428bca; color: white; border: none; padding: 10px 20px; cursor: pointer;">Logout</button>
    </form>
</div>

    <div class="top-bar">
        <h2 class="section-title">Admin features</h2>
    </div>

    <!-- from now on admin features logic -->
    <!-- probably some kind of a window container will be better -->
    <div class="window-container">

        <!-- so admin can create announcement, make users admin or remove them, add or delete categories,
            so basically, three segments are needed here, each for each type of action, all of them
            will be contained in window container, so to make them symmetrically -->
        <% if (request.getParameter("error") != null) { %>
                <p style="color: red;">Could not execute!</p>
        <% } %>
        <% if (request.getParameter("success") != null) { %>
                <p style="color: green;">Succeeded!</p>
        <% } %>
        <div class="add-announcement-container">
        <h2 class="section-title">Announcements</h2>
        To add fill below two and click add: <br>
            <form action="adminOrRemove" method="post">
                <label for="title">Title:</label>
                <input type="text" class="input-style" id="title" name="title" optional><br>
                <label for="description">Description:</label>
                <input type="text" class="input-style" id="description" name="description" optional><br>
                To remove enter id and click remove: <br>
                <label for="toremove">Remove:</label>
                <input type="text" class="input-style" id="toremove" name="toremove" optional><br>
                <input type="submit" name = "submitButton" value="remove announcement" class="button-style">
                <input type="submit" name = "submitButton" value="add announcement" class="button-style">
            </form>
            <div class="table-container">
            <table class="user-info-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>By</th>
                </tr>
            </thead>
            <tbody>
            <%
            List<Announcement> announcements = announcementDAO.getAnnouncements();
            for (Announcement a : announcements) {
                String title = a.getTitle();
                String description = a.getDescription();
                int creatorID = a.getUserId();
                String name = userDAO.getUser(creatorID).getName();
                String timePosted = (a.getTime_posted()).toString(); %>
                <tr>
                    <td><%= a.getAnnouncementId() %></td>
                    <td><%= title %></td>
                    <td><%= description %></td>
                    <td><%= name %></td>
                </tr>
            <% } %>
            </tbody>
            </table>
            </div>
            </div>

        <div class="add-category-container">
        <h2 class="section-title">Categories</h2>
            <form action="adminOrRemove" method="post">
                <label for="categoryname">Category name:</label>
                <input type="text" class="input-style" id="categoryname" name="categoryname" required><br>
                <input type="submit" name = "submitButton" value="add category" class="button-style">
                <input type="submit" name = "submitButton" value="delete category" class="button-style">
            </form>
            <div class="table-container">
            <table class="user-info-table">
            <thead>
                <tr>
                    <th>Category ID</th>
                    <th>Category name</th>
                </tr>
            </thead>
            <tbody>
        <%
            List<QuizCategory> categories = categoryDAO.getAllQuizCategories();
            for (QuizCategory c : categories) { %>
                <tr>
                    <td><%= c.getCategoryId() %></td>
                    <td><%= c.getCategoryName() %></td>
                </tr>
        <% } %>
        </tbody>
        </table>
        </div>
        </div>
        <div class="admin-or-remove-container">
        <h2 class="section-title">Users</h2>
            <form action="adminOrRemove" method="post">
                <label for="username">Username:</label>
                <input type="text" class="input-style" id="username" name="username" required><br>
                <input type="submit" name = "submitButton" value="make admin" class="button-style">
                <input type="submit" name = "submitButton" value="delete user" class="button-style">
            </form>
            <div class="table-container">
            <table class="user-info-table">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Is admin</th>
                    <th>Profile</th>
                </tr>
            </thead>
            <tbody>
        <%
            List<User> users = userDAO.getAllUsers();
            for (User u : users) {
                String isAdmin;
                if (u.isAdmin()) isAdmin = "Yes";
                else isAdmin = "No"; %>
                <tr>
                    <td><%= u.getUser_id() %></td>
                    <td><%= u.getName()%></td>
                    <td><%= isAdmin %></td>
                    <td><a href="profile.jsp?user_id=<%= u.getUser_id() %>"> <%= u.getName() %> </a></td>
                </tr>
        <% } %>
        </tbody>
        </table>
        </div>
        </div>
    </div>

</body>
</html>