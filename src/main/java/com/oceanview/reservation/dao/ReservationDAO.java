package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.model.Room;
import com.oceanview.reservation.model.RoomType;
import com.oceanview.reservation.util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservationDAO {

    private void setReservation(Reservation reservation, PreparedStatement ps) throws SQLException {
        ps.setInt(1, reservation.getGuest().getGuestId());
        ps.setInt(2, reservation.getRoom().getRoomId());
        ps.setDate(3, reservation.getCheckInDate());
        ps.setDate(4, reservation.getCheckOutDate());
        ps.setString(5, reservation.getStatus());

        ps.executeUpdate();
    }


    public void insertReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (guestId, roomId, checkInDate, checkOutDate, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setReservation(reservation, ps);
        } catch (Exception e) {
            System.out.println("Failed to insert reservation: " + e.getMessage());
        }
    }

    public boolean updateReservationDates(int reservationId, Date checkIn, Date checkOut) {
        String sql = "UPDATE reservation SET checkInDate = ?, checkOutDate = ? WHERE reservationId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, checkIn);
            ps.setDate(2, checkOut);
            ps.setInt(3, reservationId);

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            System.out.println("Failed to update reservation dates: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteReservation(int reservationId) {
        String sql = "DELETE FROM reservation WHERE reservationId = ?";
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reservationId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Failed to delete reservation: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRoom(int reservationNo, int roomId){
        String sql = "UPDATE reservation SET roomId = ? WHERE reservationId = ?";

        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, roomId);
            ps.setInt(2, reservationNo);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            System.out.println("Failed to update reservation room: " + e.getMessage());
            return false;
        }
    }
    public boolean updateStatus(int reservationid, String status){
        String sql = "UPDATE reservation SET status = ? WHERE reservationId = ?";

        //Update the status of the reservation
        try (Connection con = DBConnectionManager.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, status);
            ps.setInt(2, reservationid);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Failed to update reservation status: " + e.getMessage());
            return false;
        }
    }

    //return status by reservation ID
    public String getStatus(int reservationId) {
        String sql = "SELECT status FROM reservation WHERE reservationId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("status");
            }

        } catch (Exception e) {
            System.out.println("Failed to get reservation status: " + e.getMessage());
        }
        return null;
    }


    public int insertAndReturnReservationId(Reservation reservation) {
        String sql = "INSERT INTO reservation (guestId, roomId, checkInDate, checkOutDate, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setReservation(reservation, ps);

            //Return the new reservationID
            try (ResultSet keys = ps.getGeneratedKeys()){
                if (keys.next()){
                    return keys.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to insert reservation: " + e.getMessage());
        }

        return 0;
    }


    //Find reservation by reservation ID
    public Reservation findByReservationNo(int reservationId) {
        String sql = "SELECT res.reservationId, res.checkInDate, res.checkOutDate, res.status, " +
                "g.guestId, g.name, g.address, g.contactNo, " +
                "r.roomId, r.roomNumber, r.status AS roomStatus, " +
                "rt.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                "FROM reservation res " +
                "JOIN guest g ON res.guestId = g.guestId " +
                "JOIN room r ON res.roomId = r.roomId " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "WHERE res.reservationId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return buildReservationFromResultSet(rs);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Failed to find reservation by reservation ID: " + e.getMessage());
        }
        return null;
    }

    //Find reservation by guest contactNo
    public List<Reservation> findByContactNo(String contactNo) {
        String sql = "SELECT res.reservationId, res.checkInDate, res.checkOutDate, res.status, " +
                "g.guestId, g.name, g.address, g.contactNo, " +
                "r.roomId, r.roomNumber, r.status AS roomStatus, " +
                "rt.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                "FROM reservation res " +
                "JOIN guest g ON res.guestId = g.guestId " +
                "JOIN room r ON res.roomId = r.roomId " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "WHERE g.contactNo = ?";

        List<Reservation> reservationList = new ArrayList<>();

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, contactNo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservationList.add(buildReservationFromResultSet(rs));
            }
            return reservationList;

        } catch (Exception e) {
            System.out.println("Failed to find reservation by guest contactNo: " + e.getMessage());
        }
        return null;
    }

    public List<Reservation> findAllReservationsByGuestId(int guestId) {
        String sql = "SELECT res.reservationId, res.checkInDate, res.checkOutDate, res.status, " +
                "g.guestId, g.name, g.address, g.contactNo, " +
                "r.roomId, r.roomNumber, r.status AS roomStatus, " +
                "rt.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                "FROM reservation res " +
                "JOIN room r ON res.roomId = r.roomId " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "JOIN guest g ON res.guestId = g.guestId " +
                "WHERE g.guestId = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, guestId);

            try (ResultSet rs = ps.executeQuery()) {
                List<Reservation> reservationList = new ArrayList<>();
                while (rs.next()) {
                    reservationList.add(buildReservationFromResultSet(rs));
                }
                return reservationList;
            }
        } catch (Exception e) {
            System.out.println("Failed to find reservations by guest ID: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Reservation> getAllReservations() {
        String sql = "SELECT res.reservationId, res.checkInDate, res.checkOutDate, res.status, " +
                "g.guestId, g.name, g.address, g.contactNo, " +
                "r.roomId, r.roomNumber, r.status AS roomStatus, " +
                "rt.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                "FROM reservation res " +
                "JOIN room r ON res.roomId = r.roomId " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "JOIN guest g ON res.guestId = g.guestId " +
                "ORDER BY res.reservationId DESC";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                List<Reservation> reservationList = new ArrayList<>();
                while (rs.next()) {
                    reservationList.add(buildReservationFromResultSet(rs));
                }
                return reservationList;
            }
        } catch (Exception e) {
            System.out.println("Failed to get all reservations: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    //For all common reservation creations
    private Reservation buildReservationFromResultSet(ResultSet rs) throws SQLException {
        Guest guest = new Guest();
        guest.setGuestId(rs.getInt("guestId"));
        guest.setName(rs.getString("name"));
        guest.setAddress(rs.getString("address"));
        guest.setContactNo(rs.getString("contactNo"));

        RoomType rt = new RoomType(
                rs.getInt("roomTypeId"),
                rs.getString("roomTypeName"),
                rs.getDouble("ratePerNight")
        );

        Room room = new Room(
                rs.getInt("roomId"),
                rs.getInt("roomNumber"),
                rs.getString("roomStatus"),
                rt
        );

        return new Reservation(
                rs.getInt("reservationId"),
                guest,
                room,
                rs.getDate("checkInDate"),
                rs.getDate("checkOutDate"),
                rs.getString("status")
        );
    }
}


