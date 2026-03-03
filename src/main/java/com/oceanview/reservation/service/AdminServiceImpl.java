package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.StaffDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.SystemUser;

import java.util.List;

public class AdminServiceImpl implements AdminService{

    private final SystemUserDAO systemUserDAO = new SystemUserDAO();
    private final StaffDAO staffDAO = new StaffDAO();

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

    // Create a full System User
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
            int userId = systemUserDAO.createSystemUser(username, password, fullName, true);
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
    public List<Reservation> getAllReservations() {
        return List.of();
    }
}
