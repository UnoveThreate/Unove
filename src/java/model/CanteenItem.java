/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Kaan
 */
public class CanteenItem {

    private int canteenItemID;
    private int cinemaID;
    private String name;
    private float price;
    private int stock;
    private String status;

    // Constructors
    public CanteenItem(int cinemaID, String name, float price, int stock, String status) {
        this.cinemaID = cinemaID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;

    }

    public CanteenItem(int canteenItemID, int cinemaID, String name, float price, int stock, String status) {
        this.canteenItemID = canteenItemID;
        this.cinemaID = cinemaID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;

    }

    // Getters and Setters
    public int getCanteenItemID() {
        return canteenItemID;
    }

    public void setCanteenItemID(int canteenItemID) {
        this.canteenItemID = canteenItemID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
