/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.ticket;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Cinema;
import model.Seat;
import model.ticket.TicketOrderDetails;

/**
 *
 * @author DELL
 */
public class TicketDAO extends MySQLConnect {

    public TicketDAO(ServletContext context) throws Exception {
        super();
        connect(context); // Establish the connection from MySQLConnect
    }

    public void insertTickets(int orderID, List<Seat> seats, String status) {
        String query = "INSERT INTO ticket (OrderID, SeatID, Status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = this.connection.prepareStatement(query)) {
            for (Seat seat : seats) {
                ps.setInt(1, orderID);
                ps.setInt(2, seat.getSeatID());
                ps.setString(3, status);
                ps.addBatch();
            }
            ps.executeBatch(); // Execute all insert statements in a batch
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTicketStatus(int orderID, String status) {
        String sql = "UPDATE ticket SET Status = ? WHERE OrderID = ?";
        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderID);
            ps.executeUpdate(); // Thực hiện cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TicketOrderDetails getTicketOrderDetailsByOrderID(int orderID) {
        String sql = "SELECT t.TicketID, t.OrderID, t.SeatID, t.Status AS TicketStatus, "
                + "o.UserID, o.MovieSlotID, o.TimeCreated, "
                + "o.PremiumTypeID, o.Status AS OrderStatus, "
                + "o.Code, o.QRCode, "
                + "s.Name AS SeatName, s.CoordinateX, s.CoordinateY, "
                + "m.Title AS MovieTitle " // Thêm MovieTitle từ bảng movie
                + "FROM ticket t "
                + "JOIN `order` o ON t.OrderID = o.OrderID "
                + "JOIN seat s ON t.SeatID = s.SeatID "
                + "JOIN movieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN movie m ON ms.MovieID = m.MovieID " // Kết hợp bảng movie
                + "WHERE t.OrderID = ?";

        TicketOrderDetails ticketOrderDetails = new TicketOrderDetails();

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // Lưu thông tin vào ticketOrderDetails
                ticketOrderDetails.setOrderID(rs.getInt("OrderID"));
                ticketOrderDetails.setUserID(rs.getInt("UserID"));
                ticketOrderDetails.setMovieSlotID(rs.getInt("MovieSlotID"));
                ticketOrderDetails.setTimeCreated(rs.getTimestamp("TimeCreated"));
                ticketOrderDetails.setPremiumTypeID(rs.getInt("PremiumTypeID"));
                ticketOrderDetails.setOrderStatus(rs.getString("OrderStatus"));
                ticketOrderDetails.setCode(rs.getString("Code"));
                ticketOrderDetails.setQrCode(rs.getString("QRCode"));
                ticketOrderDetails.setTitle(rs.getString("MovieTitle")); // Lưu tiêu đề phim vào TicketOrderDetails

                // Lưu ghế
                Seat seat = new Seat();
                seat.setSeatID(rs.getInt("SeatID"));
                seat.setName(rs.getString("SeatName"));
                seat.setCoordinateX(rs.getInt("CoordinateX"));
                seat.setCoordinateY(rs.getInt("CoordinateY"));

                ticketOrderDetails.addSeat(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketOrderDetails;
    }
}
