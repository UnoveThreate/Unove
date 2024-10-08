package DAOSchedule;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Seat;

public class SeatDAO extends MySQLConnect {

    public SeatDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public void addSeat(int roomId, String name, int coordinateX, int coordinateY, double price) {
        String sql = "INSERT INTO Seat (RoomID, Name, CoordinateX, CoordinateY, Price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setString(2, name);
            stmt.setInt(3, coordinateX);
            stmt.setInt(4, coordinateY);
            stmt.setDouble(5, price);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Seat> getSeatsByRoomId(int roomId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM Seat WHERE RoomID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Seat seat = mapResultSetToSeat(resultSet);
                    seat.setAvailable(checkSeatAvailability(seat.getSeatID()));
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public boolean checkSeatAvailability(int seatID) {
        String sql = "SELECT COUNT(*) FROM Ticket WHERE SeatID = ? AND Status = 'Đã đặt'";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, seatID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Seat getSeatById(int seatID) {
        String sql = "SELECT * FROM Seat WHERE SeatID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, seatID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Seat seat = mapResultSetToSeat(resultSet);
                    seat.setAvailable(checkSeatAvailability(seat.getSeatID()));
                    return seat;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Seat mapResultSetToSeat(ResultSet resultSet) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatID(resultSet.getInt("SeatID"));
        seat.setRoomID(resultSet.getInt("RoomID"));
        seat.setName(resultSet.getString("Name"));
        seat.setCoordinateX(resultSet.getInt("CoordinateX"));
        seat.setCoordinateY(resultSet.getInt("CoordinateY"));
        seat.setPrice(resultSet.getDouble("Price"));
        return seat;
    }

    public boolean isSeatBooked(int seatId, int movieSlotId) {
        String sql = "SELECT COUNT(*) FROM Ticket t " +
                     "JOIN `Order` o ON t.OrderID = o.OrderID " +
                     "WHERE t.SeatID = ? AND o.MovieSlotID = ? AND t.Status = 'Đã đặt'";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, seatId);
            statement.setInt(2, movieSlotId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
       
    public List<Seat> getBookedSeatsByMovieSlotId(int movieSlotId) {
        List<Seat> bookedSeats = new ArrayList<>();
        String sql = "SELECT s.* FROM Seat s " +
                     "JOIN Ticket t ON s.SeatID = t.SeatID " +
                     "JOIN `Order` o ON t.OrderID = o.OrderID " +
                     "WHERE o.MovieSlotID = ? AND t.Status = 'Đã đặt'";
        
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, movieSlotId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Seat seat = mapResultSetToSeat(resultSet);
                    seat.setAvailable(false); // Ghế đã được đặt
                    bookedSeats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedSeats;
    }

    public boolean isSeatAvailable(int seatId, int movieSlotId) {
        return !isSeatBooked(seatId, movieSlotId);
    }

    public boolean bookSeat(int seatId, int movieSlotId) {
        // Đầu tiên, tạo một đơn hàng mới
        int orderId = createOrder(movieSlotId);
        if (orderId == -1) {
            return false;
        }

        // Sau đó, tạo một vé mới cho ghế này
        return createTicket(orderId, seatId);
    }

    private int createOrder(int movieSlotId) {
        String sql = "INSERT INTO `Order` (MovieSlotID, OrderDate, TotalAmount) VALUES (?, NOW(), 0)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, movieSlotId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean createTicket(int orderId, int seatId) {
        String sql = "INSERT INTO Ticket (OrderID, SeatID, Status) VALUES (?, ?, 'Đã đặt')";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, seatId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}