package model.ticket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Seat;

public class TicketOrderDetails {
    private int orderID;
    private int userID;
    private int movieSlotID;
    private Timestamp timeCreated;
    private int premiumTypeID;
    private String orderStatus;
    private String code;
    private String qrCode;
    private String title;
    private List<Seat> seats = new ArrayList<>();

    // Getters and Setters
    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    // Other getters and setters

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

    public int getPremiumTypeID() {
        return premiumTypeID;
    }

    public void setPremiumTypeID(int premiumTypeID) {
        this.premiumTypeID = premiumTypeID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
