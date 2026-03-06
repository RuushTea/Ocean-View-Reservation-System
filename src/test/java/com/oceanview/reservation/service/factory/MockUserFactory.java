package com.oceanview.reservation.service.factory;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;

public class MockUserFactory implements UserFactory {

    @Override
    public Admin createAdmin(SystemUser systemUser) {
        return new Admin();
    }

    @Override
    public Staff createStaff(SystemUser systemUser) {
        return new Staff();
    }

    @Override
    public Guest createGuest(Object guestData) {
        return new Guest();
    }

    @Override
    public SystemUser createSystemUser(String u, String p, String f, boolean a) {
        return new SystemUser(1, u, p, f, a);
    }
}