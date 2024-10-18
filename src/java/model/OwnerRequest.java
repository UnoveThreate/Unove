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

    public OwnerRequest(int requestID, int userID, Timestamp requestDate, String status, String reason) {
        this.requestID = requestID;
        this.userID = userID;
        this.requestDate = requestDate;
        this.status = status;
        this.reason = reason;
    }

    public OwnerRequest() {
    }

    // Getters and setters
    public int getRequestID() {
        return requestID;
    }

    public int getUserID() {
        return userID;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OwnerRequest{" + "requestID=" + requestID + ", userID=" + userID + ", requestDate=" + requestDate + ", status=" + status + ", reason=" + reason + '}';
    }
}
