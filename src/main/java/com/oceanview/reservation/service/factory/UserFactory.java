package com.oceanview.reservation.service.factory;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;

public interface UserFactory {
    Admin createAdmin(SystemUser systemUser);
    Staff createStaff(SystemUser systemUser);
    Guest createGuest(Object guestData);
    SystemUser createSystemUser(String username, String password, String fullName, boolean active);
}
