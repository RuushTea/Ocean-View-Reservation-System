package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.dao.StaffDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.factory.UserFactory;
import com.oceanview.reservation.factory.UserFactoryImpl;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.SystemUser;

import java.util.List;

public class AdminServiceImpl implements AdminService{

    private final SystemUserDAO systemUserDAO = new SystemUserDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final UserFactory userFactory = new UserFactoryImpl();

    @Override
    public List<SystemUser> getAllStaffUsers() {
        return systemUserDAO.getAllStaffUsers();
    }

    @Override
    public SystemUser getStaffByUserId(int userId) {
        return systemUserDAO.findStaffUserByUserId(userId);
    }

    @Override
    public boolean updateStaffUser(int userId, String username, String password, String fullName) {
        if (username != null && username.trim().isEmpty()){
            username = null;
        }

        if (password != null && password.trim().isEmpty()){
            password = null;
        }

        if (fullName != null && fullName.trim().isEmpty()){
            fullName = null;
        }

        return systemUserDAO.updateStaffUser(userId, username, password, fullName);
    }

    @Override
    public boolean createStaff(String username, String password, String fullName) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            return false;
        }

        if (systemUserDAO.findByUsername(username) != null) {
            return false;
        }

        try {
            SystemUser systemUser = userFactory.createSystemUser(username, password, fullName, true);
            int userId = systemUserDAO.createSystemUser(systemUser.getUserName(), systemUser.getPassword(), systemUser.getFullName(), systemUser.isActive());
            if (userId == -1) {
                return false;
            }

            staffDAO.insertStaff(userId);
            return true;
        } catch (Exception e) {
            System.out.println("createStaff failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean toggleStaffActive(int userId) {
        try {
            SystemUser staff = systemUserDAO.findStaffUserByUserId(userId);
            if (staff == null) {
                return false;
            }
            
            boolean newActiveStatus = !staff.isActive();
            return systemUserDAO.updateActive(userId, newActiveStatus);
        } catch (Exception e) {
            System.out.println("toggleStaffActive failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteStaff(int userId) {
        try {
            SystemUser staff = systemUserDAO.findStaffUserByUserId(userId);
            if (staff == null) {
                return false;
            }

            staffDAO.deleteStaff(userId);
            return systemUserDAO.deleteUser(userId);
        } catch (Exception e) {
            System.out.println("deleteStaff failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }
}
