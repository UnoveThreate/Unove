package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.owner.Movie;
import DAO.landingPageMovieDAO.MovieDAO;
import util.RouterJSP;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/searchMoviesByGenre")
public class SearchMovieByGenreServlet extends HttpServlet {

    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Error initializing MovieDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thể loại từ tham số request
        String genre = request.getParameter("genre");
        List<Movie> movies = null;

        try {
            if (genre != null && !genre.trim().isEmpty()) {
                // Nếu có thể loại, gọi phương thức tìm kiếm
                movies = movieDAO.getMoviesByGenre(genre);
            } else {
                // Nếu không có thể loại, lấy tất cả phim
                movies = movieDAO.getAllMovies();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchMovieByGenreServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Đặt danh sách phim vào thuộc tính request
        request.setAttribute("movies", movies);
        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher(RouterJSP.LANDING_PAGE).forward(request, response);

    }
}
