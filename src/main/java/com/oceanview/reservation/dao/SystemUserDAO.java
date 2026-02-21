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

    //Find Staff with their Username
    public Staff findStaffByUsername(String username){
        String sql = "SELECT su.userName, su.password, su.fullName, su.active, " +
                "s.staffId, s.staffSince, s.assignedReservationNo " +
                "FROM system_user su " +
                "JOIN staff s ON su.userId = s.userId " +
                "WHERE su.username = ? AND su.active = 1";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()){

                if (rs.next()){
                    Staff staff = new Staff();
                    staff.setUserName(rs.getString("username"));
                    staff.setPassword(rs.getString("password"));
                    staff.setFullName(rs.getString("fullName"));
                    staff.setActive(rs.getBoolean("active"));
                    staff.setStaffId(rs.getInt("staffId"));
                    staff.setStaffSince(rs.getDate("staffSince"));
                    staff.setAssignedReservationNo((Integer) rs.getObject("assignedReservationNo"));
                    return staff;
                }
            } catch (Exception e){
                System.out.println("Error finding staff by username " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Admin findAdminByUsername(String username){
        String sql = "SELECT su.userName, su.password, su.fullName, su.active, " +
                "a.adminId, a.createdAt " +
                "FROM system_user su " +
                "JOIN admin a ON su.userId = a.userId " +
                "WHERE su.userName = ? AND su.active = 1";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    Admin admin = new Admin();
                    admin.setUserName(rs.getString("userName"));
                    admin.setPassword(rs.getString("password"));
                    admin.setFullName(rs.getString("fullName"));
                    admin.setActive(rs.getInt("active") == 1);
                    admin.setAdminId(rs.getInt("adminId"));
                    admin.setCreatedAt(rs.getDate("createdAt"));
                    return admin;
                }
            } catch (Exception e){
                System.out.println("Error finding admin by username: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
