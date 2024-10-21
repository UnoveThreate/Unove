package controller.owner.movie;

import DAO.cinemaChainOwnerDAO.MovieDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.owner.Movie;

import java.io.IOException;
import java.util.List;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/movie")
public class MovieServlet extends HttpServlet {

    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            movieDAO = new MovieDAO(context);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize MovieDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Check if user is logged in and has the owner role
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Get cinemaID from the request
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not provided
            return;
        }

        try {
            Integer cinemaID = Integer.parseInt(cinemaIDStr); // Parse cinemaID to Integer
            List<Movie> movies = movieDAO.getAllMovies(cinemaID); // Get movies for the specific cinema
            String cinemaName = movieDAO.getCinemaNameByID(cinemaID);
            request.setAttribute("movies", movies);
            request.setAttribute("cinemaID", cinemaID); // Pass cinemaID to the JSP for further use
            request.setAttribute("cinemaName", cinemaName); // Pass cinemaName to the JSP
            request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_LIST_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not a valid integer
        } catch (Exception e) {
            throw new ServletException("Error retrieving movies for cinema ID: " + cinemaIDStr, e);
        }
    }
}