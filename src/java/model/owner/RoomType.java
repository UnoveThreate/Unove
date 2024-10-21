/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.owner;

/**
 *
 * @author nguyendacphong
 */
public class RoomType {
    private int roomID;
    private String type;

    // Constructors, Getters, Setters
    public RoomType() {}

    public RoomType(int roomID, String type) {
        this.roomID = roomID;
        this.type = type;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RoomType{" + "roomID=" + roomID + ", type=" + type + '}';
    }
 
    
    
}
