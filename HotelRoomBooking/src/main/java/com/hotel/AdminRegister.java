package com.hotel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.jdbc.HotelJdbc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminRegister")
public class AdminRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Set content type to ensure proper encoding of response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Initialize HotelJdbc class to interact with the database
        HotelJdbc user1 = new HotelJdbc();
        try {
            boolean isRegistered = user1.registerAdmin(name, mobile, email, password);

            // Provide appropriate feedback based on registration status
            if (isRegistered) {
                out.println("<html><body>");
                out.println("<h3>" + name + ", you have registered successfully!</h3>");
                out.println("<a href='admin-login.jsp'>Click here to login</a>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h3>Registration failed. Please try again.</h3>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Log the error message for debugging purposes
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>There was an error processing your registration. Please try again later.</h3>");
            out.println("</body></html>");
        }
    }
}
