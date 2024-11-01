package controller.filter_movie;

import DAO.cinemaChainOwnerDAO.GenreDAO;
import DAO.cinemaChainOwnerDAO.CountryDAO;
import DAO.landingPageMovieDAO.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.owner.Movie;
import model.owner.Genre;
import util.RouterJSP;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/filterMovies")
public class FilterMoviesServlet extends HttpServlet {

    private MovieDAO movieDAO;
    private GenreDAO genreDAO;
    private CountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        try {
            movieDAO = new MovieDAO(getServletContext());
            genreDAO = new GenreDAO(getServletContext());
            countryDAO = new CountryDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Error initializing DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String genre = request.getParameter("genre");
        String country = request.getParameter("country");
        String searchQuery = request.getParameter("searchQuery");

        List<Movie> movies = null;
        List<Genre> genres = null;
        List<String> countries = null;

        try {
            // Lấy danh sách thể loại
            genres = genreDAO.getAllGenres();
            // Lấy danh sách quốc gia
            countries = countryDAO.getAllCountries();

            // Xử lý tìm kiếm
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                movies = movieDAO.searchMoviesByTitle(searchQuery);
            } else if (genre != null && !genre.trim().isEmpty()) {
                movies = movieDAO.getMoviesByGenre(genre);
            } else if (country != null && !country.trim().isEmpty()) {
                movies = movieDAO.getMoviesByCountry(country);
            } else {
                movies = movieDAO.getAllMovies();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilterMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Thiết lập thuộc tính cho JSP
        request.setAttribute("movies", movies);
        request.setAttribute("genres", genres);
        request.setAttribute("countries", countries);
        request.setAttribute("selectedGenre", genre);
        request.setAttribute("selectedCountry", country);
        request.setAttribute("searchQuery", searchQuery);

        request.getRequestDispatcher(RouterJSP.FILTER_MOVIE).forward(request, response);
    }
}
