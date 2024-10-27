/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.payment;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Order;

/**
 *
 * @author DELL
 */
public class OrderDAO extends MySQLConnect {

    public OrderDAO(ServletContext context) throws Exception {
        super();
        connect(context); // Establish the connection from MySQLConnect
    }

    public int insertOrder(int userID, int movieSlotID, Integer premiumTypeID, String status, String code, String qrCode) {
        int generatedOrderID = -1;

        String sql = "INSERT INTO `order` (UserID, MovieSlotID, TimeCreated, PremiumTypeID, Status, Code, QRCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userID);
            statement.setInt(2, movieSlotID);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            if (premiumTypeID != null) {
                statement.setInt(4, premiumTypeID);
            } else {
                statement.setNull(4, java.sql.Types.INTEGER);
            }

            statement.setString(5, status);
            statement.setString(6, code);
            statement.setString(7, qrCode);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedOrderID = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedOrderID;
    }

    public boolean updateOrderWithCodeAndQRCode(int orderID, String code, String qrCode) {
        String query = "UPDATE `order` SET Code = ?, QRCode = ? WHERE OrderID = ?";

        try (PreparedStatement ps = this.connection.prepareStatement(query)) {

            ps.setString(1, code);
            ps.setString(2, qrCode);
            ps.setInt(3, orderID);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer getPremiumTypeIDByUserId(int userID) {
        Integer premiumTypeID = null;
        String sql = "SELECT PremiumTypeID FROM USER WHERE UserID = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {

            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                premiumTypeID = rs.getInt("PremiumTypeID");

                // Kiểm tra xem premiumTypeID có null không, nếu null trả về giá trị null
                if (rs.wasNull()) {
                    premiumTypeID = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return premiumTypeID;
    }

    public boolean updateOrderStatus(int orderID, String status) {
        String sql = "UPDATE `order` SET Status = ? WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderID);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu cập nhật thành công

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Order getOrderById(int orderID) {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE OrderID = ?"; // Thay đổi tên bảng và các trường theo cơ sở dữ liệu của bạn

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderID(rs.getInt("OrderID"));
                order.setUserID(rs.getInt("UserID"));
                order.setMovieSlotID(rs.getInt("MovieSlotID"));
                order.setTimeCreated(rs.getTimestamp("TimeCreated"));
                order.setPremiumTypeID(rs.getInt("PremiumTypeID"));
                order.setStatus(rs.getString("Status"));
                order.setCode(rs.getString("Code"));
                order.setQRCodeURL(rs.getString("QrCode")); // Nếu có
                // Thêm các thuộc tính khác nếu cần
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In ra thông báo lỗi nếu có
        }
        return order;
    }

}
