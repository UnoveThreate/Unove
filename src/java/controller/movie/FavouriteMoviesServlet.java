import DAO.movie.FavouriteMoviesDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;

public class FavouriteMoviesServlet extends HttpServlet {

    private FavouriteMoviesDAO fmd;

    @Override
    public void init() throws ServletException {
        try {
            fmd = new FavouriteMoviesDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID != null) {
            try {
                List<Movie> favouriteMovies = fmd.queryFavouriteMovies(userID);
                request.setAttribute("favouriteMovies", favouriteMovies);
                request.getRequestDispatcher("favourite_movies.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(FavouriteMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving favorite movies.");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID != null) {
            try {
                int movieID = Integer.parseInt(request.getParameter("movieID"));
                
                if ("true".equals(request.getParameter("isAddingToFavorite"))) {
                    String favoritedAt = request.getParameter("favoritedAt");
                    fmd.insertFavouriteMovie(userID, movieID, favoritedAt);
                } else if ("true".equals(request.getParameter("isDeletingFavorite"))) {
                    fmd.deleteFavouriteMovie(userID, movieID);
                }

                response.sendRedirect("FavouriteMoviesServlet");
            } catch (SQLException | NumberFormatException ex) {
                Logger.getLogger(FavouriteMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating favorite movies.");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
