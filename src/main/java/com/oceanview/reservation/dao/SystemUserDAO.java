package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemUserDAO {

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

    public Staff findStaffByUserId(int userId){
        String sql = "SELECT staffId, staffSince, assignedReservationNo " +
                "FROM staff WHERE userId = ?";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    Staff staff = new Staff();
                    staff.setStaffId(rs.getInt("staffId"));
                    staff.setStaffSince(rs.getDate("staffSince"));

                    //This can be null
                    int assigned = rs.getInt("assignedReservationNo");

                    if (rs.wasNull()) {
                        staff.setAssignedReservationNo(null);
                    }
                    else {
                        staff.setAssignedReservationNo(assigned);
                    }
                    return staff;
                }
            }
        } catch (Exception e){
            System.out.println("Find Staff by UserId failed: : " + e.getMessage());
        }
        return null;
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
