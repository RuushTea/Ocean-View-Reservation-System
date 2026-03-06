package com.oceanview.reservation.dao.mocks;

import com.oceanview.reservation.dao.StaffDAO;

public class MockStaffDAO extends StaffDAO {
    public boolean ok;
    @Override
    public void insertStaff(int id) {}
    @Override
    public void deleteStaff(int id) {}
}