package com.oceanview.reservation.dao.mocks;

import com.oceanview.reservation.dao.SystemUserDAO;
import com.oceanview.reservation.model.SystemUser;

import java.util.List;

public class MockUserDAO extends SystemUserDAO {
    public List<SystemUser> staff;
    public SystemUser user;
    public int createId;
    public boolean ok;

    @Override
    public java.util.List<SystemUser> getAllStaffUsers() { return staff; }
    @Override
    public SystemUser findStaffUserByUserId(int id) { return user; }
    @Override
    public SystemUser findByUsername(String u) { return user; }
    @Override
    public int createSystemUser(String u, String p, String f, boolean a) { return createId; }
    @Override
    public boolean updateStaffUser(int id, String u, String p, String f) { return true; }
    @Override
    public boolean updateActive(int id, boolean a) { return ok; }
    @Override
    public boolean deleteUser(int id) { return ok; }
}