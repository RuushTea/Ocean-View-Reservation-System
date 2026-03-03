package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public void insertStaff(int userId){
        String sql = "INSERT INTO staff (userId) VALUES (?)";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("insertStaff failed: " + e.getMessage(), e);
        }
    }

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



    public List<Staff> getAllStaff() {
        String sql =
                "SELECT s.staffId, su.userId, su.username, su.password, su.fullName, su.active " +
                        "FROM staff s " +
                        "JOIN system_user su ON s.userId = su.userId " +
                        "ORDER BY s.staffId DESC";

        List<Staff> list = new ArrayList<>();

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Staff st = new Staff();
                st.setStaffId(rs.getInt("staffId"));

                st.setUserId(rs.getInt("userId"));
                st.setUserName(rs.getString("username"));
                st.setPassword(rs.getString("password"));
                st.setFullName(rs.getString("fullName"));
                st.setActive(rs.getBoolean("active"));

                list.add(st);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("findAllStaffWithUser failed: " + e.getMessage(), e);
        }
    }
}