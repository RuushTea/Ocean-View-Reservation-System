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

    //Update Reservation
    @Override
    public Reservation updateReservation(int reservationId, String guestName, String address, String contactNo, int roomtypeId, Date checkInDate, Date checkOutDate) {

        if (checkInDate == null || checkOutDate == null || !checkInDate.before(checkOutDate)) {
            System.out.println("Invalid update dates");
            return null;
        }

        Reservation existing = reservationDAO.findByReservationNo(reservationId);
        if (existing == null){
            System.out.println("Reservation not found for update");
            return null;
        }

        // Update guest information
        Guest g = existing.getGuest();
        g.setName(guestName);
        g.setAddress(address);
        g.setContactNo(contactNo);

        boolean guestUpdated = guestDAO.updateGuest(g);


        // Check for room re-allocation
        boolean roomTypeChanged = existing.getRoom().getRoomType().getRoomTypeId() != roomtypeId;
        boolean datesChanged = !existing.getCheckInDate().equals(checkInDate) || !existing.getCheckOutDate().equals(checkOutDate);

        if (roomTypeChanged || datesChanged){
            Room newRoom = roomDAO.findAvailableRoomForUpdate(roomtypeId, checkInDate, checkOutDate, reservationId);

            if (newRoom == null){
                System.out.println("No available room for updated dates or type");
                return null;
            }

            //update room id in reservation
            boolean roomUpdated = reservationDAO.updateRoom(reservationId, newRoom.getRoomId());
            if (!roomUpdated){return null;}

            existing.setRoom(newRoom);
        }

        //Update dates
        boolean datesUpdated = reservationDAO.updateReservationDates(reservationId, checkInDate, checkOutDate);
        if (!datesUpdated) {return null;}

        existing.setCheckInDate(checkInDate);
        existing.setCheckOutDate(checkOutDate);

        return existing;
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
