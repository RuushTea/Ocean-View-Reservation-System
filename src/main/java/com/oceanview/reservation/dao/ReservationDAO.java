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
    public void insert(Reservation reservation) {
        String sql = "INSERT INTO reservation (guestId, roomId, checkInDate, checkOutDate, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            setReservation(reservation, ps);
        } catch (Exception e) {
            System.out.println("Failed to insert reservation: " + e.getMessage());
        }
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

    private void setReservation(Reservation reservation, PreparedStatement ps) throws SQLException {
        ps.setInt(1, reservation.getGuest().getGuestId());
        ps.setInt(2, reservation.getRoom().getRoomId());
        ps.setDate(3, reservation.getCheckInDate());
        ps.setDate(4, reservation.getCheckOutDate());
        ps.setString(5, reservation.getStatus());

        ps.executeUpdate();
    }

    public Reservation findByReservationNo(int reservationId) {
        String sql = "SELECT res.reservationId, res.checkInDate, res.checkOutDate, res.status, " +
                "g.guestId, g.name, g.address, g.contactNo, " +
                "r.roomId, r.roomNumber, r.status AS roomStatus, " +
                "rt.roomTypeId, rt.roomTypeName, rt.ratePerNight " +
                "FROM reservation res " +
                "JOIN room r ON res.roomId = r.roomId " +
                "JOIN room_type rt ON r.roomTypeId = rt.roomTypeId " +
                "WHERE res.reservationNo = ?";

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

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
        } catch (Exception e) {
            System.out.println("Failed to find reservation by reservation ID: " + e.getMessage());
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

                    reservationList.add(new Reservation(
                            rs.getInt("reservationId"),
                            guest,
                            room,
                            rs.getDate("checkInDate"),
                            rs.getDate("checkOutDate"),
                            rs.getString("status")
                    ));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to find reservations by guest ID: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
