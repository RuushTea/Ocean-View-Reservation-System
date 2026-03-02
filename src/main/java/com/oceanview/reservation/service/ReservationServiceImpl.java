package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.dao.RoomDAO;
import com.oceanview.reservation.model.Bill;
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
    public boolean cancelReservation(int reservationId) {
        return reservationDAO.updateStatus(reservationId, "CANCELLED");
    }

    //Generate Bill for Staff
    @Override
    public Bill generateBill(int reservationId) {
        Reservation reservation = reservationDAO.findByReservationNo(reservationId);

        if (reservation == null){
            return null;
        }

        long dateDifference = reservation.getCheckOutDate().getTime() - reservation.getCheckInDate().getTime();

        int nights = (int) (dateDifference / (1000 * 60 * 60 * 24));
        double ratePerNight = reservation.getRoom().getRoomType().getRatePerNight();

        return new Bill(reservationId, nights, ratePerNight);
    }

    @Override
    public boolean toggleCancel(int reservationId) {

        String currentStatus = reservationDAO.getStatus(reservationId);

        if (currentStatus == null){return false;}

        if ("CONFIRMED".equalsIgnoreCase(currentStatus)){
            return reservationDAO.updateStatus(reservationId, "CANCELLED");
        }

        if ("CANCELLED".equalsIgnoreCase(currentStatus)){
            return reservationDAO.updateStatus(reservationId, "CONFIRMED");
        }

        // Return false if the status is neither CONFIRMED nor CANCELLED
        return false;
    }


}
