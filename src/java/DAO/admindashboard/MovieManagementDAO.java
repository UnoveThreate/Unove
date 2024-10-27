package DAO.admindashboard;

import model.moviemanagementadmin.Movie;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieManagementDAO extends MySQLConnect {

    public MovieManagementDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                movies.add(extractMovieFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public int getTotalMovies() {
        String sql = "SELECT COUNT(*) FROM movie";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Movie getMovieByID(int movieID) {
        String sql = "SELECT * FROM movie WHERE MovieID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractMovieFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO movie (Title, Synopsis, DatePublished, ImageURL, Rating, Country, LinkTrailer, CinemaID, Type, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setDate(3, new java.sql.Date(movie.getDatePublished().getTime()));
            pstmt.setString(4, movie.getImageURL());
            pstmt.setFloat(5, movie.getRating());
            pstmt.setString(6, movie.getCountry());
            pstmt.setString(7, movie.getLinkTrailer());
            pstmt.setInt(8, movie.getCinemaID());
            pstmt.setString(9, movie.getType());
            pstmt.setBoolean(10, movie.isStatus());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public boolean updateMovie(Movie movie, List<Integer> genreIDs) {
    String updateMovieSQL = "UPDATE movie SET Title = ?, Synopsis = ?, DatePublished = ?, ImageURL = ?, Rating = ?, Country = ?, LinkTrailer = ?, CinemaID = ?, Type = ?, Status = ? WHERE MovieID = ?";
    String deleteGenresSQL = "DELETE FROM movieingenre WHERE MovieID = ?";
    String insertGenreSQL = "INSERT INTO movieingenre (MovieID, GenreID) VALUES (?, ?)";

    try {
        connection.setAutoCommit(false);

        // Cập nhật thông tin phim
        try (PreparedStatement pstmt = this.connection.prepareStatement(updateMovieSQL)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setDate(3, new java.sql.Date(movie.getDatePublished().getTime()));
            pstmt.setString(4, movie.getImageURL());
            pstmt.setFloat(5, movie.getRating());
            pstmt.setString(6, movie.getCountry());
            pstmt.setString(7, movie.getLinkTrailer());
            pstmt.setInt(8, movie.getCinemaID());
            pstmt.setString(9, movie.getType());
            pstmt.setBoolean(10, movie.isStatus());
            pstmt.setInt(11, movie.getMovieID());
            pstmt.executeUpdate();
        }

        // Xóa tất cả các thể loại hiện tại của phim
        try (PreparedStatement pstmt = connection.prepareStatement(deleteGenresSQL)) {
            pstmt.setInt(1, movie.getMovieID());
            pstmt.executeUpdate();
        }

        // Thêm các thể loại mới
        try (PreparedStatement pstmt = connection.prepareStatement(insertGenreSQL)) {
            for (int genreID : genreIDs) {
                pstmt.setInt(1, movie.getMovieID());
                pstmt.setInt(2, genreID);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }

        connection.commit();
        return true;
    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

   public boolean deleteMovie(int movieID) {
    try {
        connection.setAutoCommit(false);
        
        // xóa dữ liệu liên quan trong bảng movieingenre
        String deleteMovieInGenreSQL = "DELETE FROM movieingenre WHERE MovieID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteMovieInGenreSQL)) {
            pstmt.setInt(1, movieID);
            pstmt.executeUpdate();
        }
        
       
        String deleteMovieSQL = "DELETE FROM movie WHERE MovieID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(deleteMovieSQL)) {
            pstmt.setInt(1, movieID);
            int affectedRows = pstmt.executeUpdate();
            
            connection.commit();
            return affectedRows > 0;
        }
    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } finally {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    public boolean updateMovieStatus(int movieID, boolean status) {
        String sql = "UPDATE movie SET Status = ? WHERE MovieID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setBoolean(1, status);
            pstmt.setInt(2, movieID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Movie extractMovieFromResultSet(ResultSet rs) throws SQLException {
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
        movie.setType(rs.getString("Type"));
        movie.setStatus(rs.getBoolean("Status"));
        return movie;
    }
     public List<Movie> getMovies(int offset, int limit, String sortBy, String sortOrder) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie ORDER BY " + sortBy + " " + sortOrder + " LIMIT ? OFFSET ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(extractMovieFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // tìm kiếm phim
    public List<Movie> searchMovies(String keyword, int offset, int limit, String sortBy, String sortOrder) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie WHERE Title LIKE ? OR Synopsis LIKE ? ORDER BY " + sortBy + " " + sortOrder + " LIMIT ? OFFSET ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setInt(3, limit);
            pstmt.setInt(4, offset);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    movies.add(extractMovieFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // đếm tổng số phim tìm kiếm được
    public int getTotalSearchResults(String keyword) {
        String sql = "SELECT COUNT(*) FROM movie WHERE Title LIKE ? OR Synopsis LIKE ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
