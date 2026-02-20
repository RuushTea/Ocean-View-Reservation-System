package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Reservation;

import java.util.Date;

public class ReservationServiceImpl implements ReservationService{

    @Override
    public void createReservation(Reservation reservation) {

    }

    @Override
    public void checkAvailability(String roomType, Date checkInDate, Date checkOutDate) {

    }

    @Override
    public boolean checkRoomAvailability(int roomTypeId, Date checkInDate, Date checkOutDate) {
        return false;
    }

    @Override
    public void cancelReservation(int reservationId) {

    }
}
