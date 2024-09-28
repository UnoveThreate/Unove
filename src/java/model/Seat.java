// File: Seat.java
package model;

public class Seat {
    private int seatID; // ID của ghế
    private int roomID; // ID của phòng chiếu
    private String name; // Tên ghế (ví dụ: A1, B2)
    private int coordinateX; // Tọa độ X
    private int coordinateY; // Tọa độ Y
    private boolean booked; // Trạng thái ghế (đã đặt hay chưa)

    // Constructor
    public Seat(int seatID, int roomID, String name, int coordinateX, int coordinateY, boolean booked) {
        this.seatID = seatID;
        this.roomID = roomID;
        this.name = name;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.booked = booked;
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

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}