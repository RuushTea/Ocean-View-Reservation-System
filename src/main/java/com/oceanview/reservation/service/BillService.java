package com.oceanview.reservation.service;

import com.oceanview.reservation.model.Bill;

public interface BillService {
    public Bill generateBill(int reservationNo);
    public Bill getBill(int reservationNo);
}
