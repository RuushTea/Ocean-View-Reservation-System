package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.*;

public class GuestDAO {

    public Guest findByGuestId(int guestId){
        String sql = "select * from guest where guestId = ?";
        try (Connection con = DBConnectionManager.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, guestId);
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

    public int insertGuestAndReturnId(Guest guest) throws SQLException{
        String sql = "INSERT INTO guest (name, address, contactNo) VALUES (?, ?, ?)";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, guest.getName());
            ps.setString(2, guest.getAddress());
            ps.setString(3, guest.getContactNo());

            int affected = ps.executeUpdate();
            if (affected == 0){
                throw new SQLException("Failed to create a new guest");
            }

            try (ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    int guestId = rs.getInt(1);
                    return guestId;
                }
            }
        }
        return guest.getGuestId();
    }

    public Guest findByContactNo(String contactNo) throws SQLException{
        String sql = "select * from guest where contactNo = ?";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, contactNo);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Guest g = new Guest();
                    g.setGuestId(rs.getInt("guestId"));
                    g.setName(rs.getString("name"));
                    g.setAddress(rs.getString("address"));
                    g.setContactNo(rs.getString("contactNo"));
                    return g;
                }
            }

        }
        return null;
    }

    public Guest findByReservationNoAndContactNo(int reservationNo, String contactNo) throws SQLException{
        String sql = "SELECT g.* FROM reservation r JOIN guest g ON r.guestId = g.guestId WHERE r.reservationNo = ? AND g.contactNo = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationNo);
            ps.setString(2, contactNo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Guest g = new Guest();
                    g.setGuestId(rs.getInt("guestId"));
                    g.setName(rs.getString("name"));
                    g.setAddress(rs.getString("address"));
                    g.setContactNo(rs.getString("contactNo"));
                    return g;
                }
            }
        } catch (Exception e){
            System.out.println("Failed to find guest by reservation ID and contact number: " + e.getMessage());
        }
        return null;
    }
}
