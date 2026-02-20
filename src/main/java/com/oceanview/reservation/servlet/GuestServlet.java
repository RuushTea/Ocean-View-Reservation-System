package com.oceanview.reservation.servlet;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.model.Guest;
import com.sun.source.tree.CatchTree;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GuestServlet", value = "/guest-servlet")
public class GuestServlet extends HttpServlet {

    private final GuestDAO guestDAO = new GuestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/plain");
        String guestId = request.getParameter("guestId");

        try {
            Guest g = guestDAO.findByGuestId(guestId);
            if (g == null){
                response.getWriter().println("Guest not found");
            } else {
                response.getWriter().println("Found: " + g.getName() + "(" + g.getGuestId() + ") \n");
                response.getWriter().println("Guest ID: " + guestId);
                response.getWriter().println("Guest Name: " + g.getName());
                response.getWriter().println("Guest Address: " + g.getAddress());
                response.getWriter().println("Guest Contact No: " + g.getContactNo());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contactNo = request.getParameter("contactNo");

        try {
            Guest guest = new Guest(name, address, contactNo);
            guestDAO.insertGuest(guest);

            response.getWriter().println("Guest added sucessfully");

        } catch (Exception e){
            System.out.println(e.getMessage());
            response.getWriter().println("Error adding guest: " + e.getMessage());
        }
    }
}
