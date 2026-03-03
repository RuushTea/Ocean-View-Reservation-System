package com.oceanview.reservation.service;

import com.oceanview.reservation.dao.BillDAO;
import com.oceanview.reservation.dao.ReservationDAO;
import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Reservation;

public class BillServiceImpl implements BillService{

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final BillDAO billDAO = new BillDAO();

    @Override
    public Bill generateBill(int reservationId) {

        Reservation res = reservationDAO.findByReservationNo(reservationId);
        if (res == null) return null;

        long diffMs = res.getCheckOutDate().getTime() - res.getCheckInDate().getTime();
        int nights = (int) (diffMs / (1000L * 60 * 60 * 24));

        if (nights <= 0) return null;

        double rate = res.getRoom().getRoomType().getRatePerNight();
        double total = nights * rate;

        boolean ok = billDAO.insertBill(reservationId, nights, rate, total);
        if (!ok) return null;

        return billDAO.findByReservationId(reservationId);
    }

    @Override
    public Bill getBill(int reservationId) {
        return billDAO.findByReservationId(reservationId);
    }
}
