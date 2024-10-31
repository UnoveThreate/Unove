package DAO.movie;

import model.CinemaChain;
import model.Cinema;
import model.MovieSlot;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CinemaChainMovieDAO extends MySQLConnect {

    public CinemaChainMovieDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // Lấy danh sách chuỗi rạp có chiếu phim này
    public List<CinemaChain> getCinemaChainsByMovie(int movieId) {
        List<CinemaChain> chains = new ArrayList<>();
        String sql = """
            SELECT DISTINCT cc.CinemaChainID, cc.UserID, cc.Name, cc.AvatarURL, cc.Information
            FROM cinemachain cc
            JOIN cinema c ON cc.CinemaChainID = c.CinemaChainID
            JOIN room r ON c.CinemaID = r.CinemaID
            JOIN movieslot ms ON r.RoomID = ms.RoomID
            WHERE ms.MovieID = ? 
            AND ms.Status = 'Active'
            AND ms.StartTime >= NOW()
            """;
            
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CinemaChain chain = new CinemaChain();
                    chain.setCinemaChainID(rs.getInt("CinemaChainID"));
                    chain.setUserID(rs.getInt("UserID"));
                    chain.setName(rs.getString("Name"));
                    chain.setAvatarURL(rs.getString("AvatarURL"));
                    chain.setInformation(rs.getString("Information"));
                    chains.add(chain);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chains;
    }   
 
public List<Cinema> getCinemasByChainAndMovie(int cinemaChainID, int movieID) {
    List<Cinema> cinemas = new ArrayList<>();
    String sql = """
        SELECT DISTINCT c.CinemaID, c.Name, c.Address, c.Province, c.District, c.Commune
        FROM cinema c
        JOIN room r ON c.CinemaID = r.CinemaID
        JOIN movieslot ms ON r.RoomID = ms.RoomID
        WHERE c.CinemaChainID = ? 
        AND ms.MovieID = ?
        ORDER BY c.Name
        """;

    try (
         PreparedStatement ps = this.connection.prepareStatement(sql)) {
        
        ps.setInt(1, cinemaChainID);
        ps.setInt(2, movieID);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setName(rs.getString("Name"));
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
                cinemas.add(cinema);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cinemas;
}

    public List<MovieSlot> getMovieSlotsByCinemaAndMovie(int cinemaId, int movieId, LocalDate date) {
    List<MovieSlot> slots = new ArrayList<>();
    String sql = """
        SELECT ms.MovieSlotID, ms.RoomID, ms.MovieID, 
               ms.StartTime, ms.EndTime, ms.Type, 
               ms.Price, ms.Discount, ms.Status,
               r.RoomName, r.ScreenType
        FROM movieslot ms
        JOIN room r ON ms.RoomID = r.RoomID
        WHERE r.CinemaID = ?
        AND ms.MovieID = ?
        AND ms.Status = 'Active'
        AND DATE(ms.StartTime) = ?
        AND ms.StartTime >= NOW()
        ORDER BY ms.StartTime
        """;
        
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
        pstmt.setInt(1, cinemaId);
        pstmt.setInt(2, movieId);
        pstmt.setDate(3, java.sql.Date.valueOf(date));
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                MovieSlot slot = extractMovieSlotFromResultSet(rs);
                slots.add(slot);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return slots;
}


public List<MovieSlot> getMovieSlotsByCinemaAndMovie(int cinemaId, int movieId) {
    List<MovieSlot> slots = new ArrayList<>();
    String sql = """
        SELECT ms.MovieSlotID, ms.RoomID, ms.MovieID, 
               ms.StartTime, ms.EndTime, ms.Type, 
               ms.Price, ms.Discount, ms.Status,
               r.RoomName, r.ScreenType
        FROM movieslot ms
        JOIN room r ON ms.RoomID = r.RoomID
        WHERE r.CinemaID = ?
        AND ms.MovieID = ?
        AND ms.Status = 'Active'
        AND ms.StartTime >= NOW()
        ORDER BY ms.StartTime
        """;
        
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
        pstmt.setInt(1, cinemaId);
        pstmt.setInt(2, movieId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                MovieSlot slot = extractMovieSlotFromResultSet(rs);
                slots.add(slot);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return slots;
}

    private MovieSlot extractMovieSlotFromResultSet(ResultSet rs) throws SQLException {
        MovieSlot slot = new MovieSlot();
        slot.setMovieSlotID(rs.getInt("MovieSlotID"));
        slot.setRoomID(rs.getInt("RoomID"));
        slot.setMovieID(rs.getInt("MovieID"));
        slot.setStartTime(rs.getTimestamp("StartTime"));
        slot.setEndTime(rs.getTimestamp("EndTime"));
        slot.setType(rs.getString("Type"));
        slot.setPrice(rs.getFloat("Price"));
        slot.setDiscount(rs.getFloat("Discount"));
        slot.setStatus(rs.getString("Status"));
        return slot;
    }

    // Kiểm tra suất chiếu tồn tại
    public boolean checkMovieSlotExists(int movieSlotId) {
        String sql = """
            SELECT COUNT(*) 
            FROM movieslot 
            WHERE MovieSlotID = ? 
            AND Status = 'Active'
            AND StartTime >= NOW()
            """;
        
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieSlotId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm method lấy thông tin chi tiết của một suất chiếu
    public MovieSlot getMovieSlotById(int movieSlotId) {
        String sql = """
            SELECT ms.MovieSlotID, ms.RoomID, ms.MovieID, 
                   ms.StartTime, ms.EndTime, ms.Type, 
                   ms.Price, ms.Discount, ms.Status,
                   r.RoomName, r.ScreenType
            FROM movieslot ms
            JOIN room r ON ms.RoomID = r.RoomID
            WHERE ms.MovieSlotID = ?
            AND ms.Status = 'Active'
            """;
            
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieSlotId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractMovieSlotFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}