package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.service.strategy.MockAuthenticationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest {

    private AuthenticationServiceImpl service;
    private MockAuthenticationContext authContext;

    @BeforeEach
    void setUp() throws Exception {
        service = new AuthenticationServiceImpl();
        authContext = new MockAuthenticationContext();

        // Inject Mock DAOs
        setField(service, authContext);
    }

    private void setField(Object target, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField("authContext");
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testAuthenticateStaff_Success() {
        Staff mockStaff = new Staff();
        mockStaff.setStaffId(1);
        mockStaff.setUserId(1);
        mockStaff.setUserName("test@gmail.com");
        mockStaff.setPassword("password");
        mockStaff.setFullName("TestUser");
        mockStaff.setActive(true);

        authContext.setStaff(mockStaff);

        Staff result = service.authenticateStaff("test@gmail.com", "password");

        assertNotNull(result);
        assertEquals("TestUser", result.getFullName());
        assertEquals(1, result.getStaffId());
        assertTrue(result.isActive());
    }

    @Test
    void testAuthenticateStaff_InvalidCredentials() {
        authContext.setStaff(null);

        Staff result = service.authenticateStaff("invalid@gmail.com", "wrongpassword");

        assertNull(result);
    }

    @Test
    void testAuthenticateAdmin_Success() {
        Admin mockAdmin = new Admin();
        mockAdmin.setAdminId(1);
        mockAdmin.setUserId(1);
        mockAdmin.setUserName("testadmin@gmail.com");
        mockAdmin.setPassword("adminpass");
        mockAdmin.setFullName("Admin User");
        mockAdmin.setActive(true);

        authContext.setAdmin(mockAdmin);

        Admin result = service.authenticateAdmin("testadmin@gmail.com", "adminpass");

        assertNotNull(result);
        assertEquals("Admin User", result.getFullName());
        assertEquals(1, result.getAdminId());
        assertTrue(result.isActive());
    }

    @Test
    void testAuthenticateAdmin_InvalidCredentials() {
        authContext.setAdmin(null);

        Admin result = service.authenticateAdmin("invalid@gmail.com", "wrongpass");

        assertNull(result);
    }

    @Test
    void testVerifyGuestIdentity_Success() {
        Guest mockGuest = new Guest("TestGuest", "Test Address", "012345689");
        authContext.setGuest(mockGuest);

        Guest result = service.verifyGuestIdentity(1, "9876543210");

        assertNotNull(result);
        assertEquals("TestGuest", result.getName());
        assertEquals("012345689", result.getContactNo());
    }

    @Test
    void testVerifyGuestIdentity_InvalidReservation() {
        authContext.setGuest(null);

        Guest result = service.verifyGuestIdentity(999, "invalidnumber");

        assertNull(result);
    }

    @Test
    void testVerifyGuestIdentity_WrongContactNo() {
        authContext.setGuest(null);

        Guest result = service.verifyGuestIdentity(1, "1111111111");

        assertNull(result);
    }


}