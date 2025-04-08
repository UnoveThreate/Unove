/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.review;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Order;
import model.Order_Update;
import model.Review;
import model.User;

public class MovieReviewDAO extends MySQLConnect {

    public MovieReviewDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean canReview(int userID, int movieID) throws SQLException {
        String sqlQuery = "SELECT DISTINCT MovieSlot.MovieID "
                + "FROM `Order` "
                + "JOIN Ticket ON `Order`.OrderID = Ticket.OrderID "
                + "JOIN MovieSlot ON `Order`.MovieSlotID = MovieSlot.MovieSlotID "
                + "WHERE `Order`.UserID = ? AND MovieSlot.MovieID = ? AND `Order`.Status = 'Confirmed'";

        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Nếu có kết quả trả về nghĩa là người dùng đã đặt vé cho phim này và có thể review
        }
    }

    public int insertReview(int userID, int movieID, int rating, String timeCreated, String content) throws SQLException {
        String sql = "insert into MovieReview (UserID, MovieID, Rating, TimeCreated, Content) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, movieID);
        ps.setInt(3, rating);
        ps.setString(4, timeCreated);
        ps.setString(5, content);
        return ps.executeUpdate();
    }

    public boolean hasReviewed(int userID, int movieID) throws SQLException {
        String sqlQuery = "SELECT COUNT(*) FROM MovieReview WHERE UserID = ? AND MovieID = ? ";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Nếu count > 0, người dùng đã đánh giá
            }
            return false; // Nếu không có kết quả nào (người dùng chưa đánh giá)
        }
    }

    public Map<String, Object> getMovieDetails(int movieID) throws SQLException {
        String sqlQuery = "SELECT Title, ImageURL, Rating FROM Movie WHERE MovieID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, movieID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Map<String, Object> movieDetails = new HashMap<>();
                movieDetails.put("title", rs.getString("Title"));
                movieDetails.put("imageURL", rs.getString("ImageURL"));
                movieDetails.put("rating", rs.getString("Rating"));

                // Fetch genres as a list of strings and add it to the map
                List<String> genres = getGenresByMovieID(movieID);
                movieDetails.put("genres", genres); // genres is stored as a list

                return movieDetails;
            }
        }
        return null;
    }

