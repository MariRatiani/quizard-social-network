<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title> About us </title>
    <link rel="stylesheet" href="styles/quizzes.css">
    <link rel="stylesheet" href="styles/header.css">
    <style>
        body {
             margin: 0;
             font-family: Arial, sans-serif;
        }

        .text-container {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            height: calc(100vh-40px);
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            background-color: white;
            width: 450px;
            margin: 100px auto;
            font-size: 18px;
            position: relative;
        }
    </style>
</head>
<body>
<div class="header" style="
  display: flex;
  flex-direction: row;
  align-items: center;
  ">
    <div class="left-section">
        <button class="quizard"> Quizard</button>
    </div>
</div>
<div class = "text-container">
    <h2> Welcome </h2>
    Quizard is quiz website, where anyone can create account for free and become user on this website,
    users create quizzes, take them, they can also become friends with each other, send messages and challenge
    others, users can reach achievements and any user can also become admin, but admins are just like
    fellow users too.
    This page was intended to be educative and entertaining for anyone, so hurry up and register
    <p> The time you become quizard is Now! </p>
    <p><a href="register.jsp">Register here</a></p>
    <p> Already have an account? <a href="login.jsp">Login</a></p>
</div>
</body>
</html>