package com.oceanview.reservation.servlet;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.service.ReservationService;
import com.oceanview.reservation.service.ReservationServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GuestServlet", value = {"/guest-servlet", "/guest/view"})
public class GuestServlet extends HttpServlet {

    private final GuestDAO guestDAO = new GuestDAO();
    private final ReservationService reservationService = new ReservationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String pathInfo = request.getServletPath();
        
        if ("/guest/view".equals(pathInfo)) {
            try {
                request.getRequestDispatcher("/guest/viewReservation.jsp").forward(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }
        
        response.setContentType("text/plain");
        String guestIdString = request.getParameter("guestId");

        int guestId = Integer.parseInt(guestIdString);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String pathInfo = request.getServletPath();
        
        if ("/guest/view".equals(pathInfo)) {
            String contactNo = request.getParameter("contactNo");
            
            if (contactNo == null || contactNo.trim().isEmpty()) {
                request.setAttribute("error", "Please fill in all required fields");
                try {
                    request.getRequestDispatcher("/guest/viewReservation.jsp").forward(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            
            contactNo = contactNo.trim();
            
            if (!contactNo.matches("[0-9]{10}")) {
                request.setAttribute("error", "Contact number must be exactly 10 digits");
                try {
                    request.getRequestDispatcher("/guest/viewReservation.jsp").forward(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            List<Reservation> reservations = reservationService.searchByGuestContactNo(contactNo);

            if (reservations == null || reservations.isEmpty()) {
                request.setAttribute("error", "No reservations found for this contact number");
                try {
                    request.getRequestDispatcher("/guest/viewReservation.jsp").forward(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }

            request.setAttribute("reservations", reservations);
            try {
                request.getRequestDispatcher("/guest/reservationDetails.jsp").forward(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contactNo = request.getParameter("contactNo");

        try {
            Guest guest = new Guest(name, address, contactNo);
            guestDAO.insertGuest(guest);

            response.getWriter().println("Guest added successfully");

        } catch (Exception e){
            System.out.println(e.getMessage());
            response.getWriter().println("Error adding guest: " + e.getMessage());
        }
    }
}
