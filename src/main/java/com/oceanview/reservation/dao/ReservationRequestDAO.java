package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.ReservationRequest;
import com.oceanview.reservation.model.ReservationRequestStatus;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRequestDAO {

    public int createReservationRequest(ReservationRequest request) {
        String sql = "INSERT INTO reservation_requests (guest_name, address, contact_no, room_type_id, check_in_date, check_out_date, status, requested_by_user_id, request_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, request.getGuestName());
            ps.setString(2, request.getAddress());
            ps.setString(3, request.getContactNo());
            ps.setInt(4, request.getRoomTypeId());
            ps.setDate(5, request.getCheckInDate());
            ps.setDate(6, request.getCheckOutDate());
            ps.setString(7, request.getStatus().name());
            ps.setInt(8, request.getRequestedByUserId());
            ps.setDate(9, request.getRequestDate());

            int result = ps.executeUpdate();
            if (result == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("createReservationRequest failed: " + e.getMessage());
        }
        return -1;
    }

    public ReservationRequest findById(int requestId) {
        String sql = "SELECT * FROM reservation_requests WHERE request_id = ?";
        
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReservationRequest(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("findById failed: " + e.getMessage());
        }
        return null;
    }

    public List<ReservationRequest> findByStatus(ReservationRequestStatus status) {
        String sql = "SELECT * FROM reservation_requests WHERE status = ? ORDER BY request_date DESC";
        List<ReservationRequest> requests = new ArrayList<>();
        
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapResultSetToReservationRequest(rs));
                }
            }
        } catch (Exception e) {
            System.out.println("findByStatus failed: " + e.getMessage());
        }
        return requests;
    }

    public boolean updateStatus(int requestId, ReservationRequestStatus status, int approvedByUserId, String rejectionReason) {
        String sql = "UPDATE reservation_requests SET status = ?, approved_by_user_id = ?, rejection_reason = ?, processed_date = ? WHERE request_id = ?";
        
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, approvedByUserId);
            ps.setString(3, rejectionReason);
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.setInt(5, requestId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("updateStatus failed: " + e.getMessage());
            return false;
        }
    }

    public List<ReservationRequest> findAll() {
        String sql = "SELECT * FROM reservation_requests ORDER BY request_date DESC";
        List<ReservationRequest> requests = new ArrayList<>();
        
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                requests.add(mapResultSetToReservationRequest(rs));
            }
        } catch (Exception e) {
            System.out.println("findAll failed: " + e.getMessage());
        }
        return requests;
    }

    private ReservationRequest mapResultSetToReservationRequest(ResultSet rs) throws SQLException {
        ReservationRequest request = new ReservationRequest();
        request.setRequestId(rs.getInt("request_id"));
        request.setGuestName(rs.getString("guest_name"));
        request.setAddress(rs.getString("address"));
        request.setContactNo(rs.getString("contact_no"));
        request.setRoomTypeId(rs.getInt("room_type_id"));
        request.setCheckInDate(rs.getDate("check_in_date"));
        request.setCheckOutDate(rs.getDate("check_out_date"));
        request.setStatus(ReservationRequestStatus.valueOf(rs.getString("status")));
        request.setRequestedByUserId(rs.getInt("requested_by_user_id"));
        request.setApprovedByUserId(rs.getInt("approved_by_user_id"));
        request.setRejectionReason(rs.getString("rejection_reason"));
        request.setRequestDate(rs.getDate("request_date"));
        request.setProcessedDate(rs.getDate("processed_date"));
        return request;
    }
}
