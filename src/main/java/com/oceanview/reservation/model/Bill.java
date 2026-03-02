package com.oceanview.reservation.model;

import java.sql.Timestamp;

public class Bill {
    private int billId;
    private int reservationNo;
    private int nights;
    private double ratePerNight;
    private double totalAmount;

    public Bill(){
    }

    public Bill(int reservationNo, int nights, double ratePerNight) {
        this.reservationNo = reservationNo;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = nights * ratePerNight;
    }

    public Bill(int billId, int reservationNo, int nights, double ratePerNight, double totalAmount) {
        this.billId = billId;
        this.reservationNo = reservationNo;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = totalAmount;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getReservationNo() {
        return reservationNo;
    }

    public void setReservationNo(int reservationNo) {
        this.reservationNo = reservationNo;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(double ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
