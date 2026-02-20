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

                Guest g = new Guest();
                g.setGuestId(rs.getInt("guestId"));
                g.setName(rs.getString("name"));
                g.setAddress(rs.getString("address"));
                g.setContactNo(rs.getString("contactNo"));
                return g;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertGuest(Guest guest) throws  SQLException{
        String sql = "INSERT INTO guest (name, address, contactNo) VALUES (?, ?, ?)";
        try (Connection con = DBConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, guest.getName());
            ps.setString(2, guest.getAddress());
            ps.setString(3, guest.getContactNo());
            ps.executeUpdate();
        }
    }
}
