/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.payment;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Cinema;
import model.CinemaChain;
import model.Movie;
import model.MovieSlot;

/**
 *
 * @author DELL
 */
public class PaymentDAO extends MySQLConnect {

    public PaymentDAO(ServletContext context) throws Exception {
        super();
        connect(context); // Establish the connection from MySQLConnect
    }

    public Cinema getCinemaById(int cinemaID) throws SQLException {
        Cinema cinema = null;
        String sql = "SELECT * FROM `Cinema` WHERE `CinemaID` = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cinema = new Cinema();
                cinema.setName(rs.getString("Name"));
                cinema.setCinemaID(rs.getInt("CinemaID"));
                cinema.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinema.setAddress(rs.getString("Address"));
                cinema.setProvince(rs.getString("Province"));
                cinema.setDistrict(rs.getString("District"));
                cinema.setCommune(rs.getString("Commune"));
            }
        }
        return cinema;
    }

    public CinemaChain getCinemaChainByUserID(int userID) throws SQLException {
        String sql = "SELECT * FROM CinemaChain WHERE UserID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new CinemaChain(rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Information"), rs.getInt("UserID"));
        }
        return null;
    }

    public MovieSlot getMovieSlotById(int movieSlotID) {
        MovieSlot movieSlot = null;
        String sql = "SELECT * FROM MovieSlot WHERE MovieSlotID = ?";
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

    public Movie getMovieByCinemaIDAndMovieID(int cinemaID, int movieID) throws SQLException {
        Movie movie = null;
        String sqlQuery = "SELECT * FROM Movie WHERE CinemaID = ? AND MovieID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, cinemaID);
            pstmt.setInt(2, movieID);

            // Debugging log
            System.out.println("Executing query: " + sqlQuery + " with CinemaID: " + cinemaID + " and MovieID: " + movieID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve data from ResultSet
                    String title = rs.getString("Title");
                    String synopsis = rs.getString("Synopsis");
                    Date datePublished = rs.getDate("DatePublished");  // java.sql.Date, ensure compatibility with Movie class
                    String imageURL = rs.getString("ImageURL");
                    float rating = rs.getFloat("Rating");
                    String country = rs.getString("Country");
                    String linkTrailer = rs.getString("LinkTrailer");

                    // Create a new Movie object using the constructor
                    movie = new Movie();
                    movie.setMovieID(rs.getInt("MovieID"));
                    movie.setCinemaID(cinemaID);
                    movie.setLinkTrailer(linkTrailer);
                    movie.setRating(rating);
                    movie.setDatePublished(datePublished);
                    movie.setCountry(country);
                    movie.setImageURL(imageURL);
                    movie.setTitle(title);
                    movie.setSynopsis(synopsis);

                   

                    // Set genres to the movie obje               
                } else {
                    System.err.println("No movie found for CinemaID: " + cinemaID + " and MovieID: " + movieID);
                }
            }
        } catch (SQLException e) {
            // Handle exceptions properly
            e.printStackTrace();
            throw new SQLException("Error while fetching movie data.", e);
        }

        return movie; // Return the Movie object (or null if not found)
    }

}
