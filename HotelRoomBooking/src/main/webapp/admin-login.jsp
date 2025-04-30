<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #e0f2f1, #ffffff);
            color: #333;
            min-height: 100vh;
        }

        header {
            background-color: #0077b6;
            color: white;
            padding: 20px 0;
            text-align: center;
            font-size: 24px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .form-container {
            width: 100%;
            max-width: 400px;
            margin: 50px auto;
            padding: 40px 30px;
            background-color: white;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            border-radius: 16px;
        }

        .form-container input[type="email"],
        .form-container input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            font-size: 15px;
            border: 1px solid #ccd6dd;
            border-radius: 8px;
            transition: border-color 0.3s;
        }

        .form-container input[type="email"]:focus,
        .form-container input[type="password"]:focus {
            border-color: #0077b6;
            outline: none;
        }

        .form-container input[type="submit"] {
            background-color: #0077b6;
            color: white;
            border: none;
            padding: 12px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 8px;
            font-weight: bold;
            transition: background-color 0.3s;
        }

        .form-container input[type="submit"]:hover {
            background-color: #1abc9c;
        }

        .form-container a {
            display: block;
            margin-top: 15px;
            color: #0077b6;
            text-decoration: none;
            font-size: 14px;
            text-align: center;
        }

        .form-container a:hover {
            text-decoration: underline;
        }

        @media (max-width: 480px) {
            .form-container {
                padding: 30px 20px;
                margin: 20px;
            }

            header {
                font-size: 20px;
            }
        }
    </style>
</head>
<body>

<header>
    Admin Login
</header>

<div class="form-container">
    <form action="AdminLogin" method="post">
        Username: <input type="email" name="email" required>
        Password: <input type="password" name="password" required>
        <input type="submit" value="Login">
    </form>
</div>

</body>
</html>
