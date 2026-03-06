package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillDAOTest {

    private BillDAO dao;

    @BeforeEach
    void setUp() {
        dao = new BillDAO();
    }

    @Test
    void testFindByReservationId_NotFound() {
        Bill bill = dao.findByReservationId(99999);
        assertNull(bill);
    }

    @Test
    void testInsertBill_InvalidReservationId() {
        // Insert with non-existent reservation ID should fail or succeed based on FK constraint
        boolean result = dao.insertBill(999999999, 3, 150.0, 450.0);
        // DB will reject if FK constraint exists
        assertFalse(result);
    }
}