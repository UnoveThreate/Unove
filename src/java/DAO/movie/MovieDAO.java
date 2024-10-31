package DAO.movie;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Movie;
import model.Review;

/**
 *
 * @author DELL
 */
public class MovieDAO extends MySQLConnect {

    public MovieDAO(ServletContext context) throws Exception {
        super();
        connect(context); // Establish the connection from MySQLConnect
    }

    // Method to get movie by CinemaID and MovieID
    public Movie getMovieByCinemaIDAndMovieID(int movieID) throws SQLException {
        Movie movie = null;
        String sqlQuery = "SELECT * FROM Movie WHERE  MovieID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {

            pstmt.setInt(1, movieID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve data from ResultSet
                    String title = rs.getString("Title");
                    String synopsis = rs.getString("Synopsis");
                    Date datePublished = rs.getDate("DatePublished");  // java.sql.Date, ensure compatibility with Movie class
                    String imageURL = rs.getString("ImageURL");
                    float rating = rs.getFloat("Rating");
                    String country = rs.getString("Country");
                    String linkTrailer = rs.getString("LinkTrailer");
                    int cinemaID = rs.getInt("CinemaID");

                    // Create a new Movie object using the constructor
                    movie = new Movie();
                    movie.setMovieID(rs.getInt("MovieID"));
                    movie.setCinemaID(cinemaID);
                    movie.setLinkTrailer(linkTrailer);
                    movie.setRating(rating);
                    movie.setDatePublished(datePublished);
                    movie.setCountry(country);
                    movie.setImageURL(imageURL);
                    movie.setTitle(title);
                    movie.setSynopsis(synopsis);

                    // Lấy danh sách thể loại của bộ phim từ bảng MovieInGenre
                    List<String> genres = new ArrayList<>();
                    String genreQuery = "SELECT g.GenreName as Genre FROM Genre g JOIN MovieInGenre m ON m.genreID = g.genreID WHERE m.MovieID = ?;";

                    try (PreparedStatement genrePstmt = connection.prepareStatement(genreQuery)) {
                        genrePstmt.setInt(1, movieID);
                        try (ResultSet genreResultSet = genrePstmt.executeQuery()) {
                            while (genreResultSet.next()) {
                                String genre = genreResultSet.getString("Genre");
                                genres.add(genre);
                            }
                        }
                    }

                    // Set genres to the movie object
                    movie.setGenres(genres);
                } else {
                    System.err.println("No movie found for  MovieID: " + movieID);
                }
            }
        } catch (SQLException e) {
            // Handle exceptions properly
            e.printStackTrace();
            throw new SQLException("Error while fetching movie data.", e);
        }

        return movie; // Return the Movie object (or null if not found)
    }

    public ArrayList<Review> getReviewsByMovieID(int movieID, ServletContext context) throws Exception {

        database.MySQLConnect dbConnect = new MySQLConnect();

        java.sql.Connection connection = dbConnect.connect(context);

        ArrayList<Review> reviews = new ArrayList<>();
        String sql = "SELECT moviereview.*, [User].AvatarLink, [User].Username "
                + "FROM Review "
                + "JOIN [User] ON moviereview.UserID = [User].UserID "
                + "WHERE moviereview.MovieID = ?";

        try {
            // Tạo một PreparedStatement từ kết nối và truy vấn SQL
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, movieID);
            // Thực thi truy vấn và lấy kết quả
            ResultSet resultSet = preparedStatement.executeQuery();
            // Lặp qua các kết quả và tạo đối tượng Review cho mỗi kết quả
            while (resultSet.next()) {
//                int reviewID = 1; //resultSet.getInt("ReviewID"); please change again!
                int userID = resultSet.getInt("UserID");
                int rating = resultSet.getInt("Rating");
                Date timeCreated = resultSet.getTimestamp("TimeCreated");
                String content = resultSet.getString("Content");
                String userAvatarLink = resultSet.getString("AvatarLink");
                String username = resultSet.getString("Username");

                // Tạo đối tượng Review và thêm vào danh sách
                Review review = new Review(userID, movieID, rating, timeCreated, content, userAvatarLink, username);
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews; // Trả về danh sách các review của bộ phim có movieID tương ứng dưới dạng ArrayList
    }
}
