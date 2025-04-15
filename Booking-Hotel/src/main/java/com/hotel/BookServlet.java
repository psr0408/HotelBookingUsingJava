package com.hotel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.jdbc.HotelJdbc;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get parameters from the request
            int userId = Integer.parseInt(request.getParameter("userId"));
            int roomId = Integer.parseInt(request.getParameter("roomId"));
            String bookingDate = request.getParameter("bookingDate");
            System.out.println("User ID: " + userId);
            System.out.println("Room ID: " + roomId);


            // Validate the input values
            if (userId <= 0 || roomId <= 0 || bookingDate == null || bookingDate.isEmpty()) {
                response.sendRedirect("userwelcome.jsp?error=Invalid input values.");
                return;
            }

            // Initialize the database interaction object
            HotelJdbc db = new HotelJdbc();

            // Try booking the room
            boolean booked = db.bookRoom(userId, roomId, bookingDate);

            // Redirect the user with a success or failure message
            if (booked) {
                response.sendRedirect("welcomeuser.jsp?booked=true");
            } else {
                response.sendRedirect("welcomeuser.jsp?booked=false");
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format errors
            response.sendRedirect("welcomeuser.jsp?error=Invalid user or room ID.");
        } catch (Exception e) {
            // Catch other exceptions
            response.sendRedirect("welcomeuser.jsp?error=An unexpected error occurred. Please try again.");
        }
    }
}
