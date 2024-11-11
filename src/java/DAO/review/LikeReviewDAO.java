/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.review;

import database.MySQLConnect;
import static database.MySQLConnect.connect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class LikeReviewDAO extends MySQLConnect {

    public LikeReviewDAO(ServletContext context) throws Exception {
        super();
        connect(context); // Establish the connection from MySQLConnect
    }

//    public boolean toggleLike(int userID, int reviewID) throws SQLException {
//        String checkLikeSQL = "SELECT * FROM likedmoviereview WHERE UserID = ? AND MovieReviewID = ?";
//        String addLikeSQL = "INSERT INTO likedmoviereview (UserID, MovieReviewID, likeCount) VALUES (?, ?, 1)";
//        String removeLikeSQL = "DELETE FROM likedmoviereview WHERE UserID = ? AND MovieReviewID = ?";
//        String updateLikeCountSQL = "UPDATE likedmoviereview SET likeCount = likeCount + ? WHERE MovieReviewID = ?";
//
//        try (PreparedStatement checkStmt = connection.prepareStatement(checkLikeSQL)) {
//            checkStmt.setInt(1, userID);
//            checkStmt.setInt(2, reviewID);
//            ResultSet rs = checkStmt.executeQuery();
//
//            if (rs.next()) {
//                // Nếu đã thích, xóa like và giảm số lượt thích
//                try (PreparedStatement removeStmt = connection.prepareStatement(removeLikeSQL)) {
//                    removeStmt.setInt(1, userID);
//                    removeStmt.setInt(2, reviewID);
//                    removeStmt.executeUpdate();
//                }
//
//                // Giảm likeCount của reviewID
//                try (PreparedStatement updateStmt = connection.prepareStatement(updateLikeCountSQL)) {
//                    updateStmt.setInt(1, -1); // Giảm 1
//                    updateStmt.setInt(2, reviewID);
//                    updateStmt.executeUpdate();
//                }
//                return false; // "Like" removed
//            } else {
//                // Nếu chưa thích, thêm like và tăng số lượt thích
//                try (PreparedStatement addStmt = connection.prepareStatement(addLikeSQL)) {
//                    addStmt.setInt(1, userID);
//                    addStmt.setInt(2, reviewID);
//                    addStmt.executeUpdate();
//                }
//
//                // Tăng likeCount của reviewID
//                try (PreparedStatement updateStmt = connection.prepareStatement(updateLikeCountSQL)) {
//                    updateStmt.setInt(1, 1); // Tăng 1
//                    updateStmt.setInt(2, reviewID);
//                    updateStmt.executeUpdate();
//                }
//                return true; // "Like" added
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    public int getLikeCount(int reviewID) throws SQLException {
//        String sql = "SELECT likeCount FROM likedmoviereview WHERE MovieReviewID = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, reviewID);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("likeCount");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
    public boolean isUserLikedReview(int userID, int reviewID) {
        String sql = "SELECT COUNT(*) FROM likedmoviereview WHERE userID = ? AND MovieReviewID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, reviewID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu có bản ghi, nghĩa là người dùng đã thích bài viết này
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm lượt thích vào bảng
    public boolean addLike(int userID, int reviewID) {
        String sql = "INSERT INTO likedmoviereview (userID, MovieReviewID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, reviewID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa lượt thích khỏi bảng
    public boolean removeLike(int userID, int reviewID) {
        String sql = "DELETE FROM likedmoviereview WHERE userID = ? AND MovieReviewID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, reviewID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
