package com.oceanview.reservation.service.facade;

import com.oceanview.reservation.model.Bill;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Reservation;
import com.oceanview.reservation.service.BillService;
import com.oceanview.reservation.service.ReservationService;

import java.sql.Date;
import java.util.List;

public class ReservationFacade implements ReservationFacadeInterface {
    private final ReservationService reservationService;
    private final BillService billService;

    public ReservationFacade(ReservationService reservationService, BillService billService) {
        this.reservationService = reservationService;
        this.billService = billService;
    }

    @Override
    public Reservation createReservation(Guest guest, int roomTypeId, Date checkIn, Date checkOut) {
        return reservationService.createReservation(guest, roomTypeId, checkIn, checkOut);
    }

    @Override
    public Reservation updateReservation(int reservationId, String guestName, String address, String contactNo, int roomtypeId, Date checkInDate, Date checkOutDate) {
        return reservationService.updateReservation(reservationId, guestName, address, contactNo, roomtypeId, checkInDate, checkOutDate);
    }

    @Override
    public Reservation searchReservation(int reservationId) {
        return reservationService.searchReservation(reservationId);
    }

    @Override
    public List<Reservation> searchByGuestContactNo(String contactNo) {
        return reservationService.searchByGuestContactNo(contactNo);
    }

    @Override
    public boolean cancelReservation(int reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    @Override
    public boolean toggleCancel(int reservationId) {
        return reservationService.toggleCancel(reservationId);
    }

    @Override
    public Bill generateBill(int reservationId) {
        return billService.generateBill(reservationId);
    }

    @Override
    public Bill getBillDetails(int reservationId) {
        return billService.getBill(reservationId);
    }
}
