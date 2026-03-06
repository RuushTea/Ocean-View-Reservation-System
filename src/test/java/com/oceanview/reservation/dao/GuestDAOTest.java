package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestDAOTest {

    private GuestDAO dao;

    @BeforeEach
    void setUp() {
        dao = new GuestDAO();
    }

    @Test
    void testFindByGuestId_NotFound() {
        Guest result = dao.findByGuestId(99999);
        assertNull(result);
    }

    @Test
    void testInsertAndFindGuest_Success() throws Exception {
        Guest guest = new Guest();
        guest.setName("TestGuest" + System.currentTimeMillis());
        guest.setAddress("123 Main St");
        guest.setContactNo("999" + System.currentTimeMillis() % 10000000);

        dao.insertGuest(guest);

        Guest found = dao.findByContactNo(guest.getContactNo());
        assertNotNull(found);
        assertEquals(guest.getName(), found.getName());
    }

    @Test
    void testUpdateGuest_Success() {
        Guest guest = new Guest();
        guest.setGuestId(1);
        guest.setName("Updated Name");
        guest.setAddress("456 Oak Ave");
        guest.setContactNo("5559876543");

        boolean result = dao.updateGuest(guest);
        assertTrue(result);
    }

    @Test
    void testFindByContactNo_NotFound() throws Exception {
        Guest result = dao.findByContactNo("9999999999");
        assertNull(result);
    }
}