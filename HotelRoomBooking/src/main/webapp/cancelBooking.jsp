<%@ page import="java.util.List" %>
<%@ page import="com.jdbc.HotelJdbc" %>
<%@ page import="com.jdbc.Booking" %>
<%
    Integer userIdObj = (Integer) session.getAttribute("userId");

    // Check if user is logged in
    if (userIdObj == null) {
%>
    <p style="color:red; text-align: center;">You must be logged in to view your bookings.</p>
<%
    } else {
        int userId = userIdObj;
        HotelJdbc db = new HotelJdbc();
        List<Booking> bookings = db.getUserBookings(userId);
%>

<!-- Title Section -->
<h2 style="text-align: center; color: #333; font-size: 28px; margin-top: 20px;">Your Bookings</h2>

<!-- Table Section -->
<table border="1" cellspacing="0" cellpadding="8" style="width: 80%; margin: 30px auto; border-collapse: collapse; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
    <tr style="background-color: #2980b9; color: white; text-align: center;">
        <th>ID</th>
        <th>Room ID</th>
        <th>Booking Date</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

<%
    // Loop through each booking and display details
    for (Booking booking : bookings) {
%>
    <tr style="text-align: center;">
        <td><%= booking.getId() %></td>
        <td><%= booking.getRoomId() %></td>
        <td><%= booking.getBookingDate() %></td>
        <td><%= booking.getStatus() %></td>
        <td>
            <% 
                // Show "Cancel" button only if the booking is not already canceled
                if (!"not booked".equalsIgnoreCase(booking.getStatus())) { 
            %>
                <form action="CancelBookingServlet" method="post" style="display:inline-block;">
                    <input type="hidden" name="bookingId" value="<%= booking.getId() %>" />
                    <input type="submit" value="Cancel" style="background-color: #e74c3c; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; transition: background-color 0.3s;"/>
                </form>
            <% 
                } else { 
            %>
                <span style="color: #e74c3c; font-weight: bold;">Cancelled</span>
            <% 
                } 
            %>
        </td>
    </tr>
<%
    }
%>
</table>

<%
    }
%>
