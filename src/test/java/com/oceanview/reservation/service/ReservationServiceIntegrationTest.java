package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.GuestDAO;
import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.dao.RoomDAO;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationServiceIntegrationTest {

    private ReservationServiceImpl service;
    private GuestDAO guestDAO;
    private ReservationDAO resDAO;
    private RoomDAO roomDAO;

    private final List<Integer> createdReservationIds = new ArrayList<>();
    private final List<Integer> createdGuestIds = new ArrayList<>();
    private int validRoomTypeId;


    @BeforeEach
    void setUp() {
        service = new ReservationServiceImpl();
        guestDAO = new GuestDAO();
        resDAO = new ReservationDAO();
        roomDAO = new RoomDAO();

        validRoomTypeId = roomDAO.getFirstAvailableRoomType();
        if (validRoomTypeId <= 0) {
            fail("No valid room types available");
        }

        createdReservationIds.clear();
        createdGuestIds.clear();
    }



    private Guest createTestGuest() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return new Guest(
                "TestGuest" + uniqueId,
                "Test Address",
                "999" + System.nanoTime() % 10000000
        );
    }

    private void trackGuest(Guest guest) {
        if (guest != null && guest.getGuestId() > 0) {
            createdGuestIds.add(guest.getGuestId());
        }
    }

    private void trackReservation(Reservation reservation) {
        if (reservation != null && reservation.getReservationId() > 0) {
            createdReservationIds.add(reservation.getReservationId());
        }
    }

    @AfterEach
    void tearDown() {
        for (int reservationId : createdReservationIds) {
            try {
                resDAO.deleteReservation(reservationId);
            } catch (Exception e) {
                System.out.println("Failed to delete reservation: " + e.getMessage());
            }
        }

        for (int guestId : createdGuestIds) {
            try {
                guestDAO.deleteGuest(guestId);
            } catch (Exception e) {
                System.out.println("Failed to delete guest: " + e.getMessage());
            }
        }

        createdReservationIds.clear();
        createdGuestIds.clear();
    }

    @Test
    void testCreateReservation_Success() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 2);

        Reservation result = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);

        assertNotNull(result);
        assertEquals("CONFIRMED", result.getStatus());
        assertEquals(guest.getName(), result.getGuest().getName());
        assertTrue(result.getReservationId() > 0);

        trackReservation(result);
        trackGuest(result.getGuest());
    }

    @Test
    void testToggleCancel_Success() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 2);

        Reservation tempReservation = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNotNull(tempReservation);
        assertEquals("CONFIRMED", tempReservation.getStatus());

        boolean result = service.toggleCancel(tempReservation.getReservationId());
        assertTrue(result);

        Reservation toggledReservation = resDAO.findByReservationNo(tempReservation.getReservationId());
        assertNotNull(toggledReservation);
        assertEquals("CANCELLED", toggledReservation.getStatus());

        trackReservation(tempReservation);
        trackGuest(tempReservation.getGuest());
    }

    @Test
    void testSearchReservation_Success() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Reservation created = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNotNull(created);

        Reservation found = service.searchReservation(created.getReservationId());
        assertNotNull(found);
        assertEquals(created.getReservationId(), found.getReservationId());

        trackReservation(created);
        trackGuest(created.getGuest());
    }

    @Test
    void testSearchByGuestContactNo_Success() {
        Guest guest = createTestGuest();
        String contactNo = guest.getContactNo();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Reservation created = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNotNull(created);

        List<Reservation> found = service.searchByGuestContactNo(contactNo);
        assertNotNull(found);
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(r -> r.getReservationId() == created.getReservationId()));

        trackReservation(created);
        trackGuest(created.getGuest());
    }

    @Test
    void testGenerateBill_Success() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000 * 3);

        Reservation reservation = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNotNull(reservation);

        Bill bill = service.generateBill(reservation.getReservationId());
        assertNotNull(bill);
        assertEquals(reservation.getReservationId(), bill.getReservationNo());
        assertTrue(bill.getTotalAmount() > 0);

        trackReservation(reservation);
        trackGuest(reservation.getGuest());
    }

    @Test
    void testCancelReservation_Success() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Reservation reservation = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNotNull(reservation);

        boolean result = service.cancelReservation(reservation.getReservationId());
        assertTrue(result);

        Reservation cancelled = resDAO.findByReservationNo(reservation.getReservationId());
        assertNotNull(cancelled);
        assertEquals("CANCELLED", cancelled.getStatus());

        trackReservation(reservation);
        trackGuest(reservation.getGuest());
    }

    @Test
    void testCreateReservation_InvalidCheckInDate() {
        Guest guest = createTestGuest();
        Reservation result = service.createReservation(guest, validRoomTypeId, null,
                new Date(System.currentTimeMillis() + 86400000));
        assertNull(result);
    }

    @Test
    void testCreateReservation_CheckOutBeforeCheckIn() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis() + 86400000);
        Date checkOut = new Date(System.currentTimeMillis());
        Reservation result = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNull(result);
    }

    @Test
    void testCreateReservation_InvalidRoomType() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);
        Reservation result = service.createReservation(guest, 99999, checkIn, checkOut);
        assertNull(result);
    }

    @Test
    void testCreateReservation_SameDayCheckInAndOut() {
        Guest guest = createTestGuest();
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis());
        Reservation result = service.createReservation(guest, validRoomTypeId, checkIn, checkOut);
        assertNull(result);
    }

    @Test
    void testSearchReservation_NotFound() {
        Reservation result = service.searchReservation(99999);
        assertNull(result);
    }

    @Test
    void testSearchByGuestContactNo_Empty() {
        List<Reservation> result = service.searchByGuestContactNo("9999999999");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCancelReservation_InvalidReservationId() {
        boolean result = service.cancelReservation(99999);
        assertFalse(result);
    }

    @Test
    void testToggleCancel_InvalidReservationId() {
        boolean result = service.toggleCancel(99999);
        assertFalse(result);
    }

    @Test
    void testGenerateBill_ReservationNotFound() {
        Bill bill = service.generateBill(99999);
        assertNull(bill);
    }
}
