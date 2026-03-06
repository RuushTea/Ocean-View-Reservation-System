package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.*;
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

class ReservationServiceImplTest {

    private ReservationServiceImpl service;
    private MockGuestDAO guestDAO;
    private MockRoomDAO roomDAO;
    private MockReservationDAO reservationDAO;

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @BeforeEach
    void setUp() throws Exception {
        service = new ReservationServiceImpl();
        guestDAO = new MockGuestDAO();
        roomDAO = new MockRoomDAO();
        reservationDAO = new MockReservationDAO();

        // Inject mock DAOs
        setField(service, "guestDAO", guestDAO);
        setField(service, "roomDAO", roomDAO);
        setField(service, "reservationDAO", reservationDAO);
    }

    @Test
    void testCreateReservation_Success() {
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Reservation result = service.createReservation(guest, 1, checkIn, checkOut);

        assertNotNull(result);
        assertEquals("CONFIRMED", result.getStatus());
    }

    @Test
    void testCreateReservation_InvalidDates() {
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() - 86400000);

        Reservation result = service.createReservation(guest, 1, checkIn, checkOut);

        assertNull(result);
    }

    @Test
    void testCreateReservation_NoAvailableRoom() {
        roomDAO.setAvailableRoom(null);
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Reservation result = service.createReservation(guest, 1, checkIn, checkOut);

        assertNull(result);
    }

    @Test
    void testGenerateBill_Success() {
        RoomType roomType = new RoomType(1, "Suite", 150.0);
        Room room = new Room(1, 101, "AVAILABLE", roomType);
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 3);
        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");

        reservationDAO.setReservation(reservation);

        Bill bill = service.generateBill(1);

        assertNotNull(bill);
        assertEquals(3, bill.getNights());
        assertEquals(150.0, bill.getRatePerNight());
    }

    @Test
    void testGenerateBill_ReservationNotFound() {
        reservationDAO.setReservation(null);

        Bill bill = service.generateBill(999);

        assertNull(bill);
    }

    @Test
    void testToggleCancel_ConfirmedToCancelled() {
        reservationDAO.setStatus("CONFIRMED");
        boolean result = service.toggleCancel(1);

        assertTrue(result);
        assertEquals("CANCELLED", reservationDAO.getStatus(1));
    }

    @Test
    void testToggleCancel_CancelledToConfirmed() {
        reservationDAO.setStatus("CANCELLED");
        boolean result = service.toggleCancel(1);

        assertTrue(result);
        assertEquals("CONFIRMED", reservationDAO.getStatus(1));
    }

    @Test
    void testSearchReservation_Found() {
        RoomType roomType = new RoomType(1, "Suite", 150.0);
        Room room = new Room(1, 101, "AVAILABLE", roomType);
        Guest guest = new Guest("TestUser", "Test Address", "0123456789");
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);
        Reservation reservation = new Reservation(guest, room, checkIn, checkOut, "CONFIRMED");
        reservation.setReservationId(1);

        reservationDAO.setReservation(reservation);

        Reservation result = service.searchReservation(1);

        assertNotNull(result);
        assertEquals(guest.getGuestId(), result.getGuest().getGuestId());
    }
}