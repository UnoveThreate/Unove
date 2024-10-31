/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.payment;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Cinema;
import model.CinemaChain;
import model.Movie;
import model.MovieSlot;
import model.Seat;

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

    public List<Seat> getSeatsByRoomId(int roomId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM Seat WHERE RoomID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, roomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Seat seat = mapResultSetToSeat(resultSet);
                    seat.setAvailable(checkSeatAvailability(seat.getSeatID()));
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public Seat getSeatById(int seatID) {
        String sql = "SELECT * FROM Seat WHERE SeatID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, seatID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Seat seat = mapResultSetToSeat(resultSet);
                    seat.setAvailable(checkSeatAvailability(seat.getSeatID()));
                    return seat;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Seat mapResultSetToSeat(ResultSet resultSet) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatID(resultSet.getInt("SeatID"));
        seat.setRoomID(resultSet.getInt("RoomID"));
        seat.setName(resultSet.getString("Name"));
        seat.setCoordinateX(resultSet.getInt("CoordinateX"));
        seat.setCoordinateY(resultSet.getInt("CoordinateY"));
        //seat.setPrice(resultSet.getDouble("Price"));
        return seat;
    }

    public boolean checkSeatAvailability(int seatID) {
        String sql = "SELECT COUNT(*) FROM Ticket WHERE SeatID = ? AND Status = 'Đã đặt'";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, seatID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAvailableDates(int cinemaID) {
        List<String> dates = new ArrayList<>();
        String sql = "SELECT DISTINCT DATE_FORMAT(StartTime, '%Y:%d:%m') as show_date FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "WHERE r.CinemaID = ? AND StartTime >= CURDATE() "
                + "ORDER BY show_date LIMIT 7";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    dates.add(rs.getString("show_date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public Movie getMovieByMovieSlotID(int movieSlotID) {
        Movie movie = null;
        String sql = "SELECT m.MovieID, m.Title, m.Synopsis, m.DatePublished, m.ImageURL, m.Rating, m.Country, m.LinkTrailer "
                + "FROM Movie m "
                + "JOIN MovieSlot ms ON ms.MovieID = m.MovieID "
                + "WHERE ms.MovieSlotID = ?";

        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {

            pstmt.setInt(1, movieSlotID);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.setMovieID(resultSet.getInt("MovieID"));
                movie.setTitle(resultSet.getString("Title"));
                movie.setSynopsis(resultSet.getString("Synopsis"));
                movie.setDatePublished(resultSet.getDate("DatePublished"));
                movie.setImageURL(resultSet.getString("ImageURL"));
                movie.setRating(resultSet.getFloat("Rating"));
                movie.setCountry(resultSet.getString("Country"));
                movie.setLinkTrailer(resultSet.getString("LinkTrailer"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    // Method to get cinema details by movieSlotID
    public Cinema getCinemaByMovieSlot(int movieSlotID) {

        String sql = "SELECT c.CinemaID, c.Name AS CinemaName, c.Address, c.Province, c.District, c.Commune "
                + "FROM Cinema c "
                + "JOIN Movie m ON c.CinemaID = m.CinemaID "
                + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
                + "WHERE ms.MovieSlotID = ?";
        Cinema cinema = new Cinema();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieSlotID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cinema.setCinemaID(resultSet.getInt("CinemaID"));
                    cinema.setName(resultSet.getString("CinemaName"));
                    cinema.setAddress(resultSet.getString("Address"));
                    cinema.setProvince(resultSet.getString("Province"));
                    cinema.setDistrict(resultSet.getString("District"));
                    cinema.setCommune(resultSet.getString("Commune"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinema; // Return the list of cinemas for the given MovieSlotID
    }

    public boolean addCanteenItemToOrder(int orderID, int canteenItemID, int quantity) {
        String sql = "INSERT INTO orderCanteenItem (orderID, canteenItemID, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderID);
            statement.setInt(2, canteenItemID);
            statement.setInt(3, quantity);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất một bản ghi được chèn
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
