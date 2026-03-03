package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;

public interface AuthenticationService {
    Staff authenticateStaff(String username, String password);
    Admin authenticateAdmin(String username, String password);
    Guest verifyGuestIdentity(int reservationNo, String contactNo);
}
