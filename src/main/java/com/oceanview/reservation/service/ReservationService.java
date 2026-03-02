package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(Guest guest, int roomTypeId, Date checkIn, Date checkOut);
    Reservation updateReservation(int reservationId, String guestName, String address, String contactNo,
                                  int roomtypeId, Date checkInDate, Date checkOutDate);
    Reservation searchReservation(int reservationId);
    List<Reservation> searchByGuestContactNo(String contactNo);
    void checkAvailability(String roomType, Date checkInDate, Date checkOutDate);
    boolean checkRoomAvailability(int roomTypeId, Date checkInDate, Date checkOutDate);
    boolean cancelReservation(int reservationId);
    Bill generateBill(int reservationId);
    boolean toggleCancel(int reservationId);
}
