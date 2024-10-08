package DAO.cinemaChainOwnerDAO;

import database.MySQLConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import model.owner.Room;

public class RoomDAO extends MySQLConnect {

    public RoomDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // 1. Create Room (Tạo phòng và lưu các loại phòng)
    public void createRoom(Room room) throws SQLException {
        String sql = "INSERT INTO Room (RoomName, Capacity, ScreenType, IsAvailable, CinemaID) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, room.getRoomName());
            statement.setInt(2, room.getCapacity());
            statement.setString(3, room.getScreenType());
            statement.setBoolean(4, true); // Mặc định IsAvailable = true
            statement.setInt(5, room.getCinemaID());
            statement.executeUpdate();

            // Lấy RoomID vừa tạo từ khóa chính (primary key) tự động tăng
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int roomID = generatedKeys.getInt(1);
                room.setRoomID(roomID);
                // Lưu RoomTypes sau khi tạo phòng
                saveRoomTypes(roomID, room.getRoomTypes());
            }
        }
    }

    public void updateRoom(Room room) throws SQLException {
        String sql = "UPDATE Room SET RoomName = ?, Capacity = ?, ScreenType = ?, CinemaID = ? WHERE RoomID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, room.getRoomName());
            statement.setInt(2, room.getCapacity());
            statement.setString(3, room.getScreenType());
            statement.setInt(4, room.getCinemaID()); // Đảm bảo CinemaID đúng
            statement.setInt(5, room.getRoomID());
            statement.executeUpdate();

            // Cập nhật RoomTypes sau khi cập nhật phòng
            saveRoomTypes(room.getRoomID(), room.getRoomTypes());
        }
    }

    // 3. Set isAvailable = false để "xóa" phòng (Không thực sự xóa mà chỉ ẩn phòng đi)
    public void deleteRoom(int roomID) throws SQLException {
        String sql = "UPDATE Room SET IsAvailable = false WHERE RoomID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomID);
            statement.executeUpdate();
        }
    }

    // 4. Lấy thông tin phòng theo RoomID
    public Room getRoom(int roomID) throws SQLException {
        Room room = null;
        String sql = "SELECT * FROM Room WHERE RoomID = ? AND IsAvailable = true";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                room = new Room(
                        rs.getInt("RoomID"),
                        rs.getString("RoomName"),
                        rs.getInt("Capacity"),
                        rs.getString("ScreenType"),
                        rs.getBoolean("IsAvailable"),
                        rs.getInt("CinemaID"),
                        getRoomTypes(rs.getInt("RoomID")) // Gọi phương thức để lấy loại phòng
                );
            }
        }
        return room;
    }

    // 5. Lưu loại phòng (RoomType) vào cơ sở dữ liệu
    private void saveRoomTypes(int roomID, List<String> roomTypes) throws SQLException {
        // Xóa các RoomTypes cũ của phòng chiếu
        String deleteSql = "DELETE FROM RoomType WHERE RoomID = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, roomID);
            deleteStatement.executeUpdate();
        }

        // Thêm RoomTypes mới
        String insertSql = "INSERT INTO RoomType (RoomID, Type) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            for (String type : roomTypes) {
                insertStatement.setInt(1, roomID);
                insertStatement.setString(2, type);
                insertStatement.executeUpdate();
            }
        }
    }

    // 6. Lấy danh sách RoomTypes của một phòng
    private List<String> getRoomTypes(int roomID) throws SQLException {
        List<String> roomTypes = new ArrayList<>();
        String sql = "SELECT Type FROM RoomType WHERE RoomID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roomID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                roomTypes.add(rs.getString("Type"));
            }
        }
        return roomTypes;
    }

    // Method to get rooms by cinema ID
    public List<Room> getRoomsByCinemaID(int cinemaID) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room WHERE CinemaID = ? AND IsAvailable = true";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cinemaID); // Set cinemaID in query
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Room room = new Room(
                            rs.getInt("RoomID"),
                            rs.getString("RoomName"),
                            rs.getInt("Capacity"),
                            rs.getString("ScreenType"),
                            rs.getBoolean("IsAvailable"),
                            rs.getInt("CinemaID"),
                            getRoomTypes(rs.getInt("RoomID")) // Gọi phương thức để lấy loại phòng
                    );
                    // Add room object to list
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }
}
