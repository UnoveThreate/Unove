/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
 *
 * @author Kaan
 */
public class CanteenItemDAO extends MySQLConnect {

    public CanteenItemDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // Create a new Canteen Item
    public boolean addCanteenItem(CanteenItem item) {
        String sql = "INSERT INTO CanteenItem (CinemaID, Name, Price, Stock, Status, imageURL) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, item.getCinemaID());
            pstmt.setString(2, item.getName());
            pstmt.setFloat(3, item.getPrice());
            pstmt.setInt(4, item.getStock());
            pstmt.setString(5, item.getStatus()); // Sử dụng status
            pstmt.setString(6, item.getImageURL()); // Lưu imageURL
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all Canteen Items by CinemaID
    public List<CanteenItem> getAllCanteenItems(int cinemaID) {
        List<CanteenItem> items = new ArrayList<>();
        String sql = "SELECT * FROM CanteenItem WHERE CinemaID = ? AND Status = 'true'"; // Lọc theo status
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                items.add(new CanteenItem(
                        rs.getInt("CanteenItemID"),
                        rs.getInt("CinemaID"),
                        rs.getString("Name"),
                        rs.getFloat("Price"),
                        rs.getInt("Stock"),
                        rs.getString("Status"), // Chỉ sử dụng status
                        rs.getString("imageURL") // Lấy imageURL
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Update Canteen Item
    public boolean updateCanteenItem(CanteenItem item) {
        String sql = "UPDATE CanteenItem SET Name = ?, Price = ?, Stock = ?, imageURL = ? WHERE CanteenItemID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setFloat(2, item.getPrice());
            pstmt.setInt(3, item.getStock());
            pstmt.setString(4, item.getImageURL()); // Cập nhật imageURL
            pstmt.setInt(5, item.getCanteenItemID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Canteen Item (chuyển status thành false)
    public boolean softDeleteCanteenItem(int canteenItemID) {
        String sql = "UPDATE CanteenItem SET Status = 'false' WHERE CanteenItemID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, canteenItemID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
