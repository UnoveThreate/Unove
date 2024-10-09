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
    private String imageURL; // Thêm thuộc tính imageURL

    // Constructors
    public CanteenItem(int cinemaID, String name, float price, int stock, String status, String imageURL) {
        this.cinemaID = cinemaID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.imageURL = imageURL; // Khởi tạo imageURL
    }

    public CanteenItem(int canteenItemID, int cinemaID, String name, float price, int stock, String status, String imageURL) {
        this.canteenItemID = canteenItemID;
        this.cinemaID = cinemaID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.imageURL = imageURL; // Khởi tạo imageURL
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
