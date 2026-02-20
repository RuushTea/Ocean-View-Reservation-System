package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestDAO {
    public Guest findByGuestId(String guestId){
        String sql = "select * from guest where guestId = ?";
        try (Connection con = DBConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, guestId);
            try (ResultSet rs = ps.executeQuery()){
                if (!rs.next()) return null;

                Guest g = new Guest(
                        rs.getInt("guestId"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contactNo")
                );
                return g;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
