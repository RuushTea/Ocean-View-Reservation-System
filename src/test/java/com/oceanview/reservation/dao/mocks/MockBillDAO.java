package com.oceanview.reservation.dao.mocks;

import com.oceanview.reservation.dao.BillDAO;
import com.oceanview.reservation.model.Bill;

public class MockBillDAO extends BillDAO {
    private Bill bill;
    private boolean insertSuccess;

    public void setBill(Bill b) { this.bill = b; }
    public void setInsertSuccess(boolean success) { this.insertSuccess = success; }

    @Override
    public boolean insertBill(int reservationId, int nights, double rate, double total) {
        return insertSuccess;
    }

    @Override
    public Bill findByReservationId(int reservationId) {
        return bill;
    }
}
