package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.MockBillDAO;
import com.oceanview.reservation.dao.MockReservationDAO;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.Room;
import com.oceanview.reservation.model.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class BillServiceImplTest {

    private BillServiceImpl service;
    private MockReservationDAO reservationDAO;
    private MockBillDAO billDAO;

    @BeforeEach
    void setUp() throws Exception {
        service = new BillServiceImpl();
        reservationDAO = new MockReservationDAO();
        billDAO = new MockBillDAO();

        // Inject mocks using reflection
        setField(service, "reservationDAO", reservationDAO);
        setField(service, "billDAO", billDAO);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testGenerateBill_Success() {
        RoomType roomType = new RoomType(1, "Suite", 150.0);
        Room room = new Room(1, 101, "AVAILABLE", roomType);
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 3); // 3 nights
        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");

        reservationDAO.setReservation(reservation);
        billDAO.setInsertSuccess(true);
        billDAO.setBill(new Bill(1, 3, 150.0));

        Bill result = service.generateBill(1);

        assertNotNull(result);
        assertEquals(3, result.getNights());
        assertEquals(150.0, result.getRatePerNight());
        assertEquals(450.0, result.getTotalAmount());
    }

    @Test
    void testGenerateBill_ReservationNotFound() {
        reservationDAO.setReservation(null);

        Bill result = service.generateBill(999);

        assertNull(result);
    }

    @Test
    void testGenerateBill_InsertFailed() {
        RoomType roomType = new RoomType(1, "Suite", 150.0);
        Room room = new Room(1, 101, "AVAILABLE", roomType);
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 2);
        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");

        reservationDAO.setReservation(reservation);
        billDAO.setInsertSuccess(false);

        Bill result = service.generateBill(1);

        assertNull(result);
    }

    @Test
    void testGenerateBill_InvalidNights() {
        RoomType roomType = new RoomType(1, "Suite", 150.0);
        Room room = new Room(1, 101, "AVAILABLE", roomType);
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis()); // Same day, 0 nights

        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");
        reservationDAO.setReservation(reservation);

        Bill result = service.generateBill(1);

        assertNull(result);
    }

    @Test
    void testGetBill_Success() {
        Bill mockBill = new Bill(1, 2, 200.0);
        billDAO.setBill(mockBill);

        Bill result = service.getBill(1);

        assertNotNull(result);
        assertEquals(2, result.getNights());
        assertEquals(200.0, result.getRatePerNight());
    }

    @Test
    void testGetBill_NotFound() {
        billDAO.setBill(null);

        Bill result = service.getBill(999);

        assertNull(result);
    }

    @Test
    void testGenerateBill_CalculatesCorrectTotal() {
        RoomType roomType = new RoomType(1, "Deluxe", 250.0);
        Room room = new Room(2, 202, "AVAILABLE", roomType);
        Guest guest = new Guest("Jane Smith", "456 Ave", "9876543210");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 5); // 5 nights
        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");

        reservationDAO.setReservation(reservation);
        billDAO.setInsertSuccess(true);
        billDAO.setBill(new Bill(1, 5, 250.0));

        Bill result = service.generateBill(1);

        assertNotNull(result);
        assertEquals(5, result.getNights());
        assertEquals(250.0, result.getRatePerNight());
        assertEquals(1250.0, result.getTotalAmount());
    }


}