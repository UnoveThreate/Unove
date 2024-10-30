/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.movie;

import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class FavouriteMoviesDAO extends UserDAO {

    public FavouriteMoviesDAO(ServletContext context) throws Exception {
        super(context);
    }

    public List<Movie> queryFavouriteMovies(int userID) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sqlQuery = "SELECT m.MovieID, m.Title, m.Synopsis, m.DatePublished, m.ImageURL, m.Rating, m.Country, m.Status "
                + "FROM FavoriteFilm f JOIN Movie m ON f.MovieID = m.MovieID WHERE f.UserID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("MovieID"),
                        rs.getString("Title"),
                        rs.getString("Synopsis"),
                        rs.getDate("DatePublished"),
                        rs.getString("ImageURL"),
                        rs.getFloat("Rating"),
                        rs.getString("Status"),
                        rs.getString("Country"),
                        getGenresFromMovieID(rs.getInt("MovieID"))
                );
                movies.add(movie);
            }
        }
        return movies;
    }

    public int insertFavouriteMovie(int userID, int movieID, String favoritedAt) throws SQLException {
        String sql = "INSERT INTO FavoriteFilm (UserID, MovieID, FavoritedAt) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);
            ps.setString(3, favoritedAt);
            return ps.executeUpdate();
        }
    }

    public int deleteFavouriteMovie(int userID, int movieID) throws SQLException {
        String sql = "DELETE FROM FavoriteFilm WHERE UserID = ? AND MovieID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);
            return ps.executeUpdate();
        }
    }
}
