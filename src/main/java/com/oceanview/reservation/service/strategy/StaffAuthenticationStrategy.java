package com.oceanview.reservation.service.strategy;

import com.oceanview.reservation.dao.StaffDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.service.factory.UserFactory;
import com.oceanview.reservation.service.factory.UserFactoryImpl;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;

public class StaffAuthenticationStrategy implements AuthenticationStrategy {
    private final SystemUserDAO systemUserDAO = new SystemUserDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final UserFactory userFactory = new UserFactoryImpl();

    @Override
    public Object authenticate(String username, String password) {
        SystemUser base = systemUserDAO.findByUsername(username);
        if (base == null || !base.isActive() || !password.equals(base.getPassword())) {
            return null;
        }

        Staff staff = staffDAO.findByUserId(base.getUserId());
        if (staff == null) return null;

        return userFactory.createStaff(base);
    }
}
