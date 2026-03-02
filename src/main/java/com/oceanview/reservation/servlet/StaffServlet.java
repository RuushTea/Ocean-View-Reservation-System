package com.oceanview.reservation.servlet;

import javax.management.relation.Role;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StaffServlet", value = "/staff/home")
public class StaffServlet extends HttpServlet {

    private boolean isStaff(HttpSession session) {
        if (session.getAttribute("staff") == null){
            return false;
        }
        Object role = session.getAttribute("role");
        return role != null && "STAFF".equals(role.toString());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        HttpSession session = request.getSession(false);
        if (session == null || !"STAFF".equals(session.getAttribute("role"))) {
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        //Find which action for which code
        String action = request.getParameter("action");

        if (action == null){
            request.getRequestDispatcher(request.getContextPath() + "/staff/staffHome.jsp").forward(request, response);
            return;
        }

        switch (action){
            case "searchReservation":
                request.getRequestDispatcher(request.getContextPath() + "/staff/SearchReservation.jsp").forward(request, response);
                break;
            case "help":
                request.getRequestDispatcher(request.getContextPath() + "/staff/help.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);


        if (!isStaff(session)){
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        String action = request.getParameter("action");
        if (action == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        switch (action){
            case "logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/");
                break;

                //TODO add searchreservation and cancelreservation and bill

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
