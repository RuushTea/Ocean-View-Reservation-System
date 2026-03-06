package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.mocks.MockStaffDAO;
import com.oceanview.reservation.dao.mocks.MockUserDAO;
import com.oceanview.reservation.service.factory.MockUserFactory;
import com.oceanview.reservation.model.SystemUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {

    private AdminServiceImpl service;
    private MockDAO dao;

    @BeforeEach
    void setUp() throws Exception {
        service = new AdminServiceImpl();
        dao = new MockDAO();

        setField(service, "systemUserDAO", dao.userDAO);
        setField(service, "staffDAO", dao.staffDAO);
        setField(service, "userFactory", new MockUserFactory());
    }

    private void setField(Object t, String f, Object v) throws Exception {
        Field field = t.getClass().getDeclaredField(f);
        field.setAccessible(true);
        field.set(t, v);
    }

    @Test
    void testGetAllStaffUsers() {
        dao.userDAO.staff = java.util.List.of(new SystemUser(1, "test@gmail.com", "password", "TestUser", true));
        assertEquals(1, service.getAllStaffUsers().size());
    }

    @Test
    void testGetStaffByUserId() {
        dao.userDAO.user = new SystemUser(1, "test@gmail.com", "password", "TestUser", true);
        assertNotNull(service.getStaffByUserId(1));
    }

    @Test
    void testUpdateStaffUser() {
        assertTrue(service.updateStaffUser(1, "test@gmail.com", "password", "TestUser"));
    }

    @Test
    void testCreateStaff_Success() {
        dao.userDAO.user = null;
        dao.userDAO.createId = 1;
        dao.staffDAO.ok = true;
        assertTrue(service.createStaff("test@gmail.com", "password", "TestUser"));
    }

    @Test
    void testCreateStaff_Invalid() {
        assertFalse(service.createStaff("", "password", "TestUser"));
        assertFalse(service.createStaff("test@gmail.com", "  ", "TestUser"));
    }

    @Test
    void testCreateStaff_DuplicateUser() {
        dao.userDAO.user = new SystemUser(1, "test@gmail.com", "password", "TestUser", true);
        assertFalse(service.createStaff("test@gmail.com", "password", "TestUser"));
    }

    @Test
    void testToggleStaffActive_Success() {
        dao.userDAO.user = new SystemUser(1, "test@gmail.com", "password", "TestUser", true);
        dao.userDAO.ok = true;
        assertTrue(service.toggleStaffActive(1));
    }

    @Test
    void testToggleStaffActive_NotFound() {
        dao.userDAO.user = null;
        assertFalse(service.toggleStaffActive(999));
    }

    @Test
    void testDeleteStaff_Success() {
        dao.userDAO.user = new SystemUser(1, "test@gmail.com", "password", "TestUser", true);
        dao.staffDAO.ok = true;
        dao.userDAO.ok = true;
        assertTrue(service.deleteStaff(1));
    }

    @Test
    void testGetAllReservations() {
        assertNotNull(service.getAllReservations());
    }

    static class MockDAO {
        MockUserDAO userDAO = new MockUserDAO();
        MockStaffDAO staffDAO = new MockStaffDAO();
    }


}