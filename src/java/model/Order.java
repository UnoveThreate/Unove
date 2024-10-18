package model;

import java.sql.Timestamp;

public class Order {

    private int orderID;
    private int userID;
    private int movieSlotID;
    private Timestamp timeCreated;
    private Integer premiumTypeID;
    private String status;
    private String QRCodeURL;
    private String Code;

    public Order() {
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
    

    public Order(int orderID, int userID, int movieSlotID, Timestamp timeCreated, Integer premiumTypeID, String status) {
        this.orderID = orderID;
        this.userID = userID;
        this.movieSlotID = movieSlotID;
        this.timeCreated = timeCreated;
        this.premiumTypeID = premiumTypeID;
        this.status = status;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "Order{"
                + "orderID=" + orderID
                + ", userID=" + userID
                + ", movieSlotID=" + movieSlotID
                + ", timeCreated=" + timeCreated
                + ", premiumTypeID=" + premiumTypeID
                + ", status='" + status + '\''
                + '}';
    }
}
