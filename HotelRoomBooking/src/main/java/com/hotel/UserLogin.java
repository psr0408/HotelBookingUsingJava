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

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get email and password from form
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        System.out.println("Login attempt for: " + email);
        int userId = 0;
        try {
            HotelJdbc userDao = new HotelJdbc();

            // Check if login is valid
            if (userDao.loginUser(email, password)) {
                // Retrieve user ID from database (assuming the loginUser method or another one can return the user ID)
                 try {
					userId = userDao.getUserIdByEmail(email);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Assuming this method exists and returns the user ID

                // Create session and set the userId
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId); // Use userId here instead of email

                // Redirect to user dashboard
                response.sendRedirect("welcomeuser.jsp");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid email or password');");
                out.println("location='user-login.jsp';");
                out.println("</script>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Server Error: Please try again later.</h3>");
        }
    }
}
