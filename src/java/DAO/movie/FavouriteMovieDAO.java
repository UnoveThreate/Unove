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
public class FavouriteMovieDAO extends UserDAO {
    
    public FavouriteMovieDAO(ServletContext context) throws Exception {
        super(context);
    }
    
    public boolean isFavoritedMovie(int userID, int movieID) throws SQLException {
        String sqlQuery = "select * from FavoriteFilm where UserID = " + userID + " and MovieID = " + movieID;
        ResultSet rs = super.getResultSet(sqlQuery);
        return rs.next();
    }
    
    public List<Movie> queryFavouriteMovies(int userID) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sqlQuery = "select Movie.MovieID, CinemaID, Title, Synopsis, DatePublished, ImageURL, Rating, Country, Status from FavoriteFilm\n" + 
                "join Movie on FavoriteFilm.MovieID = Movie.MovieID\n" + 
                "where UserID = " + userID;
        ResultSet rs = super.getResultSet(sqlQuery);
        while(rs.next()) {
            List<String> genres = super.getGenresFromMovieID(rs.getInt("MovieID"));
            Movie movie = new Movie(rs.getInt("MovieID"), rs.getString("Title"), rs.getString("Synopsis"), rs.getDate("DatePublished"), rs.getString("ImageURL"), rs.getFloat("Rating"), rs.getString("Status"), rs.getString("Country"),genres);
            movies.add(movie);
        }
        return movies;
    }
    
    public int insertFavouriteMovie(int userID, int movieID, String favoritedAt) throws SQLException {
        String sql = "INSERT INTO FavoriteFilm (UserID, MovieID, FavoritedAt) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, movieID);
        ps.setString(3, favoritedAt);
        return ps.executeUpdate();
    }
    
    public int deleteFavouriteMovie(int userID, int movieID) throws SQLException {
        String sql = "DELETE FavoriteFilm WHERE UserID = ? AND MovieID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);    
        ps.setInt(1, userID);
        ps.setInt(2, movieID);
        return ps.executeUpdate();
    }
}
