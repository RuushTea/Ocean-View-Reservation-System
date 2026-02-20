package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Room;
import com.oceanview.reservation.model.RoomType;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public Room findAvailableRoom(int roomTypeId, Date checkInDate, Date checkOutDate){

        String sql = "SELECT r.roomId, r.roomNumber, r.status, " +
                        "rt.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                        "FROM room r " +
                        "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                        "WHERE r.roomTypeId = ? " +
                        "AND r.status = 'AVAILABLE' " +
                        "AND r.roomId NOT IN ( " +
                        "   SELECT res.roomId " +
                        "   FROM reservation res " +
                        "   WHERE res.status = 'CONFIRMED' " +
                        "   AND (? < res.checkOutDate AND ? > res.checkInDate) " +
                        ") " +
                        "LIMIT 1";


        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, roomTypeId);
            ps.setDate(2, checkInDate);
            ps.setDate(3, checkOutDate);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    RoomType rt = new RoomType(
                            rs.getInt("roomTypeId"),
                            rs.getString("roomTypeName"),
                            rs.getDouble("ratePerNight")
                    );
                    return new Room(
                            rs.getInt("roomId"),
                            rs.getInt("roomNumber"),
                            rs.getString("status"),
                            rt
                    );
                }
            }
        } catch (Exception e){
            System.out.println("Failed to find available room: " + e.getMessage());
        }
        return null;
    }
    public List<Room> findByRoomType(int roomTypeId){
        String sql = "SELECT r.roomId, r.roomNumber, r.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                "FROM room r " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "WHERE r.roomTypeId = ? AND r.status = 'AVAILABLE'";
        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, roomTypeId);
            try (ResultSet rs = ps.executeQuery()){
                List<Room> roomList = new ArrayList<>();
                while (rs.next()){
                    RoomType rt = new RoomType(
                            rs.getInt("roomTypeId"),
                            rs.getString("roomTypeName"),
                            rs.getDouble("roomTypeRate")
                    );
                    roomList.add(new Room(
                            rs.getInt("roomId"),
                            rs.getInt("roomNumber"),
                            rs.getString("status"),
                            rt
                    ));
                }
                return roomList;
            }
        } catch (Exception e){
            System.out.println("Failed to find rooms by room type: " + e.getMessage());
        }
        return null;

    }

    public Room findByRoomId(int roomId){
        String sql = "SELECT r.roomId, r.roomNumber, r.roomTypeId, rt.roomTypeName, rt.roomTypeRate " +
                "FROM room r " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "WHERE r.roomId = ?";
        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    RoomType rt = new RoomType(
                            rs.getInt("roomTypeId"),
                            rs.getString("roomTypeName"),
                            rs.getDouble("roomTypeRate")
                    );
                    return new Room(
                            rs.getInt("roomId"),
                            rs.getInt("roomNumber"),
                            rs.getString("status"),
                            rt
                    );
                }
            }
        } catch (Exception e){
            System.out.println("Failed to find room by ID: " + e.getMessage());
        }
        return null;
    }


}
