<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="styles/quizzes.css">
    <link rel="stylesheet" href="styles/header.css">
    <style>
        body {
             margin: 0;
             font-family: Arial, sans-serif;
        }

        .register-container {
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

        .register-form label, .register-form input[type="email"], .register-form input[type="text"], .register-form input[type="password"] {
            display: block;
            margin: 10px auto;
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            font-size: 16px;
        }

        .register-form input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s ease-in-out;
        }

        .register-form input[type="submit"]:hover {
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
  <div class="register-container">
    <h1>Register</h1>

    <% if (request.getParameter("error") != null && request.getParameter("error").equals("username_taken")) { %>
        <p style="color: red;">Username is already taken.</p>
    <% } %>

    <% if (request.getParameter("error") != null && request.getParameter("error").equals("email_taken")) { %>
        <p style="color: red;">Email is already registered.</p>
    <% } %>

    <form action="../register" method="post">
        <label for="email">Email(optional):</label>
        <input type="email" id="email" name="email" optional><br>

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <input type="submit" value="Register" style="background-color: #4CAF50;; color: white; border: none; padding: 14px 24px; border-radius: 3px; cursor: pointer;">
    </form>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
    <p>Read about website <a href="about-us.jsp"> About us </a></p>
  </div>
</body>
</html>
