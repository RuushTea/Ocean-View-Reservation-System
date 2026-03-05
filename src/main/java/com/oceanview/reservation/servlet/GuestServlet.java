package com.oceanview.reservation.servlet;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.service.ReservationServiceImpl;
import com.oceanview.reservation.service.facade.ReservationFacade;
import com.oceanview.reservation.service.facade.ReservationFacadeInterface;
import com.oceanview.reservation.service.BillServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GuestServlet", value = "/guest/view")
public class GuestServlet extends HttpServlet {

    private final GuestDAO guestDAO = new GuestDAO();

    private final ReservationFacadeInterface reservationFacade;

    public GuestServlet() {
        this.reservationFacade = new ReservationFacade(
            new ReservationServiceImpl(),
            new BillServiceImpl()
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "viewForm";
        }
        
        switch (action) {
            case "viewForm":
                handleViewForm(request, response);
                break;
            default:
                handleGetGuest(request, response);
                break;
        }
    }
    
    private void handleViewForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/guest/viewReservation.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "addGuest";
        }
        
        switch (action) {
            case "viewReservations":
                handleViewReservations(request, response);
                break;
            case "cancelReservation":
                handleCancelReservation(request, response);
                break;
            case "generateBill":
                handleGenerateBill(request, response);
                break;
            case "addGuest":
                handleAddGuest(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }
    
    private void handleViewReservations(HttpServletRequest request, HttpServletResponse response) {
        String contactNo = request.getParameter("contactNo");
        
        if (contactNo == null || contactNo.trim().isEmpty()) {
            request.setAttribute("error", "Please fill in all required fields");
            forwardToViewForm(request, response);
            return;
        }
        
        contactNo = contactNo.trim();
        
        if (!contactNo.matches("[0-9]{10}")) {
            request.setAttribute("error", "Contact number must be exactly 10 digits");
            forwardToViewForm(request, response);
            return;
        }

        List<Reservation> reservations = reservationFacade.searchByGuestContactNo(contactNo);

        if (reservations == null || reservations.isEmpty()) {
            request.setAttribute("error", "No reservations found for this contact number");
            forwardToViewForm(request, response);
            return;
        }

        request.setAttribute("reservations", reservations);
        forwardToReservationDetails(request, response);
    }
    
    private void handleCancelReservation(HttpServletRequest request, HttpServletResponse response) {
        String reservationIdStr = request.getParameter("reservationId");
        String contactNo = request.getParameter("contactNo");
        
        if (reservationIdStr == null || contactNo == null) {
            request.setAttribute("error", "Invalid request");
            forwardToViewForm(request, response);
            return;
        }
        
        try {
            int reservationId = Integer.parseInt(reservationIdStr.trim());
            boolean cancelled = reservationFacade.cancelReservation(reservationId);
            
            if (cancelled) {
                List<Reservation> reservations = reservationFacade.searchByGuestContactNo(contactNo);
                request.setAttribute("reservations", reservations);
                request.setAttribute("success", "Reservation cancelled successfully");
                forwardToReservationDetails(request, response);
            } else {
                request.setAttribute("error", "Failed to cancel reservation");
                forwardToViewForm(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid reservation ID");
            forwardToViewForm(request, response);
        }
    }
    
    private void handleGenerateBill(HttpServletRequest request, HttpServletResponse response) {
        String reservationIdStr = request.getParameter("reservationId");
        String contactNo = request.getParameter("contactNo");
        
        if (reservationIdStr == null || contactNo == null) {
            request.setAttribute("error", "Invalid request");
            forwardToViewForm(request, response);
            return;
        }
        
        try {
            int reservationId = Integer.parseInt(reservationIdStr.trim());
            Bill bill = reservationFacade.generateBill(reservationId);
            
            if (bill != null) {
                List<Reservation> reservations = reservationFacade.searchByGuestContactNo(contactNo);
                request.setAttribute("reservations", reservations);
                request.setAttribute("bill", bill);
                request.setAttribute("selectedReservationId", reservationId);
                forwardToReservationDetails(request, response);
            } else {
                request.setAttribute("error", "Failed to generate bill");
                forwardToViewForm(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid reservation ID");
            forwardToViewForm(request, response);
        }
    }
    
    private void handleAddGuest(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    
    private void handleGetGuest(HttpServletRequest request, HttpServletResponse response) {
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
    
    private void forwardToViewForm(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/guest/viewReservation.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void forwardToReservationDetails(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/guest/reservationDetails.jsp").forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
