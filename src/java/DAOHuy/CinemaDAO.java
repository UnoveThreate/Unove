package DAOHuy;

import model.Cinema;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaDAO extends MySQLConnect {

    public CinemaDAO(ServletContext context) throws Exception {
        super(); 
        connect((ServletContext) context);
    }

    public List<Cinema> getCinemasByChain(int cinemaChainID) {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM Cinema WHERE CinemaChainID = ?";

        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Cinema cinema = new Cinema();
                    cinema.setCinemaID(rs.getInt("CinemaID"));
                    cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
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

    public boolean insertCinema(Cinema cinema) {
        String sql = "INSERT INTO Cinema (CinemaChainID, Address, Province, District, Commune) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.connection; // Sử dụng kết nối từ MySQLConnect
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, cinema.getCinemaChainID());
            pstmt.setString(2, cinema.getAddress());
            pstmt.setString(3, cinema.getProvince());
            pstmt.setString(4, cinema.getDistrict());
            pstmt.setString(5, cinema.getCommune());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public Cinema getCinemaById(int cinemaID) {
        Cinema cinema = null;
        String sql = "SELECT * FROM Cinema WHERE CinemaID = ?";
        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, cinemaID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cinema = new Cinema();
                    cinema.setCinemaID(rs.getInt("CinemaID"));
                    cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
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
