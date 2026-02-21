package com.oceanview.reservation.model;

import java.sql.Date;

public class Staff extends SystemUser{
    private int staffId;
    private Date staffSince;
    private Integer assignedReservationNo;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public Date getStaffSince() {
        return staffSince;
    }

    public void setStaffSince(Date staffSince) {
        this.staffSince = staffSince;
    }

    public Integer getAssignedReservationNo() {
        return assignedReservationNo;
    }

    public void setAssignedReservationNo(Integer assignedReservationNo) {
        this.assignedReservationNo = assignedReservationNo;
    }
}
