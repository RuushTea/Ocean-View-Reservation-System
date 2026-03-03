package com.oceanview.reservation.service.strategy;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.model.Guest;

public class GuestAuthenticationStrategy implements AuthenticationStrategy {
    private final GuestDAO guestDAO = new GuestDAO();

    @Override
    public Object authenticate(String username, String password) {
        return null;
    }

    //Use reservationNo and contactNo to verify guest identity
    public Guest verifyGuestIdentity(int reservationNo, String contactNo) {
        try {
            return guestDAO.findByReservationNoAndContactNo(reservationNo, contactNo);
        } catch (Exception e) {
            System.out.println("No guest found for reservation No: " + reservationNo + " and contact No: " + contactNo);
            return null;
        }
    }
}
