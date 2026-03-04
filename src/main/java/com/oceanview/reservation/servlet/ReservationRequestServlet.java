package com.oceanview.reservation.servlet;

import com.oceanview.reservation.model.ReservationRequest;
import com.oceanview.reservation.service.ReservationRequestService;
import com.oceanview.reservation.service.ReservationRequestServiceImpl;
import com.oceanview.reservation.service.ReservationService;
import com.oceanview.reservation.service.ReservationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReservationRequestServlet", value = "/reservationRequest")
public class ReservationRequestServlet extends HttpServlet {
    
    private final ReservationRequestService reservationRequestService = new ReservationRequestServiceImpl();
    private final ReservationService reservationService = new ReservationServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("submit".equals(action)) {
            submitReservationRequest(request, response);
        } else if ("approve".equals(action)) {
            approveReservationRequest(request, response);
        } else if ("reject".equals(action)) {
            rejectReservationRequest(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("pending".equals(action)) {
            getPendingRequests(request, response);
        } else if ("view".equals(action)) {
            viewRequest(request, response);
        }
    }

    private void submitReservationRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String guestName = request.getParameter("guestName");
        String address = request.getParameter("address");
        String contactNo = request.getParameter("contactNo");
        
        // if guestName is null, get it from 'name' parameter
        if (guestName == null || guestName.trim().isEmpty()) {
            guestName = request.getParameter("name");
        }
        
        int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
        java.sql.Date checkInDate = java.sql.Date.valueOf(request.getParameter("checkInDate"));
        java.sql.Date checkOutDate = java.sql.Date.valueOf(request.getParameter("checkOutDate"));
        int requestedByUserId = Integer.parseInt(request.getParameter("requestedByUserId"));

        ReservationRequest reservationRequest = new ReservationRequest(
            guestName, address, contactNo, roomTypeId, checkInDate, checkOutDate, requestedByUserId);

        int requestId = reservationRequestService.submitReservationRequest(reservationRequest);
        
        if (requestId != -1) {
            response.sendRedirect("reservation/reservationRequestSuccess.jsp?submitted=success");
        } else {
            response.sendRedirect("reservation/reservationRequestSuccess.jsp?submitted=error");
        }
    }

    private void approveReservationRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));

        boolean approved = reservationRequestService.approveRequest(requestId, staffId);
        
        if (approved) {
            ReservationRequest approvedRequest = reservationRequestService.getRequestById(requestId);
            reservationService.createReservationFromRequest(approvedRequest);
            response.sendRedirect("staff-dashboard.jsp?status=approved&id=" + requestId);
        } else {
            response.sendRedirect("staff-dashboard.jsp?status=error&action=approve&id=" + requestId);
        }
    }

    private void rejectReservationRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        String reason = request.getParameter("reason");

        boolean rejected = reservationRequestService.rejectRequest(requestId, staffId, reason);
        
        if (rejected) {
            response.sendRedirect("staff-dashboard.jsp?status=rejected&id=" + requestId);
        } else {
            response.sendRedirect("staff-dashboard.jsp?status=error&action=reject&id=" + requestId);
        }
    }

    private void getPendingRequests(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<ReservationRequest> pendingRequests = reservationRequestService.getPendingRequests();
        
        response.setContentType("text/plain");
        response.getWriter().write("Pending requests: " + pendingRequests.size());
    }

    private void viewRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        ReservationRequest reservationRequest = reservationRequestService.getRequestById(requestId);
        
        response.setContentType("text/plain");
        if (reservationRequest != null) {
            response.getWriter().write("Request found: " + reservationRequest.getGuestName());
        } else {
            response.getWriter().write("Request not found");
        }
    }
}
