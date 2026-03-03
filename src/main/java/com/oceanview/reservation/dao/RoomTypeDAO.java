package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.RoomType;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO {

    // Find all room types
    public List<RoomType> findAll(){
        List<RoomType> roomTypeList = new ArrayList<>();
        String sql = "SELECT * FROM room_type";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                roomTypeList.add(new RoomType(
                        rs.getInt("roomTypeId"),
                        rs.getString("roomTypeName"),
                        rs.getDouble("ratePerNight")
                ));
            }
        } catch (Exception e){
            System.out.println("Failed to find all rooms: " + e.getMessage());
        }
        return roomTypeList;
    }

    // Find room type by ID
    public RoomType findById(int id) {
        String sql = "SELECT roomTypeId, roomTypeName, ratePerNight FROM room_type WHERE roomTypeId = ?";
        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new RoomType(
                            rs.getInt("roomTypeId"),
                            rs.getString("roomTypeName"),
                            rs.getDouble("roomTypeRate")
                    );
                }
            }
        } catch (Exception e){
            System.out.println("Failed to find room type: " + e.getMessage());
        }
        return null;
    }

    // Create a new room type
    public void createRoomType(RoomType roomType){
        String sql = "INSERT INTO room_type (roomTypeName, ratePerNight) VALUES (?, ?)";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, roomType.getRoomTypeName());
            ps.setDouble(2, roomType.getRatePerNight());

            ps.executeUpdate();
        } catch (Exception e){
            System.out.println("Failed to create room type: " + e.getMessage());
        }

    }
}
