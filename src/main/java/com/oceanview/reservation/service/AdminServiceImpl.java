package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.SystemUser;

import java.util.List;

public class AdminServiceImpl implements AdminService{

    private final SystemUserDAO systemUserDAO = new SystemUserDAO();

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
    public List<Reservation> getAllReservations() {
        return List.of();
    }
}
