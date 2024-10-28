package controller.filter_movie;

import DAO.cinemaChainOwnerDAO.GenreDAO;
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
import model.owner.Genre;

@WebServlet("/searchMoviesByGenre")
public class SearchMovieByGenreServlet extends HttpServlet {

    private MovieDAO movieDAO;
    private GenreDAO genreDAO; // Thêm thuộc tính GenreDAO

    @Override
    public void init() throws ServletException {
        try {
            movieDAO = new MovieDAO(getServletContext());
            genreDAO = new GenreDAO(getServletContext()); // Khởi tạo GenreDAO
        } catch (Exception e) {
            throw new ServletException("Error initializing DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String genre = request.getParameter("genre");
        List<Movie> movies = null;
        List<Genre> genres = null; // Danh sách thể loại

        try {
            if (genre != null && !genre.trim().isEmpty()) {
                movies = movieDAO.getMoviesByGenre(genre);
            } else {
                movies = movieDAO.getAllMovies();
            }

            // Gọi phương thức getAllGenres từ GenreDAO
            genres = genreDAO.getAllGenres();
        } catch (SQLException ex) {
            Logger.getLogger(SearchMovieByGenreServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("movies", movies);
        request.setAttribute("genres", genres); // Thiết lập thuộc tính genres
        request.setAttribute("selectedGenre", genre); // Giữ giá trị thể loại đã chọn
        request.getRequestDispatcher(RouterJSP.FILTER_MOVIE).forward(request, response);
    }
}
