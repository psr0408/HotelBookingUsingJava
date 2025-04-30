<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.jdbc.HotelJdbc" %>
<%@ page import="com.jdbc.Room" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f0f4f8;
            margin: 0;
            padding: 20px;
        }

        h2, h3 {
            color: #2c3e50;
            text-align: center;
        }

        .top-links {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-bottom: 30px;
        }

        .top-links a,
        .top-links form input[type="submit"] {
            padding: 10px 16px;
            background-color: #0077b6;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .top-links a:hover,
        .top-links form input[type="submit"]:hover {
            background-color: #1abc9c;
        }

        table {
            width: 100%;
            max-width: 1000px;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 14px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #0077b6;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 8px;
            align-items: center;
        }

        form input[type="text"] {
            padding: 6px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        form input[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 8px 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        form input[type="submit"]:hover {
            background-color: #218838;
        }

        button[disabled] {
            padding: 8px 14px;
            background-color: #ccc;
            border: none;
            border-radius: 6px;
            cursor: not-allowed;
        }

        p {
            text-align: center;
            color: #888;
            margin-top: 20px;
        }

        .message {
            text-align: center;
            font-weight: bold;
            padding: 10px;
            margin-top: 20px;
        }

        .message.success {
            color: green;
            background-color: #d4edda;
        }

        .message.error {
            color: red;
            background-color: #f8d7da;
        }
    </style>
</head>
<body>

    <h2>Hello, Welcome to the User Dashboard</h2>

    <div class="top-links">
        <a href="cancelBooking.jsp">View/Cancel Bookings</a>
        <a href="index.html">Home</a>
        <form action="LogoutServlet" method="post" style="display: inline;">
            <input type="submit" value="Logout" />
        </form>
    </div>

    <!-- Display success or error message -->
    <%
        String booked = request.getParameter("booked");
        String error = request.getParameter("error");

        // Display success message if booking was successful
        if ("true".equals(booked)) {
    %>
        <div class="message success">Room booked successfully!</div>
    <%
        } else if ("false".equals(booked)) {
    %>
        <div class="message error">Failed to book the room. Please try again.</div>
    <%
        } else if (error != null) {
    %>
        <div class="message error"><%= error %></div>
    <%
        }
    %>

    <h3>Available Rooms</h3>

    <%
        // Fetch available rooms from the database
        HotelJdbc db = new HotelJdbc();
        List<Room> rooms = db.getAvailableRooms();  // Store fetched rooms in 'rooms' variable

        // Check if there are available rooms
        if (rooms != null && !rooms.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Room ID</th>
            <th>Room Type</th>
            <th>Price</th>
            <th>Availability</th>
            <th>Action</th>
        </tr>
        <%
            // Iterate through each available room
            for (Room room : rooms) {
        %>
        <tr>
            <td><%= room.getRoomId() %></td>
            <td><%= room.getRoomType() %></td>
            <td>₹<%= room.getPrice() %></td>
            <td><%= room.isAvailable() ? "Available" : "Not Available" %></td>

            <td>
            <% if (room.isAvailable()) { %>

                    <!-- Form to book the room -->
                    <form action="BookServlet" method="post" onsubmit="return validateDate();">
                        <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>" />
                        <input type="hidden" name="roomId" value="<%= room.getRoomId() %>" />
                        <input type="date" name="bookingDate" required />
                        <input type="submit" value="Book Room" />
                    </form>
                <% } else { %>
                    <button disabled>Not Available</button>
                <% } %>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
        <p>No rooms available at the moment. Please check back later.</p>
    <% } %>

    <!-- Display error message if any -->
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>

</body>
</html>
