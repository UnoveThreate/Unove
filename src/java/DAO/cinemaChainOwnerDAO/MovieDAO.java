package DAO.cinemaChainOwnerDAO;

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

    // Method to get a movie by CinemaID and MovieID (only movies with status = TRUE)
    public Movie getMovieByCinemaIDAndMovieID(int cinemaID, int movieID) throws SQLException {
        Movie movie = null;
        String sqlQuery = "SELECT * FROM Movie WHERE CinemaID = ? AND MovieID = ? AND Status = TRUE";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, cinemaID);
            pstmt.setInt(2, movieID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie();
                    movie.setMovieID(rs.getInt("MovieID"));
                    movie.setCinemaID(cinemaID);
                    movie.setTitle(rs.getString("Title"));
                    movie.setSynopsis(rs.getString("Synopsis"));
                    movie.setDatePublished(rs.getDate("DatePublished"));
                    movie.setImageURL(rs.getString("ImageURL"));
                    movie.setRating(rs.getFloat("Rating"));
                    movie.setCountry(rs.getString("Country"));
                    movie.setLinkTrailer(rs.getString("LinkTrailer"));
                    movie.setStatus(rs.getBoolean("Status"));
                    movie.setType(rs.getString("Type"));

                    List<Genre> genres = getGenresByMovieID(movieID);
                    movie.setGenres(genres);
                }
            }
        }
        return movie;
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
    public List<Movie> getAllMovies(int cinemaID) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Movie WHERE CinemaID = ? AND Status = TRUE";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaID);
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

    // Method to create a new movie (default status is TRUE)
    public void createMovie(Movie movie, String[] genreIDs) throws SQLException {
        String sql = "INSERT INTO Movie (Title, Synopsis, DatePublished, ImageURL, Rating, Country, LinkTrailer, CinemaID, Status, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setDate(3, new java.sql.Date(movie.getDatePublished().getTime()));
            pstmt.setString(4, movie.getImageURL());
            pstmt.setFloat(5, movie.getRating());
            pstmt.setString(6, movie.getCountry());
            pstmt.setString(7, movie.getLinkTrailer());
            pstmt.setInt(8, movie.getCinemaID());
            pstmt.setString(9, movie.getType());

            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int movieID = generatedKeys.getInt(1);

                if (genreIDs != null) {
                    for (String genreID : genreIDs) {
                        addMovieGenre(movieID, Integer.parseInt(genreID));
                    }
                }
            }
        }
    }
// Method to update movie details (keep status and rating unchanged, only update other fields)

    public void updateMovie(Movie movie, String[] genreIDs) throws SQLException {
        // Lấy giá trị rating hiện tại từ cơ sở dữ liệu
        String getCurrentRatingSQL = "SELECT Rating FROM Movie WHERE MovieID = ?";
        float currentRating = 0.0f;

        try (PreparedStatement pstmt = connection.prepareStatement(getCurrentRatingSQL)) {
            pstmt.setInt(1, movie.getMovieID());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                currentRating = rs.getFloat("Rating");
            }
        }

        // Cập nhật các trường khác trừ rating
        String updateSQL = "UPDATE Movie SET Title = ?, Synopsis = ?, DatePublished = ?, ImageURL = ?, Country = ?, LinkTrailer = ?, Type = ? WHERE MovieID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setDate(3, new java.sql.Date(movie.getDatePublished().getTime()));
            pstmt.setString(4, movie.getImageURL());
            pstmt.setString(5, movie.getCountry());
            pstmt.setString(6, movie.getLinkTrailer());
            pstmt.setString(7, movie.getType());
            pstmt.setInt(8, movie.getMovieID());

            pstmt.executeUpdate();
        }

        // Cập nhật thể loại của phim
        deleteMovieGenres(movie.getMovieID());

        if (genreIDs != null) {
            for (String genreID : genreIDs) {
                addMovieGenre(movie.getMovieID(), Integer.parseInt(genreID));
            }
        }
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

    // Method to 'delete' a movie (set status to FALSE)
    public void deleteMovie(int movieID) throws SQLException {
        String sql = "UPDATE Movie SET Status = FALSE WHERE MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            pstmt.executeUpdate();
        }
    }

    // Thêm thể loại cho movie
    private void addMovieGenre(int movieID, int genreID) throws SQLException {
        String sql = "INSERT INTO MovieInGenre (MovieID, GenreID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            pstmt.setInt(2, genreID);
            pstmt.executeUpdate();
        }
    }

    // Xóa thể loại của movie
    private void deleteMovieGenres(int movieID) throws SQLException {
        String sql = "DELETE FROM MovieInGenre WHERE MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            pstmt.executeUpdate();
        }
    }

    public String getCinemaNameByID(int cinemaID) throws SQLException {
        String cinemaName = null;
        String query = "SELECT name FROM Cinema WHERE cinemaID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cinemaID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cinemaName = rs.getString("name");
                }
            }
        }
        return cinemaName;
    }

    public List<Movie> searchMoviesByTitle(String searchQuery) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
