package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.BillDAO;
import com.oceanview.reservation.model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillServiceIntegrationTest {

    private BillServiceImpl service;
    private BillDAO billDAO;

    @BeforeEach
    void setUp() {
        service = new BillServiceImpl();
        billDAO = new BillDAO();
    }

    // Failure cases without test data retention

    @Test
    void testGenerateBill_ReservationNotFound() {
        Bill result = service.generateBill(99999);
        assertNull(result);
    }

    @Test
    void testGenerateBill_InvalidReservationId() {
        Bill result = service.generateBill(-1);
        assertNull(result);
    }

    @Test
    void testGetBill_NotFound() {
        Bill result = service.getBill(99999);
        assertNull(result);
    }

    @Test
    void testGetBill_InvalidId() {
        Bill result = service.getBill(0);
        assertNull(result);
    }

    @Test
    void testGenerateBill_ZeroReservationId() {
        Bill result = service.generateBill(0);
        assertNull(result);
    }

    @Test
    void testBillDAOFindByReservationId_NotExists() {
        Bill bill = billDAO.findByReservationId(99999);
        assertNull(bill);
    }

    @Test
    void testBillDAOInsertBill_InvalidReservationId() {
        boolean result = billDAO.insertBill(99999, 3, 150.0, 450.0);
        assertFalse(result);
    }

    @Test
    void testBillDAOInsertBill_NegativeNights() {
        boolean result = billDAO.insertBill(99999, -5, 150.0, -750.0);
        assertFalse(result);
    }

    @Test
    void testBillDAOFindByReservationId_InvalidId() {
        Bill bill = billDAO.findByReservationId(-1);
        assertNull(bill);
    }
}