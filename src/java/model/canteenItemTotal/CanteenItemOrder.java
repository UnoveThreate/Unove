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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CanteenItemOrder{" + "canteenItemID=" + canteenItemID + ", quantity=" + quantity + '}';
    }

    
}
