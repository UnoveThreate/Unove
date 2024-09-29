package DAOHuy;

import model.Movie;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO extends MySQLConnect {

    public MovieDAO(ServletContext context) throws Exception {
        super(); 
        connect((ServletContext) context);
    }

    public List<Movie> getMoviesByCinema(int cinemaID) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT DISTINCT m.* FROM Movie m " +
                     "JOIN MovieSlot ms ON m.MovieID = ms.MovieID " +
                     "JOIN Room r ON ms.RoomID = r.RoomID " +
                     "WHERE r.CinemaID = ?";

        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, cinemaID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setMovieID(rs.getInt("MovieID"));
                    movie.setTitle(rs.getString("Title"));
                    movie.setSynopsis(rs.getString("Synopsis"));
                    movie.setDatePublished(rs.getDate("DatePublished"));
                    movie.setImageURL(rs.getString("ImageURL"));
                    movie.setRating(rs.getFloat("Rating"));
                    movie.setCountry(rs.getString("Country"));
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies; 
    }

    public boolean insertMovie(Movie movie) {
        String sql = "INSERT INTO Movie (Title, Synopsis, DatePublished, ImageURL, Rating, Country) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setDate(3, new java.sql.Date(movie.getDatePublished().getTime()));
            pstmt.setString(4, movie.getImageURL());
            pstmt.setFloat(5, movie.getRating());
            pstmt.setString(6, movie.getCountry());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
}