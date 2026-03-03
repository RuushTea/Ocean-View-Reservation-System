package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StaffDAO {

    public Staff findByUserId(int userId) {
        String sql = "SELECT staffId, userId FROM staff WHERE userId = ? LIMIT 1";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Staff staff = new Staff();
                staff.setStaffId(rs.getInt("staffId"));
                staff.setUserId(rs.getInt("userId"));
                return staff;
            }
        } catch (Exception e) {
            System.out.println("StaffDAO.findByUserId failed: " + e.getMessage());
            return null;
        }
    }
}