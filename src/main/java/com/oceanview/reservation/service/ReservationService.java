package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;

import java.sql.Date;

public interface ReservationService {
    Reservation createReservation(Guest guest, int roomTypeId, Date checkIn, Date checkOut);

    Reservation searchReservation(int reservationId);
    void checkAvailability(String roomType, Date checkInDate, Date checkOutDate);
    boolean checkRoomAvailability(int roomTypeId, Date checkInDate, Date checkOutDate);
    boolean cancelReservation(int reservationId);
    Bill generateBill(int reservationId);
}
