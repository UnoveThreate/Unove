package model;
import java.time.LocalDateTime;

public class Order {

    private int orderID;       
    private int userID;        
    private int movieSlotID;   
    private LocalDateTime timeCreated;  
    private int premiumTypeID; 
    private String status;     

    // Constructors
    public Order() {
    }

    public Order(int userID, int movieSlotID, LocalDateTime timeCreated, int premiumTypeID, String status) {
        this.userID = userID;
        this.movieSlotID = movieSlotID;
        this.timeCreated = timeCreated;
        this.premiumTypeID = premiumTypeID;
        this.status = status;
    }

    public Order(int orderID, int userID, int movieSlotID, LocalDateTime timeCreated, int premiumTypeID, String status) {
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

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getPremiumTypeID() {
        return premiumTypeID;
    }

    public void setPremiumTypeID(int premiumTypeID) {
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
