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

@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get form values
        String name = request.getParameter("username").trim();
        String mobile = request.getParameter("mobile").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        try {
            HotelJdbc userJdbc = new HotelJdbc();

            if (userJdbc.registerUser(name, mobile, email, password)) {
//                out.println("<script type=\"text/javascript\">");
//                out.println("alert('Registration successful. Please log in.');");
//                out.println("location='user-login.jsp';");
//                out.println("</script>");
            	 out.println("<html><body>");
                 out.println("<h3>" + name + ", you have registered successfully!</h3>");
                 out.println("<a href='admin-login.jsp'>Click here to login</a>");
                 out.println("</body></html>");
            } else {
//                out.println("<script type=\"text/javascript\">");
//                out.println("alert('Registration failed. Try again.');");
//                out.println("location='user-register.jsp';");
//                out.println("</script>");
            	 out.println("<html><body>");
                 out.println("<h3>" + name + ", you have registered successfully!</h3>");
                 out.println("<a href='admin-login.jsp'>Click here to login</a>");
                 out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Server Error. Please try again later.</h3>");
        }
    }
}
