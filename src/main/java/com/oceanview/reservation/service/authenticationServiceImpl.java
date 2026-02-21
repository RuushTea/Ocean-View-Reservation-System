package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;

import java.sql.SQLException;

public class authenticationServiceImpl implements AuthenticationService{
    private GuestDAO guestDAO = new GuestDAO();

    @Override
    public Staff authenticateStaff(String username, String password) {

        Staff staff = new Staff();
        if (staff == null){
            return null;
        }

        if (!password.equals(staff.getPassword())){
            return null;
        }

        return staff;

    }

    @Override
    public Admin authenticateAdmin(String username, String password) {

        Admin admin = new Admin();
        if (admin == null){
            return null;
        }

        if (!password.equals(admin.getPassword())){
            return null;
        }

        return admin;
    }

    @Override
    public Guest verifyGuestIdentity(int reservationNo, String contactNo) {
        try {
            return guestDAO.findByReservationNoAndContactNo(reservationNo, contactNo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
