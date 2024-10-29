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
    private User currentUser;
    private Timestamp requestDate;
    private String status;
    private String taxNumber;
    private String businessLicenseFile;

    public OwnerRequest() {
    }

    public OwnerRequest(int requestID, User currentUser, Timestamp requestDate, String status, String reason, String taxNumber) {
        this.requestID = requestID;
        this.currentUser = currentUser;
        this.requestDate = requestDate;
        this.status = status;
        this.taxNumber = taxNumber;
        this.businessLicenseFile = businessLicenseFile;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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
        return "OwnerRequest{" + "requestID=" + requestID + ", currentUser=" + currentUser + ", requestDate=" + requestDate + ", status=" + status + ", taxNumber=" + taxNumber + ", businessLicenseFile=" + businessLicenseFile + '}';
    }

   

}
