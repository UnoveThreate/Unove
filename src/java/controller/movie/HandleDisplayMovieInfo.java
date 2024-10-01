package controller.movie;

import DAO.movie.MovieDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import util.RouterJSP;

@WebServlet(name = "HandleDisplayMovieInfo", urlPatterns = {"/HandleDisplayMovieInfo"})
public class HandleDisplayMovieInfo extends HttpServlet {

    private MovieDAO movieDAO;
    private RouterJSP route = new RouterJSP();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

//            String cinemaID = request.getParameter("cinemaID");
//            String movieID = request.getParameter("movieID");
            String cinemaID = "1";
            String movieID = "1";

            //kiem tra chuoi có null hay khong
            if (cinemaID == null || cinemaID.isEmpty() || movieID == null || movieID.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CinemaID or MovieID is missing or empty");
                return;
            }

            int cinemaIDInt;
            int movieIDInt;
            try {
                cinemaIDInt = Integer.parseInt(cinemaID);
                movieIDInt = Integer.parseInt(movieID);

            } catch (NumberFormatException ex) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid format for cinemaID or movieID");
                return;
            }

            Movie movie = movieDAO.getMovieByCinemaIDAndMovieID(cinemaIDInt,movieIDInt);
            System.out.println("Looking for Movie with CinemaID: " + cinemaIDInt + " and MovieID: " + movieIDInt);

//             If movie is null, handle the case
            if (movie == null) {
                System.err.println("Movie not found for CinemaID: " + cinemaIDInt + ", MovieID: " + movieIDInt);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
                return;
            }

            // Add movie to request attributes
            request.setAttribute("movie", movie);

            // Forward request to DisplayMovieInfo.jsp
            request.getRequestDispatcher("page/movie/DisplayMovieInfo.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        } catch (NumberFormatException ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid format for cinemaID or movieID");
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implement doPost if needed
    }
}
