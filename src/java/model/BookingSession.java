package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.canteenItemTotal.CanteenItemOrder;

public class BookingSession implements Serializable {

    private int movieSlotID;
    private int movieID;
    private int cinemaID;
    private int cinemaChainID;
    private List<Integer> selectedSeatIDs;
    private double totalPrice;
    private int userID;
    private String status;

    private MovieSlot movieSlot;
    private List<Seat> listSeats;
    private List<CanteenItemOrder> itemOrders; //Thêm vào để lấy list order cho việc thanh toán

    public BookingSession() {
        this.selectedSeatIDs = new ArrayList<>();
        this.status = "Đang xử lý";
    }

    public List<Seat> getListSeats() {
        return listSeats;
    }

    public void setListSeats(List<Seat> listSeats) {
        this.listSeats = listSeats;
    }

    public MovieSlot getMovieSlot() {
        return movieSlot;
    }

    public void setMovieSlot(MovieSlot movieSlot) {
        this.movieSlot = movieSlot;
    }

    // Getters and Setters
    public int getMovieSlotID() {
        return movieSlotID;
    }

    public void setMovieSlotID(int movieSlotID) {
        this.movieSlotID = movieSlotID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(int cinemaID) {
        this.cinemaID = cinemaID;
    }

    public int getCinemaChainID() {
        return cinemaChainID;
    }

    public void setCinemaChainID(int cinemaChainID) {
        this.cinemaChainID = cinemaChainID;
    }

    public List<Integer> getSelectedSeatIDs() {
        return selectedSeatIDs;
    }

    public void setSelectedSeatIDs(List<Integer> selectedSeatIDs) {
        this.selectedSeatIDs = selectedSeatIDs;
    }

    public void addSelectedSeatID(int seatID) {
        this.selectedSeatIDs.add(seatID);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void clear() {
        this.selectedSeatIDs.clear();
        this.totalPrice = 0;
        this.status = "Đang xử lý";
    }

    public List<CanteenItemOrder> getItemOrders() {
        return itemOrders;
    }

    public void setItemOrders(List<CanteenItemOrder> itemOrders) {
        this.itemOrders = itemOrders;
    }

    // Thêm vào phương thức này trong BookingSession:
    public void addCanteenItemOrder(int canteenItemID, int quantity) {
        if (itemOrders == null) {
            itemOrders = new ArrayList<>();
        }
        itemOrders.add(new CanteenItemOrder(canteenItemID, quantity));
    }

    @Override
    public String toString() {
        return "BookingSession{"
                + "movieSlotID=" + movieSlotID
                + ", movieID=" + movieID
                + ", cinemaID=" + cinemaID
                + ", cinemaChainID=" + cinemaChainID
                + ", selectedSeatIDs=" + selectedSeatIDs
                + ", totalPrice=" + totalPrice
                + ", userID=" + userID
                + ", status='" + status + '\''
                + '}';
    }
}
