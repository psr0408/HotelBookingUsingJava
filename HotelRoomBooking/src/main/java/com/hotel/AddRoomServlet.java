package com.hotel;

import java.io.IOException;
import java.io.PrintWriter;

import com.jdbc.HotelJdbc;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddRoomServlet
 */
@WebServlet("/AddRoomServlet")
public class AddRoomServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        // Getting form parameters
        String type = request.getParameter("type");
        String priceStr = request.getParameter("price");
        String availabilityStr = request.getParameter("availability");
        String userId = request.getParameter("userId");
        // Validate input values
        if (type == null || type.isEmpty() || priceStr == null || availabilityStr == null) {
            out.println("<h3>Error: All fields are required!</h3>");
            return;
        }

        // Parsing price and availability
        int price = 0;
        int availability = 0;
        int adminid = 0;

        
      
        try {
            adminid = Integer.parseInt(userId);
            System.out.println("User ID: " + userId);
        } catch (NumberFormatException e) {
            // Handle invalid userId (if it's not a valid integer)
            e.printStackTrace();
            response.getWriter().println("Invalid User ID.");
            return;
        }
        try {
            price = Integer.parseInt(priceStr);
            availability = Integer.parseInt(availabilityStr);
        } catch (NumberFormatException e) {
            out.println("<h3>Error: Price and Availability must be valid numbers!</h3>");
            return;
        }

        // Initialize database connection
        HotelJdbc db = new HotelJdbc();
        boolean added = false;

        try {
            added = db.addRoom(type, price, availability, adminid);
            if (added) {
                // On success, redirect to admin welcome page with a success message
                response.sendRedirect("adminwelcome.jsp?roomAdded=true");
            } else {
                // On failure, show an error message
                out.println("<h3>Failed to add room. Please try again.</h3>");
            }
        } catch (Exception e) {
            // Handle potential exceptions from the database
            e.printStackTrace();
            out.println("<h3>An error occurred while adding the room. Please try again later.</h3>");
        }
    }
}
