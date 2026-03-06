package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Room;
import com.oceanview.reservation.model.RoomType;

import java.sql.Date;

public class MockRoomDAO extends RoomDAO {
    private Room availableRoom = new Room(1, 101, "AVAILABLE", new RoomType(1, "Suite", 150.0));

    public void setAvailableRoom(Room room) {
        this.availableRoom = room;
    }

    @Override
    public Room findAvailableRoom(int roomTypeId, Date checkIn, Date checkOut) {
        return availableRoom;
    }

    @Override
    public Room findAvailableRoomForUpdate(int roomTypeId, Date checkIn, Date checkOut, int reservationId) {
        return new Room(1, 101, "AVAILABLE", new RoomType(1, "Suite", 150.0));
    }
}