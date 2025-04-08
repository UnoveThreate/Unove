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
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Seat;
import model.booking.OrderDetail;
import model.canteenItemTotal.CanteenItemOrder;

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
        String sql = "SELECT * FROM `order` WHERE OrderID = ?"; // Thay đổi tên bảng và các trường theo cơ sở dữ liệu của bạn

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

    public OrderDetail getOrderDetails(int orderId) throws SQLException {
        String sql = "SELECT o.OrderID, o.TimeCreated, m.Title AS MovieTitle, "
                + "r.RoomName, r.IsAvailable AS RoomStatus, r.ScreenType, "
                + "m.ImageURL, m.Synopsis, m.Status AS MovieStatus, m.Country, "
                + "cc.Name AS CinemaChainName, c.Name AS CinemaName, "
                + "c.Address, c.Province, c.District, c.Commune, ms.StartTime, ms.EndTime "
                + "FROM `order` o "
                + "JOIN Ticket t ON o.OrderID = t.OrderID "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Movie m ON ms.MovieID = m.MovieID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE o.OrderID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                OrderDetail order = new OrderDetail();
                order.setOrderId(rs.getInt("OrderID"));
                order.setTimeCreated(rs.getTimestamp("TimeCreated"));
                order.setMovieTitle(rs.getString("MovieTitle"));
                order.setRoomName(rs.getString("RoomName"));
                order.setRoomStatus(rs.getBoolean("RoomStatus") ? "Available" : "Unavailable");
                order.setImageURL(rs.getString("ImageURL"));
                order.setSynopsis(rs.getString("Synopsis"));
                order.setMovieStatus(rs.getString("MovieStatus"));
                order.setCountry(rs.getString("Country"));
                order.setCinemaName(rs.getString("CinemaName"));
                order.setAddress(rs.getString("Address"));
                order.setProvince(rs.getString("Province"));
                order.setDistrict(rs.getString("District"));
                order.setCommune(rs.getString("Commune"));
                order.setStartTime(rs.getTimestamp("StartTime"));
                order.setEndTime(rs.getTimestamp("EndTime"));
                return order;
            }
        }

        return null;
    }

    public List<Seat> getSeatsByOrderID(int orderID) {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT S.SeatID, S.RoomID, S.Name, S.CoordinateX, S.CoordinateY "
                + "FROM Ticket T "
                + "JOIN Seat S ON T.SeatID = S.SeatID "
                + "WHERE T.OrderID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and create Seat objects
            while (rs.next()) {
                int seatID = rs.getInt("SeatID");
                int roomID = rs.getInt("RoomID");
                String name = rs.getString("Name");
                int coordinateX = rs.getInt("CoordinateX");
                int coordinateY = rs.getInt("CoordinateY");
                seats.add(new Seat(seatID, roomID, name, coordinateX, coordinateY));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public List<CanteenItemOrder> getCanteenItemsByOrderID(int orderID) {

        List<CanteenItemOrder> canteenItems = new ArrayList<>();

        String query = "SELECT CIIO.CanteenItemOrderID, CIIO.CanteenItemID, CIIO.orderID,CIIO.Quantity,CI.`Name`,CI.Price "
                + "FROM ordercanteenitem CIIO "
                + "JOIN CanteenItem CI ON CIIO.CanteenItemID = CI.CanteenItemID "
                + "WHERE CIIO.OrderID = ? AND CI.Status = 'Còn hàng'";

        try (
                PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and create CanteenItemInOrder objects
            while (rs.next()) {

                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int canteenItemID = rs.getInt("CanteenItemID");
                int quantity = rs.getInt("quantity");

                canteenItems.add(new CanteenItemOrder(canteenItemID, quantity, orderID, name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canteenItems;
    }

    public String getQRCodeUrlByOrderID(int orderID) {
        String qrCodeUrl = null;
        String sql = "SELECT QRCode FROM `order` WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                qrCodeUrl = rs.getString("QRCode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as appropriate
        }

        return qrCodeUrl;
    }
     public String getStatusByOrderID(int orderID) {
        String status = null ;
        String sql = "SELECT Status FROM `order` WHERE OrderID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = rs.getString("Status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions as appropriate
        }

        return status;
    }
}
