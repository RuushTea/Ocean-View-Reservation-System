package com.oceanview.reservation.dao.mocks;

import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.model.Reservation;

public class MockReservationDAO extends ReservationDAO {
    private Reservation reservation;
    private String status = "CONFIRMED";

    public void setReservation(Reservation res) { this.reservation = res; }
    public void setStatus(String s) { this.status = s; }

    @Override
    public int insertAndReturnReservationId(Reservation reservation) { return 1; }

    @Override
    public Reservation findByReservationNo(int reservationId) { return reservation; }

    @Override
    public String getStatus(int reservationId) { return status; }

    @Override
    public boolean updateStatus(int reservationId, String status) {
        this.status = status;
        return true;
    }
}