// Method to get genres by movie ID
    public List<String> getGenresByMovieID(int movieID) throws SQLException {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT g.GenreName FROM Genre g "
                + "JOIN MovieInGenre mig ON g.GenreID = mig.GenreID "
                + "WHERE mig.MovieID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("GenreName")); // Add genre names to the list
            }
        }
        return genres; // Return the list of genre names
    }

    // Phương thức để cập nhật rating của movie
    public void updateMovieRating(int movieID) throws SQLException {
        double averageRating = calculateAverageRating(movieID);
        if (averageRating >= 0) { // Kiểm tra xem có rating nào không
            String sqlUpdate = "UPDATE Movie SET Rating = ? WHERE MovieID = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sqlUpdate)) {
                pstmt.setDouble(1, averageRating);
                pstmt.setInt(2, movieID);
                pstmt.executeUpdate();
            }
        }
    }

    // Phương thức để tính toán trung bình cộng rating
    private double calculateAverageRating(int movieID) throws SQLException {
        String sqlQuery = "SELECT AVG(Rating) AS AverageRating FROM MovieReview WHERE MovieID = ? AND Rating != 0";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("AverageRating");
            }
        }
        return -1; // Trả về -1 nếu không có đánh giá nào
    }

    public Map<User, Review> getReviewsByMovieID(int movieID) throws SQLException {
        Map<User, Review> userReviews = new HashMap<>();
        String sql = """
        SELECT ur.UserID, ur.FullName, ur.avatarLink, mr.MovieReviewID, mr.Rating, mr.Content, mr.TimeCreated 
        FROM MovieReview mr 
        INNER JOIN User ur ON mr.UserID = ur.UserID 
        WHERE mr.MovieID = ?
        ORDER BY mr.TimeCreated DESC
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, movieID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create User object
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setFullName(rs.getString("FullName"));
                user.setAvatarLink(rs.getString("avatarLink"));

                // Create Review object
                Review review = new Review();
                review.setReviewID(rs.getInt("MovieReviewID"));
                review.setRating(rs.getInt("Rating"));
                review.setContent(rs.getString("Content"));
                review.setTimeCreated(rs.getTimestamp("TimeCreated"));

                // Put in map
                userReviews.put(user, review);
            }
        }
        return userReviews;
    }

    private Order mapToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderID(rs.getInt("OrderID"));
        order.setUserID(rs.getInt("UserID"));
        order.setMovieSlotID(rs.getInt("MovieSlotID"));
        order.setTimeCreated(rs.getTimestamp("TimeCreated"));
        order.setPremiumTypeID(rs.getInt("PremiumTypeID"));
        order.setStatus(rs.getString("Status"));
        return order;
    }

    public List<Order> getEligibleOrdersForReview(LocalDateTime currentTime) throws SQLException {
        String sql = "SELECT * FROM `Order` WHERE Status = 'Confirmed' AND MovieSlotID IS NOT NULL";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                orders.add(mapToOrder(rs)); // Gọi phương thức mapToOrder để chuyển đổi ResultSet thành Order
            }
            return orders;
        }
    }

    public String getUserEmail(int userId) throws SQLException {
        String sql = "SELECT Email FROM User WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Email");
            }
        }
        return null;
    }

//    public void markEmailSent(int userID, int movieID) throws SQLException {
//        // Kiểm tra xem bản ghi có tồn tại không
//        String checkQuery = "SELECT COUNT(*) FROM MovieReview WHERE UserID = ? AND MovieID = ?";
//        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
//            checkStmt.setInt(1, userID);
//            checkStmt.setInt(2, movieID);
//            ResultSet rs = checkStmt.executeQuery();
//            if (rs.next() && rs.getInt(1) == 0) {
//                // Nếu chưa có bản ghi, chèn mới
//                String insertQuery = "INSERT INTO MovieReview (UserID, MovieID, EmailSent) VALUES (?, ?, 1)";
//                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
//                    insertStmt.setInt(1, userID);
//                    insertStmt.setInt(2, movieID);
//                    insertStmt.executeUpdate();
//                    return; // Đã chèn mới và đánh dấu EmailSent, không cần cập nhật tiếp
//                }
//            }
//        }
//
//        // Nếu đã có bản ghi, chỉ cần cập nhật EmailSent thành 1
//        String updateQuery = "UPDATE MovieReview SET EmailSent = 1 WHERE UserID = ? AND MovieID = ?";
//        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
//            updateStmt.setInt(1, userID);
//            updateStmt.setInt(2, movieID);
//            updateStmt.executeUpdate();
//        }
//    }
    public List<Order_Update> getConfirmedOrders() throws SQLException {
        List<Order_Update> orders = new ArrayList<>();
        String sql = "SELECT OrderID, UserID, MovieSlotID, Status, ReviewRequestSent FROM `Order` WHERE Status = 'Confirmed'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order_Update order = new Order_Update();
                order.setOrderID(rs.getInt("OrderID"));
                order.setUserID(rs.getInt("UserID"));
                order.setMovieSlotID(rs.getInt("MovieSlotID"));
                order.setStatus(rs.getString("Status"));
                order.setReviewRequestSent(rs.getBoolean("ReviewRequestSent")); // Lấy trạng thái email

                orders.add(order);
            }
        }
        return orders;
    }

    public void markReviewRequestSent(int orderID) throws SQLException {
        String sql = "UPDATE `Order` SET ReviewRequestSent = 1 WHERE OrderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            ps.executeUpdate();
        }
    }

    public int deleteReview(int userID, int movieID) throws SQLException {
        String sql = "DELETE FROM MovieReview WHERE UserID = ? AND MovieID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setInt(2, movieID);

            // Thực thi câu lệnh và trả về số dòng bị ảnh hưởng (dòng bị xóa)
            return ps.executeUpdate();
        }
    }

    public int updateReview(int userID, int movieID, int rating, String content) throws SQLException {
        String sql = "UPDATE MovieReview SET Rating = ?, Content = ? WHERE UserID = ? AND MovieID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rating);
            ps.setString(2, content);
            ps.setInt(3, userID);
            ps.setInt(4, movieID);

            // Thực thi câu lệnh và trả về số dòng bị ảnh hưởng (dòng được cập nhật)
            return ps.executeUpdate();
        }
    }

}
