package DAOSchedule;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import model.MovieSlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieSlotDAO extends MySQLConnect {

    public MovieSlotDAO(ServletContext context) throws Exception {
        super(); 
        connect(context);
    }

    public List<MovieSlot> getMovieSlotsByCinemaAndDate(int cinemaID, LocalDate date) {
        List<MovieSlot> movieSlots = new ArrayList<>();
        String sql = "SELECT ms.* FROM MovieSlot ms " +
                     "JOIN Room r ON ms.RoomID = r.RoomID " +
                     "WHERE r.CinemaID = ? AND DATE(ms.StartTime) = ?";

        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, cinemaID);
            pstmt.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
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
                    movieSlots.add(movieSlot);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieSlots; 
    }

    public List<LocalDate> getAvailableDates(int cinemaID) {
        List<LocalDate> dates = new ArrayList<>();
        String sql = "SELECT DISTINCT DATE(StartTime) as show_date FROM MovieSlot ms " +
                     "JOIN Room r ON ms.RoomID = r.RoomID " +
                     "WHERE r.CinemaID = ? AND StartTime >= CURDATE() " +
                     "ORDER BY show_date LIMIT 7";

        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
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
}