/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Kaan
 */
public class OwnerRequest {

    private int requestID;
    private int userID;
    private Timestamp requestDate;
    private String status;
    private String reason;
    private String fullName;
    private String email;
    private String cinemaName;
    private String cinemaAddress;
    private String businessLicenseNumber;
    private String businessLicenseFile;

    public OwnerRequest() {
    }

    // Constructor with all fields
    public OwnerRequest(int requestID, int userID, Timestamp requestDate, String status, String reason,
            String fullName, String email, String cinemaName, String cinemaAddress,
            String businessLicenseNumber, String businessLicenseFile) {
        this.requestID = requestID;
        this.userID = userID;
        this.requestDate = requestDate;
        this.status = status;
        this.reason = reason;
        this.fullName = fullName;
        this.email = email;
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.businessLicenseNumber = businessLicenseNumber;
        this.businessLicenseFile = businessLicenseFile;
    }

    // Getters and setters for each field
    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public String getBusinessLicenseFile() {
        return businessLicenseFile;
    }

    public void setBusinessLicenseFile(String businessLicenseFile) {
        this.businessLicenseFile = businessLicenseFile;
    }

    @Override
    public String toString() {
        return "OwnerRequest{" + "requestID=" + requestID + ", userID=" + userID + ", requestDate=" + requestDate + ", status=" + status + ", reason=" + reason + ", fullName=" + fullName + ", email=" + email + ", cinemaName=" + cinemaName + ", cinemaAddress=" + cinemaAddress + ", businessLicenseNumber=" + businessLicenseNumber + ", businessLicenseFile=" + businessLicenseFile + '}';
    }

}
