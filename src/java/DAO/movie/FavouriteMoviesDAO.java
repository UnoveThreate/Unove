package DAO.movie;

import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FavouriteMoviesDAO extends UserDAO {

    public FavouriteMoviesDAO(ServletContext context) throws Exception {
        super(context);
    }

    // Kiểm tra phim có được yêu thích hay không
    public boolean isFavoritedMovie(int userID, int movieID) throws SQLException {
        String sqlQuery = "SELECT * FROM FavoriteFilm WHERE UserID = ? AND MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, movieID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // Truy vấn danh sách phim yêu thích của người dùng
    public List<Movie> queryFavouriteMovies(int userID) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sqlQuery = "SELECT m.MovieID, m.Title, m.Synopsis, m.DatePublished, m.ImageURL, m.Rating, m.Country, m.Type, m.Status "
                + "FROM FavoriteFilm f JOIN Movie m ON f.MovieID = m.MovieID WHERE f.UserID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

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
        return movies;
    }

    // Thêm phim vào danh sách yêu thích
    public int insertFavouriteMovie(int userID, int movieID, String favoritedAt) throws SQLException {
        String sql = "INSERT INTO FavoriteFilm (UserID, MovieID, FavoritedAt) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, movieID);
        ps.setString(3, favoritedAt);
        return ps.executeUpdate();
    }

    // Xóa phim khỏi danh sách yêu thích
    public int deleteFavouriteMovie(int userID, int movieID) throws SQLException {
        String sql = "DELETE FROM FavoriteFilm WHERE UserID = ? AND MovieID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);
            return ps.executeUpdate();
        }
    }
}
