package DAO.cinemaChainOwnerDAO;

import jakarta.servlet.ServletContext;
import model.owner.MovieSlot;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import database.MySQLConnect;

public class MovieSlotDAO extends MySQLConnect {

    // Constructor nhận ServletContext để kết nối
    public MovieSlotDAO(ServletContext context) throws Exception {
        super();
        connect(context);  // Kết nối cơ sở dữ liệu bằng cách sử dụng ServletContext
    }

    public List<MovieSlot> getAllMovieSlots(int cinemaID, Integer roomID, Integer movieID) throws SQLException {
        List<MovieSlot> movieSlots = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT ms.*, m.Title, m.Rating, m.ImageURL FROM MovieSlot ms "
                + "JOIN Movie m ON ms.MovieID = m.MovieID "
                + "WHERE m.CinemaID = ? AND ms.Status = 'Active'" // Chỉ lấy MovieSlot có status 'Active'
        );

        // Nếu roomID không phải null, thêm điều kiện lọc theo roomID
        if (roomID != null) {
            sql.append(" AND ms.RoomID = ?");
        }

        // Nếu movieID không phải null, thêm điều kiện lọc theo movieID
        if (movieID != null) {
            sql.append(" AND ms.MovieID = ?");
        }

        sql.append(" ORDER BY ms.StartTime DESC"); // Sắp xếp theo thời gian bắt đầu

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, cinemaID); // Đặt tham số cinemaID

            // Nếu roomID không phải null, đặt tham số roomID
            if (roomID != null) {
                ps.setInt(2, roomID);
            }

            // Nếu movieID không phải null, đặt tham số movieID
            if (movieID != null) {
                ps.setInt(roomID != null ? 3 : 2, movieID);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MovieSlot slot = new MovieSlot(
                            rs.getInt("MovieSlotID"),
                            rs.getInt("RoomID"),
                            rs.getInt("MovieID"),
                            rs.getTimestamp("StartTime").toLocalDateTime(),
                            rs.getTimestamp("EndTime").toLocalDateTime(),
                            rs.getString("Type"),
                            rs.getFloat("Price"),
                            rs.getFloat("Discount"),
                            rs.getString("Status")
                    );
                    // Lấy thêm thông tin phim
                    slot.setMovieTitle(rs.getString("Title"));
                    slot.setMovieRating(rs.getFloat("Rating"));
                    slot.setMovieImageURL(rs.getString("ImageURL"));

                    movieSlots.add(slot);
                }
            }
        }
        return movieSlots;
    }

    // Thêm mới MovieSlot với status mặc định là 'Active'
    public boolean createMovieSlot(MovieSlot movieSlot) throws SQLException {
        String sql = "INSERT INTO MovieSlot (RoomID, MovieID, StartTime, EndTime, Type, Price, Discount, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, movieSlot.getRoomID());
            ps.setInt(2, movieSlot.getMovieID());
            ps.setObject(3, movieSlot.getStartTime());
            ps.setObject(4, movieSlot.getEndTime());
            ps.setString(5, movieSlot.getType());
            ps.setFloat(6, movieSlot.getPrice());
            ps.setFloat(7, movieSlot.getDiscount());
            ps.setString(8, "Active"); // Đặt status mặc định là 'Active'

            return ps.executeUpdate() > 0;
        }
    }

    // Kiểm tra xem suất chiếu có trùng không
    public boolean checkOverlap(LocalDateTime startTime, LocalDateTime endTime, int roomID) throws SQLException {
        String sql = "SELECT * FROM MovieSlot WHERE RoomID = ? AND "
                + "NOT (StartTime >= ? OR EndTime <= ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomID);
            ps.setObject(2, endTime);   // Điều kiện suất chiếu mới phải bắt đầu sau khi suất chiếu hiện tại kết thúc
            ps.setObject(3, startTime); // Điều kiện suất chiếu mới phải kết thúc trước khi suất chiếu hiện tại bắt đầu

            ResultSet rs = ps.executeQuery();
            return rs.next();  // Nếu tìm thấy kết quả, tức là có sự trùng lặp
        }
    }

    // Lấy danh sách suất chiếu bị trùng
    public List<MovieSlot> getOverlappingSlots(LocalDateTime startTime, LocalDateTime endTime, int roomID) throws SQLException {
        List<MovieSlot> overlappingSlots = new ArrayList<>();
        String sql = "SELECT * FROM MovieSlot WHERE RoomID = ? AND "
                + "NOT (StartTime >= ? OR EndTime <= ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roomID);
            ps.setObject(2, endTime);   // Điều kiện suất chiếu mới phải bắt đầu sau khi suất chiếu hiện tại kết thúc
            ps.setObject(3, startTime); // Điều kiện suất chiếu mới phải kết thúc trước khi suất chiếu hiện tại bắt đầu

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MovieSlot slot = new MovieSlot(
                        rs.getInt("MovieSlotID"),
                        rs.getInt("RoomID"),
                        rs.getInt("MovieID"),
                        rs.getTimestamp("StartTime").toLocalDateTime(),
                        rs.getTimestamp("EndTime").toLocalDateTime(),
                        rs.getString("Type"),
                        rs.getFloat("Price"),
                        rs.getFloat("Discount"),
                        rs.getString("Status")
                );
                overlappingSlots.add(slot);
            }
        }
        return overlappingSlots;
    }

    public List<MovieSlot> getAllMovieSlots(int cinemaID, Integer roomID) throws SQLException {
        List<MovieSlot> movieSlots = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT ms.*, m.Title, m.Rating, m.ImageURL FROM MovieSlot ms "
                + "JOIN Movie m ON ms.MovieID = m.MovieID "
                + "WHERE m.CinemaID = ? AND ms.Status = 'Active'" // Chỉ lấy MovieSlot có status 'Active'
        );

        // Nếu roomID không phải null, thêm điều kiện lọc theo roomID
        if (roomID != null) {
            sql.append(" AND ms.RoomID = ?");
        }

        sql.append(" ORDER BY ms.StartTime DESC"); // Sắp xếp theo thời gian bắt đầu

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, cinemaID); // Đặt tham số cinemaID

            // Nếu roomID không phải null, đặt tham số roomID
            if (roomID != null) {
                ps.setInt(2, roomID);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MovieSlot slot = new MovieSlot(
                            rs.getInt("MovieSlotID"),
                            rs.getInt("RoomID"),
                            rs.getInt("MovieID"),
                            rs.getTimestamp("StartTime").toLocalDateTime(),
                            rs.getTimestamp("EndTime").toLocalDateTime(),
                            rs.getString("Type"),
                            rs.getFloat("Price"),
                            rs.getFloat("Discount"),
                            rs.getString("Status")
                    );
                    // Lấy thêm thông tin phim
                    slot.setMovieTitle(rs.getString("Title"));
                    slot.setMovieRating(rs.getFloat("Rating"));
                    slot.setMovieImageURL(rs.getString("ImageURL"));

                    movieSlots.add(slot);
                }
            }
        }
        return movieSlots;
    }

    // Phương thức lấy tất cả MovieSlot theo cinemaID và movieID chỉ lấy phim có Status = TRUE
    public List<MovieSlot> getMovieSlotsByCinemaAndMovie(int cinemaID, int movieID) throws SQLException {
        List<MovieSlot> movieSlots = new ArrayList<>();
        String sql = "SELECT ms.*, m.Title, m.Rating, m.ImageURL FROM MovieSlot ms "
                + "JOIN Movie m ON ms.MovieID = m.MovieID "
                + "WHERE m.CinemaID = ? AND m.Status = TRUE AND ms.MovieID = ? AND ms.Status = 'Active' ORDER BY ms.StartTime DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cinemaID);
            ps.setInt(2, movieID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MovieSlot slot = new MovieSlot(
                            rs.getInt("MovieSlotID"),
                            rs.getInt("RoomID"),
                            rs.getInt("MovieID"),
                            rs.getTimestamp("StartTime").toLocalDateTime(),
                            rs.getTimestamp("EndTime").toLocalDateTime(),
                            rs.getString("Type"),
                            rs.getFloat("Price"),
                            rs.getFloat("Discount"),
                            rs.getString("Status")
                    );
                    // Lấy thêm thông tin phim
                    slot.setMovieTitle(rs.getString("Title"));
                    slot.setMovieRating(rs.getFloat("Rating"));
                    slot.setMovieImageURL(rs.getString("ImageURL"));

                    movieSlots.add(slot);
                }
            }
        }
        return movieSlots;
    }

    public MovieSlot getMovieSlotById(int movieSlotID) throws SQLException {
        MovieSlot movieSlot = null;
        String sql = "SELECT * FROM MovieSlot WHERE movieSlotID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieSlotID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movieSlot = new MovieSlot();
                movieSlot.setMovieSlotID(rs.getInt("movieSlotID"));
                movieSlot.setRoomID(rs.getInt("roomID"));
                movieSlot.setMovieID(rs.getInt("movieID"));
                movieSlot.setStartTime(rs.getTimestamp("startTime").toLocalDateTime());
                movieSlot.setEndTime(rs.getTimestamp("endTime").toLocalDateTime());
                movieSlot.setType(rs.getString("type"));
                movieSlot.setPrice(rs.getFloat("price"));
                movieSlot.setDiscount(rs.getFloat("discount"));
                movieSlot.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving movie slot with ID: " + movieSlotID, e);
        }

        return movieSlot;
    }

    // Cập nhật MovieSlot
    public boolean updateMovieSlot(MovieSlot movieSlot) throws SQLException {
        String sql = "UPDATE MovieSlot SET RoomID = ?, MovieID = ?, StartTime = ?, EndTime = ?, Type = ?, Price = ?, Discount = ? "
                + "WHERE MovieSlotID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, movieSlot.getRoomID());
            ps.setInt(2, movieSlot.getMovieID());
            ps.setObject(3, movieSlot.getStartTime());
            ps.setObject(4, movieSlot.getEndTime());
            ps.setString(5, movieSlot.getType());
            ps.setFloat(6, movieSlot.getPrice());
            ps.setFloat(7, movieSlot.getDiscount());
            ps.setInt(8, movieSlot.getMovieSlotID()); // Đặt ID của MovieSlot cần cập nhật

            return ps.executeUpdate() > 0; // Trả về true nếu có bản ghi được cập nhật
        }
    }

    public void softDeleteMovieSlot(int movieSlotID) throws SQLException {
        String query = "UPDATE MovieSlot SET Status = 'InActive' WHERE MovieSlotID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, movieSlotID);
            statement.executeUpdate();
        }
    }

}
