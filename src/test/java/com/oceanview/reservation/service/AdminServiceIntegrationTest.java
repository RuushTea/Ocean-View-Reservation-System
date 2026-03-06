package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.StaffDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.SystemUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceIntegrationTest {

    private AdminServiceImpl service;
    private SystemUserDAO userDAO;
    private StaffDAO staffDAO;

    // Track ALL created user IDs for cleanup
    private final List<Integer> createdUserIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        service = new AdminServiceImpl();
        userDAO = new SystemUserDAO();
        staffDAO = new StaffDAO();
        createdUserIds.clear();
    }
    private String generateUniqueEmail() {
        return "teststaff_" + UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
    }

    private void trackUser(int userId) {
        if (userId > 0) {
            createdUserIds.add(userId);
        }
    }

    @AfterEach
    void tearDown() {
        for (int userId : createdUserIds) {
            try {
                staffDAO.deleteStaff(userId);
            } catch (Exception e) {
                System.out.println("Failed to delete staff: " + e.getMessage());
            }

            try {
                userDAO.deleteUser(userId);
            } catch (Exception e) {
                System.out.println("Failed to delete user: " + e.getMessage());
            }
        }
        createdUserIds.clear();
    }

    @Test
    void testGetAllStaffUsers_Success() {
        List<SystemUser> result = service.getAllStaffUsers();
        assertNotNull(result);
    }

    @Test
    void testGetStaffByUserId_Success() {
        String email = generateUniqueEmail();
        boolean created = service.createStaff(email, "password123", "Test Staff");
        assertTrue(created);

        List<SystemUser> allStaff = service.getAllStaffUsers();
        SystemUser testUser = allStaff.stream()
                .filter(s -> s.getUserName().equals(email))
                .findFirst()
                .orElse(null);

        assertNotNull(testUser);
        trackUser(testUser.getUserId());

        SystemUser found = service.getStaffByUserId(testUser.getUserId());
        assertNotNull(found);
        assertNotNull(found.getFullName());
        assertEquals("Test Staff", found.getFullName());
    }

    @Test
    void testUpdateStaffUser_Success() {
        String email = generateUniqueEmail();
        boolean created = service.createStaff(email, "password123", "Original Name");
        assertTrue(created);

        List<SystemUser> allStaff = service.getAllStaffUsers();
        SystemUser testUser = allStaff.stream()
                .filter(s -> s.getUserName().equals(email))
                .findFirst()
                .orElse(null);

        assertNotNull(testUser);
        int userId = testUser.getUserId();
        trackUser(userId);

        String newEmail = generateUniqueEmail();
        boolean updated = service.updateStaffUser(userId, newEmail, "newpass", "Updated Name");
        assertTrue(updated);

        SystemUser result = service.getStaffByUserId(userId);
        assertNotNull(result);
        assertEquals("Updated Name", result.getFullName());
    }

    @Test
    void testGetAllReservations_Success() {
        List<Reservation> result = service.getAllReservations();
        assertNotNull(result);
    }

    @Test
    void testCreateStaff_Success() {
        String uniqueEmail = generateUniqueEmail();
        boolean result = service.createStaff(uniqueEmail, "password123", "Test Staff Member");
        assertTrue(result);

        List<SystemUser> allStaff = service.getAllStaffUsers();
        boolean found = allStaff.stream()
                .anyMatch(staff -> staff.getUserName().equals(uniqueEmail));
        assertTrue(found);

        SystemUser created = allStaff.stream()
                .filter(s -> s.getUserName().equals(uniqueEmail))
                .findFirst()
                .orElse(null);
        if (created != null) {
            trackUser(created.getUserId());
        }
    }

    @Test
    void testGetStaffByUserId_NotFound() {
        SystemUser result = service.getStaffByUserId(99999);
        assertNull(result);
    }

    @Test
    void testCreateStaff_EmptyUsername() {
        boolean result = service.createStaff("", "password123", "Test Staff");
        assertFalse(result);
    }

    @Test
    void testCreateStaff_EmptyPassword() {
        boolean result = service.createStaff(generateUniqueEmail(), "", "Test Staff");
        assertFalse(result);
    }

    @Test
    void testCreateStaff_EmptyFullName() {
        boolean result = service.createStaff(generateUniqueEmail(), "password123", "");
        assertFalse(result);
    }

    @Test
    void testCreateStaff_DuplicateUsername() {
        String email = generateUniqueEmail();

        boolean result1 = service.createStaff(email, "password", "Test Staff");
        assertTrue(result1);

        List<SystemUser> allStaff = service.getAllStaffUsers();
        SystemUser created = allStaff.stream()
                .filter(s -> s.getUserName().equals(email))
                .findFirst()
                .orElse(null);
        if (created != null) {
            trackUser(created.getUserId());
        }

        boolean result2 = service.createStaff(email, "password", "Test Staff");
        assertFalse(result2);
    }

    @Test
    void testToggleStaffActive_NotFound() {
        boolean result = service.toggleStaffActive(99999);
        assertFalse(result);
    }

    @Test
    void testDeleteStaff_NotFound() {
        boolean result = service.deleteStaff(99999);
        assertFalse(result);
    }

    @Test
    void testCreateStaff_NullUsername() {
        boolean result = service.createStaff(null, "password123", "Test Staff");
        assertFalse(result);
    }

    @Test
    void testCreateStaff_NullPassword() {
        boolean result = service.createStaff(generateUniqueEmail(), null, "Test Staff");
        assertFalse(result);
    }

    @Test
    void testCreateStaff_NullFullName() {
        boolean result = service.createStaff(generateUniqueEmail(), "password123", null);
        assertFalse(result);
    }
}