package com.oceanview.reservation.service.facade;

import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationFacadeInterface {
    
    // Core reservation operations
    Reservation createReservation(Guest guest, int roomTypeId, Date checkIn, Date checkOut);
    Reservation updateReservation(int reservationId, String guestName, String address, String contactNo, int roomtypeId, Date checkInDate, Date checkOutDate);
    Reservation searchReservation(int reservationId);
    List<Reservation> searchByGuestContactNo(String contactNo);
    boolean cancelReservation(int reservationId);
    boolean toggleCancel(int reservationId);
    
    // Billing operations
    Bill generateBill(int reservationId);
    Bill getBillDetails(int reservationId);
}
