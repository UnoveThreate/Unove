package model;

public class Seat {
    private int seatID;          
    private int roomID;          
    private String name;         
    private int coordinateX;     
    private int coordinateY;     
    private boolean available;    
    private boolean isVIP;        
    private boolean isSweetbox;   
    private boolean isRegular;   

    // Constructor
    public Seat() {
    }

    // Getter và Setter
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

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean isVIP) {
        this.isVIP = isVIP;
    }

    public boolean isSweetbox() {
        return isSweetbox;
    }

    public void setSweetbox(boolean isSweetbox) {
        this.isSweetbox = isSweetbox;
    }

    public boolean isRegular() {
        return isRegular;
    }

    public void setRegular(boolean isRegular) {
        this.isRegular = isRegular;
    }
}