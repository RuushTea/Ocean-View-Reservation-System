package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Reservation;

import java.util.Date;

public interface ReservationService {
    void createReservation(Reservation reservation);
    void checkAvailability(String roomType, Date checkInDate, Date checkOutDate);
    boolean checkRoomAvailability(int roomTypeId, Date checkInDate, Date checkOutDate);
    void cancelReservation(int reservationId);
}
