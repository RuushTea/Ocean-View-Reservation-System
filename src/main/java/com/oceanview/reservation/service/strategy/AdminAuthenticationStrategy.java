package com.oceanview.reservation.service.strategy;

import com.oceanview.reservation.dao.AdminDAO;
import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.service.factory.UserFactory;
import com.oceanview.reservation.service.factory.UserFactoryImpl;
import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.SystemUser;

public class AdminAuthenticationStrategy implements AuthenticationStrategy {
    private final SystemUserDAO systemUserDAO = new SystemUserDAO();
    private final AdminDAO adminDAO = new AdminDAO();
    private final UserFactory userFactory = new UserFactoryImpl();

    @Override
    public Object authenticate(String username, String password) {
        SystemUser base = systemUserDAO.findByUsername(username);
        if (base == null || !base.isActive() || !password.equals(base.getPassword())) {
            return null;
        }

        Admin admin = adminDAO.findByUserId(base.getUserId());
        if (admin == null) return null;

        return userFactory.createAdmin(base);
    }
}
