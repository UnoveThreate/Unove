/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.admin.movieDiscount;

import database.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import model.discount.MovieDiscount;

/**
 *
 * @author Kaan
 */
public class MovieDiscountDAO extends MySQLConnect {

    public MovieDiscountDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    /**
     * Inserts a new movie discount into the database.
     *
     * @param discount MovieDiscount object containing discount details.
     * @return true if the discount was created successfully, false otherwise.
     */
    public boolean createDiscount(MovieDiscount discount) {
        String sql = "INSERT INTO MovieDiscount (discountCode, DiscountPercentage, StartDate, EndDate, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, discount.getDiscountCode());
            stmt.setBigDecimal(2, discount.getDiscountPercentage());
            stmt.setDate(3, discount.getStartDate());
            stmt.setDate(4, discount.getEndDate());
            stmt.setString(5, discount.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all movie discounts from the database.
     *
     * @return List of MovieDiscount objects.
     */
    public List<MovieDiscount> getAllDiscounts() {
        List<MovieDiscount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM MovieDiscount";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MovieDiscount discount = new MovieDiscount(
                        rs.getInt("DiscountID"),
                        rs.getString("discountCode"),
                        rs.getBigDecimal("DiscountPercentage"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("Status")
                );
                discounts.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    /**
     * Updates the status of a movie discount in the database.
     *
     * @param newStatus New status to set (e.g., 'active', 'inactive').
     * @return true if the update was successful, false otherwise.
     */
    // Updated method to update discount status using a MovieDiscount object
    public boolean updateDiscountStatus(MovieDiscount discount) {
        String sql = "UPDATE MovieDiscount SET Status = ? WHERE DiscountID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, discount.getStatus());
            stmt.setInt(2, discount.getDiscountID());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves active discounts for a specific movie.
     *
     * @param movieID ID of the movie.
     * @return List of active MovieDiscount objects for the specified movie.
     */
    public List<MovieDiscount> getActiveDiscountsByMovieID(int movieID) {
        List<MovieDiscount> activeDiscounts = new ArrayList<>();
        String sql = "SELECT * FROM MovieDiscount WHERE MovieID = ? AND Status = 'active'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, movieID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MovieDiscount discount = new MovieDiscount(
                        rs.getInt("DiscountID"),
                        rs.getString("discountCode"),
                        rs.getBigDecimal("DiscountPercentage"),
                        rs.getDate("StartDate"),
                        rs.getDate("EndDate"),
                        rs.getString("Status")
                );
                activeDiscounts.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeDiscounts;
    }

    public boolean isDiscountCodeUnique(String discountCode) {
        String query = "SELECT COUNT(*) FROM MovieDiscount WHERE discountCode = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, discountCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // true if count is 0 (unique)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
