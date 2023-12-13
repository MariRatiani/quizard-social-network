<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="styles/quizzes.css">
    <link rel="stylesheet" href="styles/header.css">
    <style>
            body {
                 margin: 0;
                 font-family: Arial, sans-serif;
            }

            .login-container {
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

            .login-form label, .login-form input[type="text"], .login-form input[type="password"] {
                display: block;
                margin: 10px auto;
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 3px;
                font-size: 16px;
            }

            .login-form input[type="submit"] {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 3px;
                cursor: pointer;
                transition: background-color 0.3s ease-in-out;
            }

            .login-form input[type="submit"]:hover {
                background-color: #45a049;
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
    <div class="middle-section">
        <button class="quizzes-button">quizzes</button>

    </div>
</div>

<div class = "login-container">
    <h1>Login</h1>

    <% if (request.getParameter("error") != null) { %>
            <p style="color: red;">Username or password incorrect!</p>
    <% } %>

    <% if (request.getParameter("congratulations") != null) { %>
            <p style="color: green;">Congratulations <%= request.getParameter("congratulations")%>, you are quizard now! </p>
    <% } %>

    <form action="../login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <input type="submit" value="Login" style="background-color: #4CAF50;; color: white; border: none; padding: 14px 24px; border-radius: 3px; cursor: pointer;">
    </form>
    <p>No account? <a href="register.jsp">Register now</a></p>
    <p>Read about website <a href="about-us.jsp"> About us </a></p>
    </div>
</body>
</html>
