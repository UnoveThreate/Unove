/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author nguyendacphong
 */
public class Order_Update {

    private int orderID;
    private int userID;
    private int movieSlotID;
    private Timestamp timeCreated;
    private Integer premiumTypeID;
    private String status;
    private String QRCodeURL;
    private String Code;
    private boolean reviewRequestSent;

    public Order_Update() {
    }

    public Order_Update(int orderID, int userID, int movieSlotID, Timestamp timeCreated, Integer premiumTypeID, String status, String QRCodeURL, String Code) {
        this.orderID = orderID;
        this.userID = userID;
        this.movieSlotID = movieSlotID;
        this.timeCreated = timeCreated;
        this.premiumTypeID = premiumTypeID;
        this.status = status;
        this.QRCodeURL = QRCodeURL;
        this.Code = Code;
    }

    public Order_Update(int orderID, int userID, int movieSlotID, Timestamp timeCreated, Integer premiumTypeID, String status, String QRCodeURL, String Code, boolean reviewRequestSent) {
        this.orderID = orderID;
        this.userID = userID;
        this.movieSlotID = movieSlotID;
        this.timeCreated = timeCreated;
        this.premiumTypeID = premiumTypeID;
        this.status = status;
        this.QRCodeURL = QRCodeURL;
        this.Code = Code;
        this.reviewRequestSent = reviewRequestSent;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMovieSlotID() {
        return movieSlotID;
    }

    public void setMovieSlotID(int movieSlotID) {
        this.movieSlotID = movieSlotID;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Integer getPremiumTypeID() {
        return premiumTypeID;
    }

    public void setPremiumTypeID(Integer premiumTypeID) {
        this.premiumTypeID = premiumTypeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQRCodeURL() {
        return QRCodeURL;
    }

    public void setQRCodeURL(String QRCodeURL) {
        this.QRCodeURL = QRCodeURL;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public boolean isReviewRequestSent() {
        return reviewRequestSent;
    }

    public void setReviewRequestSent(boolean reviewRequestSent) {
        this.reviewRequestSent = reviewRequestSent;
    }

    @Override
    public String toString() {
        return "Order_Update{" + "orderID=" + orderID + ", userID=" + userID + ", movieSlotID=" + movieSlotID + ", timeCreated=" + timeCreated + ", premiumTypeID=" + premiumTypeID + ", status=" + status + ", QRCodeURL=" + QRCodeURL + ", Code=" + Code + ", reviewRequestSent=" + reviewRequestSent + '}';
    }

}
