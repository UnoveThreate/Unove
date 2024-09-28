// File: SeatDAO.java
package dao;

import database.MySQLConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Seat;

public class SeatDAO extends MySQLConnect {

    public SeatDAO() {
        super();
    }

    public void addSeat(int roomId, String name, int coordinateX, int coordinateY) {
        String sql = "INSERT INTO Seat (RoomID, Name, CoordinateX, CoordinateY) VALUES (?, ?, ?, ?)";
        try (Connection conn = this.connection; 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setString(2, name);
            stmt.setInt(3, coordinateX);
            stmt.setInt(4, coordinateY);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Seat> getAllSeats() {
    List<Seat> seats = new ArrayList<>();
    String sql = "SELECT * FROM Seat"; // Đảm bảo rằng bảng Seat tồn tại trong cơ sở dữ liệu
    try (Connection conn = this.connection; 
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            int seatID = rs.getInt("SeatID");
            int roomID = rs.getInt("RoomID");
            String name = rs.getString("Name");
            int coordinateX = rs.getInt("CoordinateX");
            int coordinateY = rs.getInt("CoordinateY");
            boolean booked = rs.getBoolean("Booked"); // Nếu có cột này trong bảng
            seats.add(new Seat(seatID, roomID, name, coordinateX, coordinateY, booked));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return seats;
}
}