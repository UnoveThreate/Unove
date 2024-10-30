package controller.movie;

import DAO.UserDAO;
import DAO.movie.CinemaChainMovieDAO;
import DAO.movie.FavouriteMoviesDAO;
import DAO.movie.MovieDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CinemaChain;
import model.Cinema;
import model.MovieSlot;
import model.Movie;
import util.RouterJSP;

@WebServlet(name = "HandleDisplayMovieInfo", urlPatterns = {"/HandleDisplayMovieInfo"})
public class HandleDisplayMovieInfo extends HttpServlet {

    private MovieDAO movieDAO;
    private CinemaChainMovieDAO cinemaChainMovieDAO;
    private FavouriteMoviesDAO favoriteMoviesDAO;
    private static final Logger LOGGER = Logger.getLogger(HandleDisplayMovieInfo.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            movieDAO = new MovieDAO(context);
            cinemaChainMovieDAO = new CinemaChainMovieDAO(context);
            favoriteMoviesDAO = new FavouriteMoviesDAO(context);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error initializing DAOs", ex);
            throw new ServletException("Error initializing DAOs", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters
            String movieIDStr = request.getParameter("movieID");
            String cinemaChainIDStr = request.getParameter("cinemaChainID"); 
            String cinemaIDStr = request.getParameter("cinemaID");
            String dateStr = request.getParameter("date");

            if (movieIDStr == null || movieIDStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "MovieID is required");
                return;
            }

            int movieID = Integer.parseInt(movieIDStr);
            Integer selectedCinemaChainID = cinemaChainIDStr != null ? Integer.parseInt(cinemaChainIDStr) : null;
            Integer selectedCinemaID = cinemaIDStr != null ? Integer.parseInt(cinemaIDStr) : null;
            LocalDate selectedDate = dateStr != null ? LocalDate.parse(dateStr) : LocalDate.now();

            // Get movie info
            Movie movie = movieDAO.getMovieByCinemaIDAndMovieID(movieID);
            if (movie == null) {
                LOGGER.warning("Movie not found with ID: " + movieID);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
                return;
            }

            // Get cinema chains showing this movie
            List<CinemaChain> chains = cinemaChainMovieDAO.getCinemaChainsByMovie(movieID);
            
            // Maps for cinemas and showtimes
            Map<Integer, List<Cinema>> chainCinemas = new HashMap<>();
            Map<Integer, List<MovieSlot>> cinemaShowtimes = new HashMap<>();
            
            // For each chain, get cinemas and showtimes
            for (CinemaChain chain : chains) {
                // Get cinemas for this chain
                List<Cinema> cinemas = cinemaChainMovieDAO.getCinemasByChainAndMovie(
                    chain.getCinemaChainID(), movieID);
                chainCinemas.put(chain.getCinemaChainID(), cinemas);
                
                // Get showtimes for selected cinema
                if (selectedCinemaID != null && selectedDate != null) {
                    List<MovieSlot> slots = cinemaChainMovieDAO.getMovieSlotsByCinemaAndMovie(
                        selectedCinemaID, movieID, selectedDate);
                    cinemaShowtimes.put(selectedCinemaID, slots);
                    
                    // Debug log
                    LOGGER.info(String.format("Found %d slots for cinema %d on %s", 
                        slots.size(), selectedCinemaID, selectedDate));
                }
            }

            // Get available dates (next 7 days)
            List<LocalDate> availableDates = new ArrayList<>();
            LocalDate currentDate = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                availableDates.add(currentDate.plusDays(i));
            }

            // Set attributes
            request.setAttribute("movie", movie);
            request.setAttribute("cinemaChains", chains);
            request.setAttribute("chainCinemas", chainCinemas);
            request.setAttribute("cinemaShowtimes", cinemaShowtimes);
            request.setAttribute("availableDates", availableDates);
            request.setAttribute("selectedCinemaChainID", selectedCinemaChainID);
            request.setAttribute("selectedCinemaID", selectedCinemaID);
            request.setAttribute("selectedDate", selectedDate);

            // Forward to JSP
            request.getRequestDispatcher(RouterJSP.DETAIL_MOVIE_PAGE).forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute("userID");
        int movieID = Integer.parseInt(request.getParameter("movieID"));

        boolean isAddingToFavorite = request.getParameter("isAddingToFavorite") != null && 
                                   request.getParameter("isAddingToFavorite").equals("true");

        if (isAddingToFavorite) {
            String favoritedAt = request.getParameter("favoritedAt");
            try {
                favoriteMoviesDAO.insertFavouriteMovie(userID, movieID, favoritedAt);
                doGet(request, response);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error adding movie to favorites", ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding to favorites");
            }
            return;
        }

        String redirectUrl = "/movie/HandleDisplayMovieInfo";
        response.setContentType("text/plain");
        response.getWriter().write(redirectUrl);

        doGet(request, response);
    }
}