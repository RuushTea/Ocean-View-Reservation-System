package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;

import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService{
    private final GuestDAO guestDAO = new GuestDAO();
    private final SystemUserDAO systemUserDAO = new SystemUserDAO();

    @Override
    public Staff authenticateStaff(String username, String password) {

        SystemUser base = systemUserDAO.findByUsername(username);
        if (base == null) return null;
        if (!base.isActive()) return null;
        if (!password.equals(base.getPassword())) return null;

        Staff staff = systemUserDAO.findStaffByUserId(base.getUserId());
        if (staff == null) return null;

        //Copy the fields into staff object
        staff.setUserId(base.getUserId());
        staff.setUserName(base.getUserName());
        staff.setPassword(base.getPassword());
        staff.setFullName(base.getFullName());
        staff.setActive(base.isActive());

        return staff;

    }

    @Override
    public Admin authenticateAdmin(String username, String password) {

        SystemUser base = systemUserDAO.findByUsername(username);
        if (base == null) return null;
        if (!base.isActive()) return null;
        if (!password.equals(base.getPassword())) return null;

        Admin admin = systemUserDAO.findAdminByUserId(base.getUserId());
        if (admin == null) return null;

        //Copy the fields into admin object
        admin.setUserId(base.getUserId());
        admin.setUserName(base.getUserName());
        admin.setPassword(base.getPassword());
        admin.setFullName(base.getFullName());
        admin.setActive(base.isActive());

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
