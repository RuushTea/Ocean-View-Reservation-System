package com.oceanview.reservation.service.strategy;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;

public class MockAuthenticationContext extends AuthenticationContext {
    private Staff staff;
    private Admin admin;
    private Guest guest;

    public void setStaff(Staff s) { this.staff = s; }
    public void setAdmin(Admin a) { this.admin = a; }
    public void setGuest(Guest g) { this.guest = g; }

    @Override
    public Staff authenticateStaff(String username, String password) {
        return staff;
    }

    @Override
    public Admin authenticateAdmin(String username, String password) {
        return admin;
    }

    @Override
    public Guest verifyGuestIdentity(int reservationNo, String contactNo) {
        return guest;
    }
}