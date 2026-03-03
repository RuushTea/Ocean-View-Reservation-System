package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {

    public Admin findByUserId(int userId) {
        String sql = "SELECT adminId, userId FROM admin WHERE userId = ? LIMIT 1";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("adminId"));
                admin.setUserId(rs.getInt("userId"));
                return admin;
            }
        } catch (Exception e) {
            System.out.println("AdminDAO.findByUserId failed: " + e.getMessage());
            return null;
        }
    }
}