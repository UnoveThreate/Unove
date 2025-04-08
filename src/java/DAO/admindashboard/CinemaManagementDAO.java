package DAO.admindashboard;

import model.Cinema;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaManagementDAO extends MySQLConnect {

    public CinemaManagementDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<Cinema> getCinemasByCinemaChainID(int cinemaChainID) {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM cinema WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cinemas.add(extractCinemaFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

    public Cinema getCinemaByID(int cinemaID) {
        String sql = "SELECT * FROM cinema WHERE CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractCinemaFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addCinema(Cinema cinema) {
        String sql = "INSERT INTO cinema (CinemaChainID, Name, Address, Province, District, Commune) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinema.getCinemaChainID());
            pstmt.setString(2, cinema.getName());
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

    public boolean updateCinema(Cinema cinema) {
        String sql = "UPDATE cinema SET CinemaChainID = ?, Name = ?, Address = ?, Province = ?, District = ?, Commune = ? WHERE CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinema.getCinemaChainID());
            pstmt.setString(2, cinema.getName());
            pstmt.setString(3, cinema.getAddress());
            pstmt.setString(4, cinema.getProvince());
            pstmt.setString(5, cinema.getDistrict());
            pstmt.setString(6, cinema.getCommune());
            pstmt.setInt(7, cinema.getCinemaID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCinema(int cinemaID) {
    try {
        connection.setAutoCommit(false);
        
        // Cập nhật CinemaID của các phim liên quan thành NULL
        String updateMoviesSQL = "UPDATE movie SET CinemaID = NULL WHERE CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(updateMoviesSQL)) {
            pstmt.setInt(1, cinemaID);
            pstmt.executeUpdate();
        }
        
        // Xóa các bản ghi liên quan trong bảng movieslot 
        String deleteMovieSlotSQL = "DELETE FROM movieslot WHERE RoomID IN (SELECT RoomID FROM room WHERE CinemaID = ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteMovieSlotSQL)) {
            pstmt.setInt(1, cinemaID);
            pstmt.executeUpdate();
        }
        
        // Xóa các bản ghi trong bảng roomtype liên quan đến các phòng của rạp
        String deleteRoomTypeSQL = "DELETE FROM roomtype WHERE RoomID IN (SELECT RoomID FROM room WHERE CinemaID = ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteRoomTypeSQL)) {
            pstmt.setInt(1, cinemaID);
            pstmt.executeUpdate();
        }
        
        // Xóa các ghế liên quan đến các phòng của rạp
        String deleteSeatsSQL = "DELETE FROM seat WHERE RoomID IN (SELECT RoomID FROM room WHERE CinemaID = ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteSeatsSQL)) {
            pstmt.setInt(1, cinemaID);
            pstmt.executeUpdate();
        }
        
        // Xóa các phòng liên quan đến rạp
        String deleteRoomsSQL = "DELETE FROM room WHERE CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteRoomsSQL)) {
            pstmt.setInt(1, cinemaID);
            pstmt.executeUpdate();
        }
        
        // Xóa rạp chiếu
        String deleteCinemaSQL = "DELETE FROM cinema WHERE CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteCinemaSQL)) {
            pstmt.setInt(1, cinemaID);
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        }
    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    private Cinema extractCinemaFromResultSet(ResultSet rs) throws SQLException {
        Cinema cinema = new Cinema();
        cinema.setCinemaID(rs.getInt("CinemaID"));
        cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
        cinema.setName(rs.getString("Name"));
        cinema.setAddress(rs.getString("Address"));
        cinema.setProvince(rs.getString("Province"));
        cinema.setDistrict(rs.getString("District"));
        cinema.setCommune(rs.getString("Commune"));
        return cinema;
    }
    public int getTotalCinemas() {
        String sql = "SELECT COUNT(*) FROM cinema";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    
    public int getTotalCinemasByCinemaChainID(int cinemaChainID) {
        String sql = "SELECT COUNT(*) FROM cinema WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean isCinemaExist(String name, String address, String province, String district, String commune, int cinemaChainID) {
    String sql = "SELECT COUNT(*) FROM cinema WHERE Name = ? AND Address = ? AND Province = ? AND District = ? AND Commune = ? AND CinemaChainID = ?";
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setString(2, address);
        pstmt.setString(3, province);
        pstmt.setString(4, district);
        pstmt.setString(5, commune);
        pstmt.setInt(6, cinemaChainID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean isCinemaExistExcludeSelf(int cinemaID, String name, String address, String province, String district, String commune, int cinemaChainID) {
    String sql = "SELECT COUNT(*) FROM cinema WHERE Name = ? AND Address = ? AND Province = ? AND District = ? AND Commune = ? AND CinemaChainID = ? AND CinemaID != ?";
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
        pstmt.setString(1, name);
        pstmt.setString(2, address);
        pstmt.setString(3, province);
        pstmt.setString(4, district);
        pstmt.setString(5, commune);
        pstmt.setInt(6, cinemaChainID);
        pstmt.setInt(7, cinemaID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean isCinemaExist(String name, String address) throws SQLException {
    String sql = "SELECT COUNT(*) FROM cinema WHERE Name = ? AND Address = ?";
    try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
        statement.setString(1, name);
        statement.setString(2, address);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
    }
    return false;
}
public List<Cinema> getAllCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM Cinema";
        
        try (
             PreparedStatement stmt = this.connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setName(rs.getString("Name"));
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
                cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu cần
        }
        
        return cinemas;
    }
public List<Cinema> getCinemasShowingMovie(int movieId) {
    List<Cinema> cinemas = new ArrayList<>();
    String sql = "SELECT DISTINCT c.* FROM cinema c " +
                 "JOIN room r ON c.CinemaID = r.CinemaID " +
                 "JOIN movieslot ms ON r.RoomID = ms.RoomID " +
                 "WHERE ms.MovieID = ? AND ms.StartTime > NOW()";
    try (
         PreparedStatement stmt = this.connection.prepareStatement(sql)) {
        stmt.setInt(1, movieId);
        try (ResultSet rs = stmt.executeQuery()) {
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
}