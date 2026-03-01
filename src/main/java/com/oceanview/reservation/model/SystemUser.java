package com.oceanview.reservation.model;

public class SystemUser {
    private int userId;
    private String userName;
    private String password;
    private String fullName;
    private boolean active;

    public SystemUser() {}

    public SystemUser(int userId, String userName, String password, String fullName, boolean active) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
