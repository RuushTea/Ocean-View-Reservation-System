package com.oceanview.reservation.model;

import java.sql.Date;

public class ReservationRequest {
    private int requestId;
    private String guestName;
    private String address;
    private String contactNo;
    private int roomTypeId;
    private Date checkInDate;
    private Date checkOutDate;
    private ReservationRequestStatus status;
    private int requestedByUserId;
    private int approvedByUserId;
    private String rejectionReason;
    private Date requestDate;
    private Date processedDate;

    public ReservationRequest() {}

    public ReservationRequest(String guestName, String address, String contactNo, int roomTypeId, 
                             Date checkInDate, Date checkOutDate, int requestedByUserId) {
        this.guestName = guestName;
        this.address = address;
        this.contactNo = contactNo;
        this.roomTypeId = roomTypeId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.requestedByUserId = requestedByUserId;
        this.status = ReservationRequestStatus.PENDING;
        this.requestDate = new Date(System.currentTimeMillis());
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public ReservationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationRequestStatus status) {
        this.status = status;
    }

    public int getRequestedByUserId() {
        return requestedByUserId;
    }

    public void setRequestedByUserId(int requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
    }

    public int getApprovedByUserId() {
        return approvedByUserId;
    }

    public void setApprovedByUserId(int approvedByUserId) {
        this.approvedByUserId = approvedByUserId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
}
