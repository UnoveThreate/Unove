/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.canteenItemTotal;

/**
 *
 * @author ASUS
 */
public class CanteenItemOrder {

    private int canteenItemID;
    private int quantity;
    private int orderID;
    private String name;
    private double price;

    public CanteenItemOrder() {
    }

    public CanteenItemOrder(int canteenItemID, int quantity) {
        this.canteenItemID = canteenItemID;
        this.quantity = quantity;

    }

    public int getCanteenItemID() {
        return canteenItemID;
    }

    public void setCanteenItemID(int canteenItemID) {
        this.canteenItemID = canteenItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public CanteenItemOrder(int canteenItemID, int quantity, int orderID, String name, double price) {
        this.canteenItemID = canteenItemID;
        this.quantity = quantity;
        this.orderID = orderID;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "CanteenItemOrder{" + "canteenItemID=" + canteenItemID + ", quantity=" + quantity + '}';
    }

}
