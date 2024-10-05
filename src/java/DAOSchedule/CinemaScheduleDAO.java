package DAOSchedule;

import model.Cinema;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaScheduleDAO extends MySQLConnect {

    public CinemaScheduleDAO(ServletContext context) throws Exception {
        super(); 
        connect((ServletContext) context);
    }

    // Lấy danh sách rạp chiếu theo chuỗi rạp
    public List<Cinema> getCinemasByChain(int cinemaChainID) {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM Cinema WHERE CinemaChainID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cinema cinema = new Cinema();
                    cinema.setCinemaID(rs.getInt("CinemaID"));
                    cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                    cinema.setName(rs.getString("Name")); // Bổ sung tên rạp
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

    // Thêm một rạp chiếu mới
    public boolean insertCinema(Cinema cinema) {
        String sql = "INSERT INTO Cinema (CinemaChainID, Name, Address, Province, District, Commune) VALUES (?, ?, ?, ?, ?, ?)";
        try (
             PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
             
            pstmt.setInt(1, cinema.getCinemaChainID());
            pstmt.setString(2, cinema.getName());  // Bổ sung tên rạp
            pstmt.setString(3, cinema.getAddress());
            pstmt.setString(4, cinema.getProvince());
            pstmt.setString(5, cinema.getDistrict());
            pstmt.setString(6, cinema.getCommune());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin rạp chiếu theo ID
    public Cinema getCinemaById(int cinemaID) {
        Cinema cinema = null;
        String sql = "SELECT * FROM Cinema WHERE CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cinema = new Cinema();
                    cinema.setCinemaID(rs.getInt("CinemaID"));
                    cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                    cinema.setName(rs.getString("Name"));  
                    cinema.setAddress(rs.getString("Address"));
                    cinema.setProvince(rs.getString("Province"));
                    cinema.setDistrict(rs.getString("District"));
                    cinema.setCommune(rs.getString("Commune"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinema; 
    }
}
