package com.oceanview.reservation.service.strategy;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;

public class AuthenticationContext {
    private AuthenticationStrategy strategy;

    public void setStrategy(AuthenticationStrategy strategy) {
        this.strategy = strategy;
    }

    public Object authenticate(String username, String password) {
        if (strategy == null) {
            throw new IllegalStateException("Authentication strategy not set");
        }
        return strategy.authenticate(username, password);
    }

    public Admin authenticateAdmin(String username, String password) {
        setStrategy(new AdminAuthenticationStrategy());
        return (Admin) authenticate(username, password);
    }

    public Staff authenticateStaff(String username, String password) {
        setStrategy(new StaffAuthenticationStrategy());
        return (Staff) authenticate(username, password);
    }

    public Guest verifyGuestIdentity(int reservationNo, String contactNo) {
        GuestAuthenticationStrategy guestStrategy = new GuestAuthenticationStrategy();
        return guestStrategy.verifyGuestIdentity(reservationNo, contactNo);
    }
}
