/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package DAO;

import database.MySQLConnect;
import model.Cinema;
import model.CinemaReview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kaan
 */
public class CinemaDAO {

    private MySQLConnect mySQLConnect;

    public CinemaDAO(MySQLConnect mySQLConnect) {
        this.mySQLConnect = mySQLConnect;
    }

    public Cinema getCinemaById(int id) {
        Cinema cinema = null;
        String sql = "SELECT * FROM Cinema WHERE CinemaID = ?";

        try (Connection connection = mySQLConnect.connect(null); // Thay null bằng ServletContext nếu cần
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cinema = new Cinema(
                        rs.getInt("CinemaID"),
                        rs.getInt("CinemaChainID"),
                        rs.getString("Address"),
                        rs.getString("Province"),
                        rs.getString("District"),
                        rs.getString("Commune")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý SQLException
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý các ngoại lệ khác
        }
        return cinema;
    }

    public List<CinemaReview> getReviewsByCinemaId(int cinemaId) {
        List<CinemaReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM CinemaReview WHERE CinemaID = ?";

        try (Connection connection = mySQLConnect.connect(null); // Thay null bằng ServletContext nếu cần
                 PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reviews.add(new CinemaReview(
                        rs.getInt("CinemaReviewID"),
                        rs.getInt("UserID"),
                        rs.getInt("CinemaID"),
                        rs.getInt("Rating"),
                        rs.getTimestamp("TimeCreated"),
                        rs.getString("Content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý SQLException
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý các ngoại lệ khác
        }
        return reviews;
    }
}
