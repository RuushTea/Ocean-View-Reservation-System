package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BillDAO {

    public boolean insertBill(int reservationId, int nights, double ratePerNight, double total) {
        String sql =
                "INSERT INTO bill (reservationId, nights, ratePerNight, totalAmount) " +
                        "VALUES (?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE nights=?, ratePerNight=?, totalAmount=?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ps.setInt(2, nights);
            ps.setDouble(3, ratePerNight);
            ps.setDouble(4, total);

            ps.setInt(5, nights);
            ps.setDouble(6, ratePerNight);
            ps.setDouble(7, total);

            return ps.executeUpdate() >= 1;

        } catch (Exception e) {
            return false;
        }
    }

    public Bill findByReservationId(int reservationId) {
        String sql = "SELECT * FROM bill WHERE reservationId=?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Bill b = new Bill();
                b.setBillId(rs.getInt("billId"));
                b.setReservationNo(rs.getInt("reservationId"));
                b.setNights(rs.getInt("nights"));
                b.setRatePerNight(rs.getDouble("ratePerNight"));
                b.setTotalAmount(rs.getDouble("totalAmount"));
                return b;
            }
        } catch (Exception e) {
            System.out.println("Bill find failed: " + e.getMessage());
            return null;
        }
    }

    public void deleteBill(int billId) {
        String sql = "DELETE FROM bill WHERE billId = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, billId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to delete bill: " + e.getMessage());
        }
    }
}