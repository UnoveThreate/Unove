/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class CanteenItem {
    private int canteenItemID;
    private int cinemaChainID;
    private String name;
    private float price;
    private int stock;
    private String status;

    public CanteenItem() {
    }

    public CanteenItem(int canteenItemID, int cinemaChainID, String name, float price, int stock, String status) {
        this.canteenItemID = canteenItemID;
        this.cinemaChainID = cinemaChainID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }

    public int getCanteenItemID() {
        return canteenItemID;
    }

    public void setCanteenItemID(int canteenItemID) {
        this.canteenItemID = canteenItemID;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
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

    @Override
    public String toString() {
        return "CanteenItem{" + "canteenItemID=" + canteenItemID + ", cinemaChainID=" + cinemaChainID + ", name=" + name + ", price=" + price + ", stock=" + stock + ", status=" + status + '}';
    }
    
    
}
