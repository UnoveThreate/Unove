/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.owner;

import java.util.List;

/**
 *
 * @author nguyendacphong
 */
public class Room {

    private int roomID;
    private String roomName;
    private int capacity;
    private String screenType;
    private boolean isAvailable;
    private int cinemaID;
    private List<String> roomTypes;  // Chứa danh sách các loại phòng (3D, IMAX, ...)

    // Constructors, Getters, Setters
    public Room() {
    }

    public Room(int roomID, String roomName, int capacity, String screenType, boolean isAvailable, int cinemaID, List<String> roomTypes) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.screenType = screenType;
        this.isAvailable = isAvailable;
        this.cinemaID = cinemaID;
        this.roomTypes = roomTypes;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public List<String> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(List<String> roomTypes) {
        this.roomTypes = roomTypes;
    }

    @Override
    public String toString() {
        return "Room{" + "roomID=" + roomID + ", roomName=" + roomName + ", capacity=" + capacity + ", screenType=" + screenType + ", isAvailable=" + isAvailable + ", cinemaID=" + cinemaID + ", roomTypes=" + roomTypes + '}';
    }
    
    
}
