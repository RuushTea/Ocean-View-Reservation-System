package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDAOTest {

    private ReservationDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ReservationDAO();
    }

    @Test
    void testFindByReservationNo_NotFound() {
        Reservation result = dao.findByReservationNo(99999);
        assertNull(result);
    }

    @Test
    void testGetStatus_NotFound() {
        String result = dao.getStatus(99999);
        assertNull(result);
    }

    @Test
    void testUpdateStatus_InvalidReservationId() {
        boolean result = dao.updateStatus(99999, "CANCELLED");
        assertFalse(result);
    }

    @Test
    void testFindByContactNo_Empty() {
        List<Reservation> result = dao.findByContactNo("9999999999");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllReservations() {
        List<Reservation> result = dao.getAllReservations();
        assertNotNull(result);
    }

    @Test
    void testUpdateReservationDates() {
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        boolean result = dao.updateReservationDates(1, checkIn, checkOut);
        assertFalse(result);
    }
}