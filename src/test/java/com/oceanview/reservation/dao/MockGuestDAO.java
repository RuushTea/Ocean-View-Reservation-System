package com.oceanview.reservation.dao;

import com.oceanview.reservation.model.Guest;

// Simple mock implementations
public class MockGuestDAO extends GuestDAO {
    @Override
    public Guest findByContactNo(String contactNo) { return null; }

    @Override
    public int insertGuestAndReturnId(Guest guest) { return 1; }

    @Override
    public Guest findByGuestId(int guestId) { return new Guest("John", "123 St", "123"); }

    @Override
    public boolean updateGuest(Guest guest) { return true; }
}