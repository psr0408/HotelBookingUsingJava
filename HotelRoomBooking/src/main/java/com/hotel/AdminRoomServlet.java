package com.hotel;

import java.io.IOException;
import java.util.List;

import com.jdbc.Room;
import com.jdbc.RoomService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/AddRoommServlet")
public class AdminRoomServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract room details from the form
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("price"));  // Ensure it's parsed as a double
        int availability = Integer.parseInt(request.getParameter("availability"));  // Ensure it's parsed as an int

        // Create a new Room object (using the correct constructor)
        Room newRoom = new Room(type, price, availability); // No need to pass roomId

        // Add the room to the database using RoomService
        RoomService roomService = new RoomService();
        roomService.addRoom(newRoom);

        // Get the updated list of rooms
        List<Room> rooms = roomService.getAllRooms();

        // Set the updated rooms list as a request attribute
        request.setAttribute("rooms", rooms);

        // Forward the request to adminwelcome.jsp
        request.getRequestDispatcher("adminwelcome.jsp").forward(request, response);
    }
}
