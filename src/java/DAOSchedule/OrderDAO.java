package DAOSchedule;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends MySQLConnect {

    public OrderDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public int createOrder(Order order) {
        String sql = "INSERT INTO `Order` (UserID, MovieSlotID, TimeCreated, PremiumTypeID, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserID());
            stmt.setInt(2, order.getMovieSlotID());
            stmt.setTimestamp(3, order.getTimeCreated());
            stmt.setObject(4, order.getPremiumTypeID()); // Có thể là null
            stmt.setString(5, order.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Tạo đơn hàng thất bại, không có dòng nào được thêm vào.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Tạo đơn hàng thất bại, không lấy được ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM `Order` WHERE OrderID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM `Order` WHERE UserID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE `Order` SET Status = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderID(rs.getInt("OrderID"));
        order.setUserID(rs.getInt("UserID"));
        order.setMovieSlotID(rs.getInt("MovieSlotID"));
        order.setTimeCreated(rs.getTimestamp("TimeCreated"));
        order.setPremiumTypeID(rs.getObject("PremiumTypeID") != null ? rs.getInt("PremiumTypeID") : null);
        order.setStatus(rs.getString("Status"));
        return order;
    }
    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE `Order` SET Status = ?, PremiumTypeID = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, order.getStatus());
            if (order.getPremiumTypeID() != null) {
                stmt.setInt(2, order.getPremiumTypeID());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setInt(3, order.getOrderID());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }
        }
    }
}