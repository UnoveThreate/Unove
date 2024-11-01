package controller.filter_movie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.owner.Movie;
import DAO.landingPageMovieDAO.MovieDAO;
import DAO.cinemaChainOwnerDAO.CountryDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

@WebServlet("/moviesByCountry")
public class SearchMoviesByCountryServlet extends HttpServlet {

    private MovieDAO movieDAO;
    private CountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        try {
            movieDAO = new MovieDAO(getServletContext());
            countryDAO = new CountryDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Error initializing DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String country = request.getParameter("country");
        List<Movie> movies = null;

        try {
            if (country != null && !country.trim().isEmpty()) {
                movies = movieDAO.getMoviesByCountry(country); // Thêm phương thức này vào MovieDAO
            } else {
                movies = movieDAO.getAllMovies(); // Lấy tất cả phim nếu không có quốc gia
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchMoviesByCountryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Lấy danh sách quốc gia
        List<String> countries = null;
        try {
            countries = countryDAO.getAllCountries();
        } catch (SQLException ex) {
            Logger.getLogger(SearchMoviesByCountryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("movies", movies);
        request.setAttribute("countries", countries);
        request.setAttribute("selectedCountry", country); // Thêm thuộc tính để giữ giá trị country đã chọn

        request.getRequestDispatcher(RouterJSP.FILTER_MOVIE).forward(request, response);
    }
}
