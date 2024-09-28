package model;

import java.util.Date;

public class MovieSlot {
    private int movieSlotID;
    private int roomID;
    private int movieID;
    private Date startTime;
    private Date endTime;
    private String type;
    private float price;
    private float discount;
    private String status;

    // Constructors, getters, setters

    public int getMovieSlotID() {
        return movieSlotID;
    }

    public void setMovieSlotID(int movieSlotID) {
        this.movieSlotID = movieSlotID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}