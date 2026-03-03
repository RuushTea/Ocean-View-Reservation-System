package com.oceanview.reservation.service;

import com.oceanview.reservation.model.ReservationRequest;
import com.oceanview.reservation.model.ReservationRequestStatus;

import java.util.List;

public interface ReservationRequestService {
    int submitReservationRequest(ReservationRequest request);
    ReservationRequest getRequestById(int requestId);
    List<ReservationRequest> getPendingRequests();
    List<ReservationRequest> getAllRequests();
    boolean approveRequest(int requestId, int staffId);
    boolean rejectRequest(int requestId, int staffId, String reason);
    List<ReservationRequest> getRequestsByStatus(ReservationRequestStatus status);
}
