package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.ReservationRequestDAO;
import com.oceanview.reservation.model.ReservationRequest;
import com.oceanview.reservation.model.ReservationRequestStatus;

import java.util.List;

public class ReservationRequestServiceImpl implements ReservationRequestService {
    
    private final ReservationRequestDAO reservationRequestDAO = new ReservationRequestDAO();

    @Override
    public int submitReservationRequest(ReservationRequest request) {
        if (request == null) {
            return -1;
        }
        
        if (request.getGuestName() == null || request.getGuestName().trim().isEmpty() ||
            request.getContactNo() == null || request.getContactNo().trim().isEmpty() ||
            request.getRoomTypeId() <= 0 ||
            request.getCheckInDate() == null ||
            request.getCheckOutDate() == null ||
            request.getRequestedByUserId() <= 0) {
            return -1;
        }
        
        return reservationRequestDAO.createReservationRequest(request);
    }

    @Override
    public ReservationRequest getRequestById(int requestId) {
        if (requestId <= 0) {
            return null;
        }
        return reservationRequestDAO.findById(requestId);
    }

    @Override
    public List<ReservationRequest> getPendingRequests() {
        return reservationRequestDAO.findByStatus(ReservationRequestStatus.PENDING);
    }

    @Override
    public List<ReservationRequest> getAllRequests() {
        return reservationRequestDAO.findAll();
    }

    @Override
    public boolean approveRequest(int requestId, int staffId) {
        if (requestId <= 0 || staffId <= 0) {
            return false;
        }
        
        ReservationRequest request = reservationRequestDAO.findById(requestId);
        if (request == null || request.getStatus() != ReservationRequestStatus.PENDING) {
            return false;
        }
        
        return reservationRequestDAO.updateStatus(requestId, ReservationRequestStatus.APPROVED, staffId, null);
    }

    @Override
    public boolean rejectRequest(int requestId, int staffId, String reason) {
        if (requestId <= 0 || staffId <= 0) {
            return false;
        }
        
        ReservationRequest request = reservationRequestDAO.findById(requestId);
        if (request == null || request.getStatus() != ReservationRequestStatus.PENDING) {
            return false;
        }
        
        return reservationRequestDAO.updateStatus(requestId, ReservationRequestStatus.REJECTED, staffId, reason);
    }

    @Override
    public List<ReservationRequest> getRequestsByStatus(ReservationRequestStatus status) {
        if (status == null) {
            return null;
        }
        return reservationRequestDAO.findByStatus(status);
    }
}
