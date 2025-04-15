package com.hotel;

import java.io.IOException;

import com.jdbc.HotelJdbc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        boolean canceled = false;

        try {
            HotelJdbc db = new HotelJdbc();
            canceled = db.cancelBooking(bookingId);
        } catch (Exception e) {
            e.printStackTrace(); // You could log this or show user-friendly error
        }

        response.sendRedirect("cancelBooking.jsp?canceled=" + canceled);
    }
}
