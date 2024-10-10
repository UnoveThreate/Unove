/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.cinemaChainOwnerDAO;

/**
 *
 * @author nguyendacphong
 */
import database.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import model.owner.Genre;

public class GenreDAO extends MySQLConnect {

    public GenreDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }
     // Thêm mới Genre
    public boolean createGenre(Genre genre) throws SQLException {
        String sql = "INSERT INTO `Genre` (`GenreName`) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, genre.getGenreName());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

    // Cập nhật Genre
    public boolean updateGenre(Genre genre) throws SQLException {
        String sql = "UPDATE `Genre` SET `GenreName` = ? WHERE `GenreID` = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, genre.getGenreName());
            pstmt.setInt(2, genre.getGenreID());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa Genre
    public boolean deleteGenre(int genreID) throws SQLException {
        String sql = "DELETE FROM `Genre` WHERE `GenreID` = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, genreID);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy Genre theo ID
    public Genre getGenreById(int genreID) throws SQLException {
        Genre genre = null;
        String sql = "SELECT * FROM `Genre` WHERE `GenreID` = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, genreID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                genre = new Genre();
                genre.setGenreID(rs.getInt("GenreID"));
                genre.setGenreName(rs.getString("GenreName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return genre;
    }
}
