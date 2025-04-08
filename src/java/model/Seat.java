package model;

public class Seat {

    private int seatID;
    private int roomID;
    private String name;
    private int coordinateX;
    private int coordinateY;
    private boolean available;

    // Constructor
    public Seat() {
    }

    public Seat(int seatID, int roomID, String name, int coordinateX, int coordinateY) {
        this.seatID = seatID;
        this.roomID = roomID;
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.available = true;
    }

    // Getters and Setters
    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
