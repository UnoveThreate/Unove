package DAO;

import database.MySQLConnect;
import model.CanteenItem;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing CanteenItem data from the database. Ensures soft
 * deletion and availability filtering.
 */
public class CanteenItemDAO extends MySQLConnect {

    public CanteenItemDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // Create a new Canteen Item with default isAvailable = TRUE
    public boolean addCanteenItem(CanteenItem item) {
        String sql = "INSERT INTO CanteenItem (CinemaID, Name, Price, Stock, Status, Image, IsAvailable) VALUES (?, ?, ?, ?, ?, ?, TRUE)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, item.getCinemaID());
            pstmt.setString(2, item.getName());
            pstmt.setFloat(3, item.getPrice());
            pstmt.setInt(4, item.getStock());
            pstmt.setString(5, item.getStatus()); // Sử dụng status
            pstmt.setString(6, item.getImageURL()); // Lưu Image (trong DB là cột Image)
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

// Method to get all Canteen Items by CinemaID (only items that are available)
    public List<CanteenItem> getAllCanteenItems(int cinemaID) throws SQLException {
        List<CanteenItem> items = new ArrayList<>();
        String sql = "SELECT * FROM CanteenItem WHERE CinemaID = ? AND IsAvailable = TRUE"; // Lọc theo CinemaID và IsAvailable

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID); // Thiết lập tham số CinemaID cho truy vấn
            ResultSet rs = pstmt.executeQuery(); // Thực thi truy vấn và nhận kết quả

            while (rs.next()) {
                // Khởi tạo đối tượng CanteenItem và ánh xạ kết quả từ ResultSet
                CanteenItem item = new CanteenItem(
                        rs.getInt("CanteenItemID"), // CanteenItemID
                        rs.getInt("CinemaID"), // CinemaID
                        rs.getString("Name"), // Tên sản phẩm
                        rs.getFloat("Price"), // Giá
                        rs.getInt("Stock"), // Số lượng
                        rs.getString("Status"), // Trạng thái
                        rs.getString("Image"), // Hình ảnh
                        rs.getBoolean("isAvailable")
                );

                // Thêm item vào danh sách
                items.add(item);
            }
        }

        return items; // Trả về danh sách các CanteenItem
    }

    // Update Canteen Item (does not update isAvailable)
    public boolean updateCanteenItem(CanteenItem item) {
        String sql = "UPDATE CanteenItem SET Name = ?, Price = ?, Stock = ?, IsAvailable = ? WHERE CanteenItemID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setFloat(2, item.getPrice());
            pstmt.setInt(3, item.getStock());
            pstmt.setBoolean(4, item.isAvailable()); 
            pstmt.setInt(5, item.getCanteenItemID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Soft delete Canteen Item (set isAvailable to FALSE)
    public boolean softDeleteCanteenItem(int canteenItemID) {
        String sql = "UPDATE CanteenItem SET IsAvailable = FALSE WHERE CanteenItemID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, canteenItemID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
