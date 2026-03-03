package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.SystemUser;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SystemUserDAO {

    // Create a full System User
    public int createSystemUser(String username, String password, String fullname, boolean active) {
        String sql = "INSERT INTO system_user (userName, password, fullName, active) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullname);
            ps.setBoolean(4, active);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                return -1;
            } catch (SQLException e) {
                System.out.println("Failed to create system user: " + e.getMessage());
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    // Check if a System User is active
    public boolean isActive(int userId) {
        String sql = "SELECT active FROM system_user WHERE userId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBoolean("active");
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("isActive failed: " + e.getMessage(), e);
        }
    }

    // Set active status for a System User
    public void setActive(int userId, boolean active) {
        String sql = "UPDATE system_user SET active = ? WHERE userId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, active);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("setActive failed: " + e.getMessage(), e);
        }
    }

        public SystemUser findByUsername(String username) {
        String sql = "SELECT userId, userName, password, fullName, active " +
                "FROM system_user WHERE userName = ?";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    SystemUser user = new SystemUser();
                    user.setUserId(rs.getInt("userId"));
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                    user.setFullName(rs.getString("fullName"));
                    user.setActive(rs.getBoolean("active"));
                    return user;
                }
            }
        } catch (Exception e){
            System.out.println("Find user by username failed: " + e.getMessage());
        }
        return null;
    }

    public SystemUser findStaffUserByUserId(int userId) {
        String sql =
                "SELECT su.userId, su.username, su.password, su.fullName, su.active " +
                        "FROM system_user su " +
                        "JOIN staff s ON s.userId = su.userId " +
                        "WHERE su.userId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SystemUser u = new SystemUser();
                    u.setUserId(rs.getInt("userId"));
                    u.setUserName(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setFullName(rs.getString("fullName"));
                    u.setActive(rs.getBoolean("active"));
                    return u;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("findStaffUserByUserId failed: " + e.getMessage(), e);
        }
        return null;
    }

    public boolean updateStaffUser(int userId, String username, String password, String fullName) {
        StringBuilder sql = new StringBuilder("UPDATE system_user su SET ");
        List<Object> params = new ArrayList<>();
        
        if (username != null) {
            sql.append("su.username = ?, ");
            params.add(username);
        }
        
        if (password != null) {
            sql.append("su.password = ?, ");
            params.add(password);
        }
        
        if (fullName != null) {
            sql.append("su.fullName = ?, ");
            params.add(fullName);
        }
        
        // Remove trailing comma and space
        if (sql.toString().endsWith(", ")) {
            sql.setLength(sql.length() - 2);
        }
        
        sql.append(" WHERE su.userId = ? AND EXISTS (SELECT 1 FROM staff s WHERE s.userId = su.userId)");
        params.add(userId);

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("updateStaffUser failed: " + e.getMessage());
            return false;
        }
    }

    public Admin findAdminByUserId(int userId){
        String sql = "SELECT adminID, createdAt FROM admin WHERE userId = ?";

        try (Connection con = DBConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1, userId);

                try (ResultSet rs = ps.executeQuery()){
                    if (rs.next()){
                        Admin admin = new Admin();
                        admin.setAdminId(rs.getInt("adminId"));
                        admin.setCreatedAt(rs.getDate("createdAt"));
                        return admin;
                    }
                }
            } catch (Exception e) {
            System.out.println("Find Admin by UserID failed: " + e.getMessage());
        }

        return null;
    }

    public List<SystemUser> getAllStaffUsers() {
        String sql =
                "SELECT su.userId, su.userName, su.password, su.fullName, su.active " +
                        "FROM system_user su " +
                        "JOIN staff s ON s.userId = su.userId " +
                        "ORDER BY su.userId";

        List<SystemUser> staffUsers = new ArrayList<>();

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SystemUser u = new SystemUser();
                    u.setUserId(rs.getInt("userId"));
                    u.setUserName(rs.getString("userName"));
                    u.setPassword(rs.getString("password"));
                    u.setFullName(rs.getString("fullName"));
                    u.setActive(rs.getBoolean("active"));
                    staffUsers.add(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("getAllStaffUsers failed: " + e.getMessage(), e);
        }
        return staffUsers;
    }

    public boolean updateActive(int userId, boolean isActive){
        String sql = "UPDATE system_user SET active = ? WHERE userId = ?";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e){
            System.out.println("Failed to update user active status: " + e.getMessage());
            return false;
        }
    }

}
