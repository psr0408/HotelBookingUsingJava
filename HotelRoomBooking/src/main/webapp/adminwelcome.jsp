<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef2f3;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #343a40;
            color: white;
            padding: 20px 0;
            text-align: center;
        }

        h2, h3 {
            color: #2c3e50;
            text-align: center;
        }

        .container {
            width: 85%;
            margin: 30px auto;
            background-color: #ffffff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
        }

        .success-message {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            text-align: center;
        }

        form {
            margin: 20px 0;
        }

        label {
            font-weight: bold;
            display: block;
            margin: 10px 0 4px;
        }

        input[type="text"],
        input[type="number"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            font-size: 15px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #28a745;
            color: white;
            font-weight: bold;
            border: none;
            padding: 10px 18px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }

        .logout-btn {
            background-color: #dc3545;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            float: right;
        }

        .logout-btn:hover {
            background-color: #c82333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }

        table th,
        table td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        table th {
            background-color: #0077b6;
            color: white;
        }

        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .clearfix::after {
            content: "";
            clear: both;
            display: table;
        }
        header {
    background-color: #343a40;
    color: white;
    padding: 25px 0;
    text-align: center;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.2);
}

header h1 {
    margin: 0;
    font-size: 32px;
    letter-spacing: 1px;
}
        
    </style>
</head>
<body>

<header>
    <h1>Welcome Admin</h1>
</header>

<div class="container">
    <div class="clearfix">
        <form action="LogoutServlet" method="post" style="float: right;">
            <input type="submit" value="Logout" class="logout-btn" />
        </form>
    </div>

  <% 
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            out.println("<p>User ID: " + userId + "</p>");
        } else {
            out.println("<p>Could not retrieve User ID.</p>");
        }
    %>
    <%-- Success message --%>
    <%
        String roomAdded = request.getParameter("roomAdded");
        if ("true".equals(roomAdded)) {
    %>
        <div class="success-message">
            ✅ Room added successfully!
        </div>
    <%
        }
    %>

    <h3>Add New Room</h3>
    <form action="AddRoomServlet" method="post">
        <input type="hidden" name="userId" value="${sessionScope.userId}" /> 
        <label for="type">Room Type:</label>
        <input type="text" name="type" required />

        <label for="price">Price:</label>
        <input type="number" name="price" required />

        <label for="availability">Availability (1 = Available, 0 = Not Available):</label>
        <input type="number" name="availability" required />

        <input type="submit" value="Add Room" />
    </form>

    <h3>Rooms List</h3>
    <table>
        <thead>
            <tr>
                <th>Room ID</th>
                <th>Room Type</th>
                <th>Price</th>
                <th>Availability</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="room" items="${rooms}">
                <tr>
                    <td>${room.roomId}</td>
                    <td>${room.roomType}</td>
                    <td>₹${room.price}</td>
                    <td>${room.availability == 1 ? 'Available' : 'Not Available'}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
