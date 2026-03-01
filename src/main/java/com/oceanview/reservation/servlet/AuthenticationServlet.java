package com.oceanview.reservation.servlet;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.service.AuthenticationService;
import com.oceanview.reservation.service.AuthenticationServiceImpl;
import com.oceanview.reservation.util.DBConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "AuthenticationServlet", value = "/auth")
public class AuthenticationServlet extends HttpServlet{

    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //Admin check
        Admin admin = authenticationService.authenticateAdmin(username, password);
        //Make session for Admin
        if (admin != null){
            HttpSession session = request.getSession(true);
            session.setAttribute("user", admin);
            session.setAttribute("role", "ADMIN");
            response.sendRedirect(request.getContextPath() + "/admin/home");
            return;
        }

        //Staff Check
        Staff staff = authenticationService.authenticateStaff(username, password);
        //Make session for Staff
        if (staff != null){
            HttpSession session = request.getSession(true);
            session.setAttribute("user", staff);
            session.setAttribute("role", "STAFF");
            response.sendRedirect(request.getContextPath() + "/staff/home");
            return;
        }

        request.setAttribute("error", "Invalid username or password");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
