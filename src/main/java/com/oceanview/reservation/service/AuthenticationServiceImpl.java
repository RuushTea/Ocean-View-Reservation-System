package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.AdminDAO;
import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.dao.StaffDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.factory.UserFactory;
import com.oceanview.reservation.factory.UserFactoryImpl;
import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;

import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService{
    private final GuestDAO guestDAO = new GuestDAO();
    private final SystemUserDAO systemUserDAO = new SystemUserDAO();
    private final AdminDAO adminDAO = new AdminDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final UserFactory userFactory = new UserFactoryImpl();

    @Override
    public Staff authenticateStaff(String username, String password) {

        SystemUser base = authenticationBase(username, password);
        if (base == null) return null;

        Staff staff = staffDAO.findByUserId(base.getUserId());
        if (staff == null) return null;

        return userFactory.createStaff(base);
    }

    @Override
    public Admin authenticateAdmin(String username, String password) {

        SystemUser base = authenticationBase(username, password);
        if (base == null) return null;

        Admin admin = adminDAO.findByUserId(base.getUserId());
        if (admin == null) return null;

        return userFactory.createAdmin(base);
    }

    private SystemUser authenticationBase(String username, String password){
        SystemUser base = systemUserDAO.findByUsername(username);
        if (base == null || !base.isActive() || !password.equals(base.getPassword())) {
            return null;
        }
        return base;
    }

    @Override
    public Guest verifyGuestIdentity(int reservationNo, String contactNo) {
        try {
            return guestDAO.findByReservationNoAndContactNo(reservationNo, contactNo);
        } catch (Exception e) {
            System.out.println("No guest found for reservation No: " + reservationNo + " and contact No: " + contactNo);
            return null;
        }
    }
}
