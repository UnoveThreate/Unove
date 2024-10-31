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
    private String taxNumber;
    private String businessLicenseFile;
    private String username;
    private String email;

    public OwnerRequest(int requestID, int userID, Timestamp requestDate, String status, String taxNumber, String businessLicenseFile) {
        this.requestID = requestID;
        this.userID = userID;
        this.requestDate = requestDate;
        this.status = status;
        this.taxNumber = taxNumber;
        this.businessLicenseFile = businessLicenseFile;
    }

    public OwnerRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getBusinessLicenseFile() {
        return businessLicenseFile;
    }

    public void setBusinessLicenseFile(String businessLicenseFile) {
        this.businessLicenseFile = businessLicenseFile;
    }

    @Override
    public String toString() {
        return "OwnerRequest{" + "requestID=" + requestID + ", userID=" + userID + ", requestDate=" + requestDate + ", status=" + status + ", taxNumber=" + taxNumber + ", businessLicenseFile=" + businessLicenseFile + '}';
    }

}
