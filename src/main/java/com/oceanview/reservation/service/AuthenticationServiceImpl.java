package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.service.strategy.AuthenticationContext;

public class AuthenticationServiceImpl implements AuthenticationService{
    // Strategy pattern
    private final AuthenticationContext authContext = new AuthenticationContext();

    @Override
    public Staff authenticateStaff(String username, String password) {
        return authContext.authenticateStaff(username, password);
    }

    @Override
    public Admin authenticateAdmin(String username, String password) {
        return authContext.authenticateAdmin(username, password);
    }

    @Override
    public Guest verifyGuestIdentity(int reservationNo, String contactNo) {
        return authContext.verifyGuestIdentity(reservationNo, contactNo);
    }
}
