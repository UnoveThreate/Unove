package DAOSchedule;

import model.Movie;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovieScheduleDAO extends MySQLConnect {
    public MovieScheduleDAO(ServletContext context) throws Exception {
        super(); 
        connect((ServletContext) context);
    }

    public List<Movie> getMoviesByCinema(int cinemaID) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT DISTINCT m.* FROM Movie m " +
                     "JOIN MovieSlot ms ON m.MovieID = ms.MovieID " +
                     "JOIN Room r ON ms.RoomID = r.RoomID " +
                     "WHERE r.CinemaID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
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
                    movie.setLinkTrailer(rs.getString("LinkTrailer"));
                    movie.setCinemaID(rs.getInt("CinemaID"));
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Movie> getMoviesByCinemaAndDate(int cinemaID, LocalDate date) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT DISTINCT m.* FROM Movie m " +
                     "JOIN MovieSlot ms ON m.MovieID = ms.MovieID " +
                     "JOIN Room r ON ms.RoomID = r.RoomID " +
                     "WHERE r.CinemaID = ? AND DATE(ms.StartTime) = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
            pstmt.setDate(2, java.sql.Date.valueOf(date));
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
                    movie.setLinkTrailer(rs.getString("LinkTrailer"));
                    movie.setCinemaID(rs.getInt("CinemaID"));
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public boolean insertMovie(Movie movie) {
        String sql = "INSERT INTO Movie (Title, Synopsis, DatePublished, ImageURL, Rating, Country, LinkTrailer, CinemaID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setDate(3, new java.sql.Date(movie.getDatePublished().getTime()));
            pstmt.setString(4, movie.getImageURL());
            pstmt.setFloat(5, movie.getRating());
            pstmt.setString(6, movie.getCountry());
            pstmt.setString(7, movie.getLinkTrailer()); 
            pstmt.setInt(8, movie.getCinemaID()); 
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
   public List<String> getMovieGenres(int movieId) {
    List<String> genres = new ArrayList<>();
    String sql = "SELECT g.GenreName FROM MovieInGenre mg " +
                 "JOIN Genre g ON mg.GenreID = g.GenreID " +
                 "WHERE mg.MovieID = ?";
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
        pstmt.setInt(1, movieId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                genres.add(rs.getString("GenreName"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return genres;
}
    
}