package DAOSchedule;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO extends MySQLConnect {

    public TicketDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean createTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (OrderID, SeatID, Status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getOrderID());
            stmt.setInt(2, ticket.getSeatID());
            stmt.setString(3, ticket.getStatus());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ticket getTicketById(int ticketId) {
        String sql = "SELECT * FROM Ticket WHERE TicketID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTicket(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ticket> getTicketsByOrderId(int orderId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Ticket WHERE OrderID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(mapResultSetToTicket(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public boolean updateTicketStatus(int ticketId, String status) {
        String sql = "UPDATE Ticket SET Status = ? WHERE TicketID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, ticketId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setTicketID(rs.getInt("TicketID"));
        ticket.setOrderID(rs.getInt("OrderID"));
        ticket.setSeatID(rs.getInt("SeatID"));
        ticket.setStatus(rs.getString("Status"));
        return ticket;
    }
    
}