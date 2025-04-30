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
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get login credentials from the form
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        int userId = 0;
        // Set content type for proper response rendering
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Initialize HotelJdbc to interact with the database
        HotelJdbc admin1 = new HotelJdbc();
        try {
            // Check if admin credentials are correct
            if (admin1.loginAdmin(email, password)) {
                // Retrieve userId by email after successful admin login
                try {
					userId = admin1.getAdminId(email, password);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                // Log the userId for debugging or use it as needed
                System.out.println("User ID retrieved for email " + email + ": " + userId);

                // Start a new session for the admin
                HttpSession session = request.getSession();
                session.setAttribute("email", email);
                session.setAttribute("userId", userId); // Store userId in session
                
                // Redirect to the admin welcome page after successful login
                response.sendRedirect("adminwelcome.jsp");
            } else {
                // Provide feedback on invalid login credentials
                out.println("<html><body>");
                out.println("<h3>Invalid email or password. Please try again.</h3>");
                out.println("<a href='admin-login.jsp'>Go back to login</a>");
                out.println("</body></html>");
            }

        } catch (ClassNotFoundException e) {
            // Log and handle exception gracefully
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>There was an error processing your login request. Please try again later.</h3>");
            out.println("</body></html>");
        } catch (SQLException e) {
            // Log and handle SQL exception gracefully
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>Database error. Please try again later.</h3>");
            out.println("</body></html>");
        }
    }
}
