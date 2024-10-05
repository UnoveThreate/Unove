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
        connect(context); // Kết nối đến cơ sở dữ liệu
    }

    public void addSeat(int roomId, String name, int coordinateX, int coordinateY) {
        String sql = "INSERT INTO Seat (RoomID, Name, CoordinateX, CoordinateY) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setString(2, name);
            stmt.setInt(3, coordinateX);
            stmt.setInt(4, coordinateY);
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
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Seat seat = new Seat();
            seat.setSeatID(resultSet.getInt("SeatID"));
            seat.setRoomID(resultSet.getInt("RoomID"));
            seat.setName(resultSet.getString("Name"));
            seat.setCoordinateX(resultSet.getInt("CoordinateX"));
            seat.setCoordinateY(resultSet.getInt("CoordinateY"));
            seat.setAvailable(checkSeatAvailability(seat.getSeatID()));
            seat.setVIP(resultSet.getBoolean("IsVIP")); 
            seat.setSweetbox(resultSet.getBoolean("IsSweetbox")); 
            seat.setRegular(resultSet.getBoolean("IsRegular"));

            seats.add(seat); // Thêm ghế vào danh sách
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return seats; // Trả về danh sách ghế
}

    public boolean checkSeatAvailability(int seatID) {
        String sql = "SELECT COUNT(*) FROM Ticket WHERE SeatID = ? AND Status = 'Booked'";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, seatID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0; // Nếu không có vé nào đặt cho ghế này, ghế có sẵn
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Mặc định trả về false nếu có lỗi xảy ra
    }
}