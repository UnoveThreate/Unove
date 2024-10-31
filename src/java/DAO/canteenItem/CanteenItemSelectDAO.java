/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.canteenItem;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CanteenItem;

/**
 *
 * @author ASUS
 */
public class CanteenItemSelectDAO extends MySQLConnect {

    public CanteenItemSelectDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<CanteenItem> getAllCanteenItemByCinemaID(int cinemaID) throws SQLException {
        List<CanteenItem> itemList = new ArrayList<>();
        String sql = "SELECT * FROM canteenitem WHERE isAvailable = true AND cinemaID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID); // Đặt tham số cinemaID
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CanteenItem canteenItem = new CanteenItem();
                canteenItem.setCanteenItemID(rs.getInt("canteenItemID"));
                canteenItem.setCinemaID(rs.getInt("cinemaID"));
                canteenItem.setName(rs.getString("name"));
                canteenItem.setPrice(rs.getFloat("price"));
                canteenItem.setStock(rs.getInt("stock"));
                canteenItem.setStatus(rs.getString("status"));
                canteenItem.setImageURL(rs.getString("image"));
                canteenItem.setAvailable(rs.getBoolean("isAvailable"));
                itemList.add(canteenItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(itemList);
        System.out.println("Canteen items retrieved: " + itemList.size());
        return itemList;
    }

    public double getCanteenItemPriceById(int canteenItemID) {
        double price = 0.0;
        String sql = "SELECT Price FROM canteenitem WHERE CanteenItemID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {

            pstmt.setInt(1, canteenItemID);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                price = resultSet.getFloat("price");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return price;
    }

    public CanteenItem getItemBycanteenItemID(int canteenItemID) {
        CanteenItem canteenItem = null;
        String query = "SELECT CanteenItemID, Name, Price FROM CanteenItem WHERE CanteenItemID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            pstmt.setInt(1, canteenItemID);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                canteenItem = new CanteenItem();
                canteenItem.setCanteenItemID(resultSet.getInt("CanteenItemID"));
                canteenItem.setName(resultSet.getString("Name"));
                canteenItem.setPrice(resultSet.getFloat("Price"));
                // Bạn có thể thêm các thuộc tính khác nếu cần
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canteenItem;
    }
}
