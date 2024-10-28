package DAO.landingPageMovieDAO;

import database.MySQLConnect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import model.owner.Movie;
import model.owner.Genre;

public class MovieDAO extends MySQLConnect {

    public MovieDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public Movie getMovieByID(int movieID) throws SQLException {
        Movie movie = null;
        String sql = "SELECT * FROM Movie WHERE MovieID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movie = new Movie();
                movie.setMovieID(rs.getInt("MovieID"));
                movie.setTitle(rs.getString("Title"));
                movie.setSynopsis(rs.getString("Synopsis"));
                movie.setDatePublished(rs.getDate("DatePublished"));
                movie.setImageURL(rs.getString("ImageURL"));
                movie.setRating(rs.getFloat("Rating"));
                movie.setCountry(rs.getString("Country"));
                movie.setLinkTrailer(rs.getString("LinkTrailer"));
                movie.setCinemaID(rs.getInt("CinemaID"));
                movie.setType(rs.getString("Type"));
                // Nếu cần thiết, bạn có thể lấy thông tin thể loại ở đây hoặc sau này.
                // Ví dụ: movie.setGenres(getGenresByMovieID(movieID));

            }
        }

        return movie;
    }

    // Method to get all movies by CinemaID (only movies with status = TRUE)
    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE Status = TRUE";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
                movie.setLinkTrailer(rs.getString("LinkTrailer"));
                movie.setCinemaID(rs.getInt("CinemaID"));
                movie.setStatus(rs.getBoolean("Status"));
                movie.setType(rs.getString("Type"));

                List<Genre> genres = getGenresByMovieID(movie.getMovieID());
                movie.setGenres(genres);

                movies.add(movie);
            }
        }
        return movies;
    }

    // Method to get genres by movie ID
    public List<Genre> getGenresByMovieID(int movieID) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT g.GenreName,g.GenreID FROM Genre g "
                + "JOIN MovieInGenre mig ON g.GenreID = mig.GenreID "
                + "WHERE mig.MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rs.getInt("GenreID"));
                genre.setGenreName(rs.getString("GenreName"));
                genres.add(genre); // Add genre names to the list
            }
        }
        return genres; // Return the list of genres
    }
    // Lấy tất cả Genres

    public List<Genre> getAllGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM `Genre`";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setGenreID(rs.getInt("GenreID"));
                genre.setGenreName(rs.getString("GenreName"));
                genres.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public float getRatingByMovieID(int movieID) throws SQLException {
        String sql = "SELECT Rating FROM Movie WHERE MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("Rating");
            }
        }
        return 0; // Trường hợp không tìm thấy rating
    }

    //search movie theo tên movie
    public List<Movie> searchMoviesByTitle(String title) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE Title LIKE ? AND Status = TRUE"; // Tìm kiếm phim còn hoạt động

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%"); // Tìm kiếm với ký tự đại diện

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
                    movie.setStatus(rs.getBoolean("Status")); // Cập nhật trạng thái
                    movie.setType(rs.getString("Type"));

                    List<Genre> genres = getGenresByMovieID(movie.getMovieID()); // Lấy thể loại phim
                    movie.setGenres(genres);

                    movies.add(movie);
                }
            }
        }

        return movies;
    }

    //lọc movie theo genre
    public List<Movie> getMoviesByGenre(String genreName) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.MovieID, m.Title, m.Synopsis, m.DatePublished, m.ImageURL, m.Rating, m.Country, m.LinkTrailer, m.CinemaID, m.Status, g.GenreName "
                + "FROM movie m "
                + "JOIN movieingenre mi ON m.MovieID = mi.MovieID "
                + "JOIN genre g ON mi.GenreID = g.GenreID "
                + "WHERE m.Status = TRUE AND g.GenreName = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, genreName);
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
                    movie.setStatus(rs.getBoolean("Status"));
                    movies.add(movie);
                }
            }
        }

        return movies;
    }

    public List<Movie> getMoviesByCountry(String countryName) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie WHERE Status = TRUE AND Country = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, countryName);
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
                    movie.setStatus(rs.getBoolean("Status"));
                    movies.add(movie);
                }
            }
        }

        return movies;
    }

}
