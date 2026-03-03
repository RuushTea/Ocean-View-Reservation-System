package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.SystemUser;

import java.util.List;

public interface AdminService {
    // Staff related methods
    List<SystemUser> getAllStaffUsers();
    SystemUser getStaffByUserId(int userId);
    boolean updateStaffUser(int userId, String username, String password, String fullName);

    List<Reservation> getAllReservations();
}
