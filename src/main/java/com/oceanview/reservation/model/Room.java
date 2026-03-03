package com.oceanview.reservation.model;

public class Room {
    private int roomId;
    private int roomNo;
    private String status;
    private RoomType roomType;

    public Room() {}

    public Room(int roomId, int roomNo, String status, RoomType roomType) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
