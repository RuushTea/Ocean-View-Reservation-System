package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.dao.RoomDAO;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.Room;

import java.sql.SQLException;
import java.sql.Date;

public class ReservationServiceImpl implements ReservationService{

    private final GuestDAO guestDAO = new GuestDAO();
    private final RoomDAO roomDAO = new RoomDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    public Reservation createReservation(Guest guest, int roomTypeId, Date checkIn, Date checkOut) {

        if (checkIn == null || checkOut == null || !checkIn.before(checkOut)) {
            System.out.println("Invalid check-in or check-out dates.");
            return null;
        }

        // Find available room
        Room room = roomDAO.findAvailableRoom(roomTypeId, checkIn, checkOut);
        if (room == null){
            return null;
        }

        // Check if guest already exists
        if (guest.getGuestId() == 0){
            try {
                Guest existingGuest = guestDAO.findByContactNo(guest.getContactNo());
                if (existingGuest != null){
                    guest = existingGuest;
                } else {
                    int newGuestId = guestDAO.insertGuestAndReturnId(guest);
                    guest = guestDAO.findByGuestId(newGuestId);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");

        int generatedId = reservationDAO.insertAndReturnReservationId(reservation);
        reservation.setReservationId(generatedId);
        return reservation;
    }

    @Override
    public Reservation searchReservation(int reservationId) {
        return reservationDAO.findByReservationNo(reservationId);
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
