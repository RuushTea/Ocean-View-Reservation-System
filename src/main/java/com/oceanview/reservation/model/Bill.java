package com.oceanview.reservation.model;

public class Bill {
    private final int reservationNo;
    private final int nights;
    private final double ratePerNight;
    private final double totalAmount;

    public Bill(int reservationNo, int nights, double ratePerNight) {
        this.reservationNo = reservationNo;
        this.nights = nights;
        this.ratePerNight = ratePerNight;
        this.totalAmount = nights * ratePerNight;
    }

    public int getReservationNo() {
        return reservationNo;
    }

    public int getNights() {
        return nights;
    }

    public double getRatePerNight() {
        return ratePerNight;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
