/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import model.Movie;

/**
 *
 * @author ASUS
 */
public class ListMovieServlet extends HttpServlet {

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    @Override
    public void init() throws ServletException {
        try {
            // Đọc file properties
            Properties props = new Properties();
            InputStream input = getServletContext().getResourceAsStream("/WEB-INF/properties.properties");
            props.load(input);

            dbUrl = "jdbc:mysql://" + props.getProperty("db.serverName") + ":"
                    + props.getProperty("db.portNumber") + "/"
                    + props.getProperty("db.databaseName");
            dbUsername = props.getProperty("db.username");
            dbPassword = props.getProperty("db.password");
        } catch (Exception e) {
            throw new ServletException("Unable to load database properties", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Movie> movies = getListMovies();
        System.out.println(movies);
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("/page/landingPage/LadingPage.jsp").forward(request, response);
    }

    private List<Movie> getListMovies() {
        List<Movie> movieList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("title"));
                movie.setSynopsis(rs.getString("synopsis"));
                movie.setDatePublished(rs.getString("datePublished"));
                movie.setImageURL(rs.getString("imageURL"));
                movie.setRating(rs.getFloat("rating"));
                movie.setCountry(rs.getString("country"));
                movie.setLinkTrailer(rs.getString("linkTrailer"));
                movie.setCinemaID(rs.getInt("cinemaID"));
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi ở đây
        }
        return movieList;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
