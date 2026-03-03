package com.oceanview.reservation.factory;

import com.oceanview.reservation.model.Admin;
import com.oceanview.reservation.model.Guest;
import com.oceanview.reservation.model.Staff;
import com.oceanview.reservation.model.SystemUser;

public class UserFactoryImpl implements UserFactory {

    @Override
    public Admin createAdmin(SystemUser systemUser) {
        if (systemUser == null) {
            throw new IllegalArgumentException("SystemUser cannot be null");
        }
        
        Admin admin = new Admin();
        copySystemUserProperties(admin, systemUser);
        return admin;
    }

    @Override
    public Staff createStaff(SystemUser systemUser) {
        if (systemUser == null) {
            throw new IllegalArgumentException("SystemUser cannot be null");
        }
        
        Staff staff = new Staff();
        copySystemUserProperties(staff, systemUser);
        return staff;
    }

    @Override
    public Guest createGuest(Object guestData) {
        return new Guest();
    }

    @Override
    public SystemUser createSystemUser(String username, String password, String fullName, boolean active) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }
        
        SystemUser systemUser = new SystemUser();
        systemUser.setUserName(username.trim());
        systemUser.setPassword(password);
        systemUser.setFullName(fullName.trim());
        systemUser.setActive(active);
        
        return systemUser;
    }
    
    private void copySystemUserProperties(Object target, SystemUser source) {
        if (target instanceof Admin) {
            Admin admin = (Admin) target;
            admin.setUserId(source.getUserId());
            admin.setUserName(source.getUserName());
            admin.setPassword(source.getPassword());
            admin.setFullName(source.getFullName());
            admin.setActive(source.isActive());
        } else if (target instanceof Staff) {
            Staff staff = (Staff) target;
            staff.setUserId(source.getUserId());
            staff.setUserName(source.getUserName());
            staff.setPassword(source.getPassword());
            staff.setFullName(source.getFullName());
            staff.setActive(source.isActive());
        }
    }
}
