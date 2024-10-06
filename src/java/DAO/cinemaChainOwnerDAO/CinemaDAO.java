/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.cinemaChainOwnerDAO;

import database.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import model.Cinema;

public class CinemaDAO extends MySQLConnect {

    public CinemaDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // Thêm mới Cinema
    public boolean createCinema(Cinema cinema) throws SQLException {
        String sql = "INSERT INTO `Cinema` (`CinemaChainID`, `Name`, `Address`, `Province`, `District`, `Commune`) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinema.getCinemaChainID());
            pstmt.setString(2, cinema.getName()); // New name field
            pstmt.setString(3, cinema.getAddress());
            pstmt.setString(4, cinema.getProvince());
            pstmt.setString(5, cinema.getDistrict());
            pstmt.setString(6, cinema.getCommune());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả Cinemas
    public List<Cinema> getAllCinemas() throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM `Cinema`";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinema.setName(rs.getString("Name")); // Thêm trường Name
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

    // Cập nhật Cinema
    public boolean updateCinema(Cinema cinema) throws SQLException {
        String sql = "UPDATE `Cinema` SET `Name` = ?, `Address` = ?, `Province` = ?, `District` = ?, `Commune` = ? WHERE `CinemaID` = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cinema.getName()); // Thêm trường Name
            pstmt.setString(2, cinema.getAddress());
            pstmt.setString(3, cinema.getProvince());
            pstmt.setString(4, cinema.getDistrict());
            pstmt.setString(5, cinema.getCommune());
            pstmt.setInt(6, cinema.getCinemaID());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Cinema
    public boolean deleteCinema(int cinemaID) throws SQLException {
        String sql = "DELETE FROM `Cinema` WHERE `CinemaID` = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Lấy Cinema theo ID
    public Cinema getCinemaById(int cinemaID) throws SQLException {
        Cinema cinema = null;
        String sql = "SELECT * FROM `Cinema` WHERE `CinemaID` = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cinema = new Cinema();
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinema.setName(rs.getString("Name")); // Thêm trường Name
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return cinema;
    }

    // Lấy tất cả Cinemas theo CinemaChainID
    public List<Cinema> getCinemasByCinemaChainID(int cinemaChainID) throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM `Cinema` WHERE `CinemaChainID` = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinema.setName(rs.getString("Name")); // Thêm trường Name
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return cinemas;
    }
}
