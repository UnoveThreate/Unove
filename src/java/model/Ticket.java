package model;

public class Ticket {
    private int ticketID;        
    private int orderID;         
    private int seatID;          
    private String status;       
    public Ticket() {
    }

    public Ticket(int ticketID, int orderID, int seatID, String status) {
        this.ticketID = ticketID;
        this.orderID = orderID;
        this.seatID = seatID;
        this.status = status;
    }

    // Getter và Setter cho các thuộc tính
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}