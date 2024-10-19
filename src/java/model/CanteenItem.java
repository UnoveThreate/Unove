package model;

/**
 * CanteenItem class representing items in the cinema canteen.
 */
public class CanteenItem {

    private int canteenItemID;
    private int cinemaID;
    private String name;
    private float price;
    private int stock;
    private String status;
    private String imageURL; // URL to the image of the item
    private boolean isAvailable; // New field representing availability

    public CanteenItem() {
    }

    public CanteenItem(int canteenItemID, int cinemaID, String name, float price, int stock, String status,
            String imageURL, boolean isAvailable) {
        this.canteenItemID = canteenItemID;
        this.cinemaID = cinemaID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.imageURL = imageURL;
        this.isAvailable = isAvailable; // Initialize isAvailable
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "CanteenItem{" +
                "canteenItemID=" + canteenItemID +
                ", cinemaID=" + cinemaID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status='" + status + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
