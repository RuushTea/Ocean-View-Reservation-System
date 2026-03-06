package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Room;
import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomDAOTest {

    private RoomDAO dao;

    @BeforeEach
    void setUp() {
        dao = new RoomDAO();
    }

    @Test
    void testFindAvailableRoom_NotFound() {
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Room result = dao.findAvailableRoom(99999, checkIn, checkOut);
        assertNull(result);
    }

    @Test
    void testFindByRoomId_NotFound() {
        Room result = dao.findByRoomId(99999);
        assertNull(result);
    }

    @Test
    void testFindByRoomType_NotFound() {
        List<Room> result = dao.findByRoomType(99999);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAvailableRoomForUpdate_InvalidRoomType() {
        Date checkIn = new Date(System.currentTimeMillis());
        Date checkOut = new Date(System.currentTimeMillis() + 86400000);

        Room result = dao.findAvailableRoomForUpdate(99999, checkIn, checkOut, 1);
        assertNull(result);
    }

    @Test
    void testFindByRoomType_NoResults() {
        List<Room> result = dao.findByRoomType(99999);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}