package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceIntegrationTest {

    private AuthenticationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AuthenticationServiceImpl();
    }

    // Failure cases without test data retention

    @Test
    void testAuthenticateStaff_EmptyUserName() {
        Staff result = service.authenticateStaff("", "password");
        assertNull(result);
    }

    @Test
    void testAuthenticateStaff_NullUsername() {
        Staff result = service.authenticateStaff(null, "password");
        assertNull(result);
    }

    @Test
    void testAuthenticateStaff_EmptyPassword() {
        Staff result = service.authenticateStaff("TestStaff", "");
        assertNull(result);
    }

    @Test
    void testAuthenticateStaff_NullPassword() {
        Staff result = service.authenticateStaff("TestStaff", null);
        assertNull(result);
    }

    @Test
    void testAuthenticateAdmin_EmptyUsername() {
        Admin result = service.authenticateAdmin("", "password");
        assertNull(result);
    }

    @Test
    void testAuthenticateAdmin_NullUsername() {
        Admin result = service.authenticateAdmin(null, "password");
        assertNull(result);
    }

    @Test
    void testAuthenticateAdmin_EmptyPassword() {
        Admin result = service.authenticateAdmin("TestAdmin", "");
        assertNull(result);
    }

    @Test
    void testAuthenticateAdmin_NullPassword() {
        Staff result = service.authenticateStaff("TestAdmin", null);
        assertNull(result);
    }

    @Test
    void testVerifyGuestIdentity_InvalidReservationNo() {
        var result = service.verifyGuestIdentity(99999, "9999999999");
        assertNull(result);
    }

    @Test
    void testVerifyGuestIdentity_InvalidContactNo() {
        var result = service.verifyGuestIdentity(1, "99999999999");
        assertNull(result);
    }

    @Test
    void testVerifyGuestIdentity_NullContactNo() {
        var result = service.verifyGuestIdentity(1, null);
        assertNull(result);
    }

    @Test
    void testVerifyGuestIdentity_ZeroReservationNo() {
        var result = service.verifyGuestIdentity(0, "0123456789");
        assertNull(result);
    }
}