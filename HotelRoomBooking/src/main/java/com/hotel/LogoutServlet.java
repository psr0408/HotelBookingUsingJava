package com.hotel;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current session (without creating a new one)
        HttpSession session = request.getSession(false); // false will not create a new session if it doesn't exist

        if (session != null) {
            // Remove the "email" attribute and invalidate the session
            session.removeAttribute("email");
            session.invalidate();
        }

        // Redirect to the homepage or login page after logout
        response.sendRedirect("index.html");  // Adjust this if you want to redirect to a login page (e.g., login.jsp)
    }
}
