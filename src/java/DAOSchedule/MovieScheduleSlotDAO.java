package DAOSchedule;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import model.MovieSlot;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieScheduleSlotDAO extends MySQLConnect {

    public MovieScheduleSlotDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<MovieSlot> getMovieSlotsByCinemaAndDate(int cinemaID, LocalDate date) {
        List<MovieSlot> movieSlots = new ArrayList<>();
        String sql = "SELECT ms.* FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "WHERE r.CinemaID = ? AND DATE(ms.StartTime) = ? "
                + "AND ms.Status = 'Active'";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            pstmt.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    movieSlots.add(extractMovieSlotFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieSlots;
    }

    public List<LocalDate> getAvailableDates(int cinemaID) {
        List<LocalDate> dates = new ArrayList<>();
        String sql = "SELECT DISTINCT DATE(StartTime) as show_date FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "WHERE r.CinemaID = ? AND StartTime >= CURDATE() "
                + "AND ms.Status = 'Active' "
                + "ORDER BY show_date LIMIT 7";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dates.add(rs.getDate("show_date").toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public MovieSlot getMovieSlotById(int movieSlotID) {
        MovieSlot movieSlot = null;
        String sql = "SELECT * FROM MovieSlot WHERE MovieSlotID = ? AND Status = 'Active'";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieSlotID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movieSlot = extractMovieSlotFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieSlot;
    }

    public MovieSlot getLatestMovieSlot() {
        MovieSlot movieSlot = null;
        String sql = "SELECT * FROM MovieSlot WHERE StartTime > NOW() AND Status = 'Active' ORDER BY StartTime ASC LIMIT 1";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movieSlot = extractMovieSlotFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieSlot;
    }

    private MovieSlot extractMovieSlotFromResultSet(ResultSet rs) throws SQLException {
        MovieSlot movieSlot = new MovieSlot();
        movieSlot.setMovieSlotID(rs.getInt("MovieSlotID"));
        movieSlot.setRoomID(rs.getInt("RoomID"));
        movieSlot.setMovieID(rs.getInt("MovieID"));
        movieSlot.setStartTime(rs.getTimestamp("StartTime"));
        movieSlot.setEndTime(rs.getTimestamp("EndTime"));
        movieSlot.setType(rs.getString("Type"));
        movieSlot.setPrice(rs.getFloat("Price"));
        movieSlot.setDiscount(rs.getFloat("Discount"));
        movieSlot.setStatus(rs.getString("Status"));
        return movieSlot;
    }

    public int getCinemaIdByMovieSlotId(int movieSlotID) throws SQLException {
        String sql = "SELECT r.CinemaID "
                + "FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "WHERE ms.MovieSlotID = ? AND ms.Status = 'Active'";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieSlotID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CinemaID");
            }
        }
        return -1; 
    }
}