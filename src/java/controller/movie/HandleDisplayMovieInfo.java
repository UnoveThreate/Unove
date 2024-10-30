package controller.movie;

import DAO.UserDAO;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import util.RouterJSP;
import util.RouterURL;

@WebServlet(name = "HandleDisplayMovieInfo", urlPatterns = {"/HandleDisplayMovieInfo"})
public class HandleDisplayMovieInfo extends HttpServlet {

    private MovieDAO movieDAO;
    FavouriteMoviesDAO favoriteMoviesDAO;
    UserDAO userDAO;
    private RouterJSP route = new RouterJSP();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            movieDAO = new MovieDAO(getServletContext());
            favoriteMoviesDAO = new FavouriteMoviesDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userDAO = new UserDAO(request.getServletContext());
            // Nhan tu username gui qua
            String movieID = request.getParameter("movieID");

            //kiem tra chuoi có null hay khong
            if (movieID == null || movieID.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CinemaID or MovieID is missing or empty");
                return;
            }

            Movie movie = movieDAO.getMovieByCinemaIDAndMovieID(Integer.parseInt(movieID));

//             If movie is null, handle the case
            if (movie == null) {
                System.err.println("Movie not found  MovieID: " + movieID);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
                return;
            }

            // case favourite movies
            HttpSession session = request.getSession();
            System.out.println(session.getAttribute("userID"));

            int userID = -1;
            if (session.getAttribute("userID") != null) {
                userID = (int) session.getAttribute("userID");
            }

            Boolean isFavoritedMovie = null;
            if (userID != -1) {
                isFavoritedMovie = favoriteMoviesDAO.isFavoritedMovie(userID, Integer.parseInt(movieID));
            }

            // Add movie to request attributes
            request.setAttribute("movie", movie);
            request.setAttribute("isFavoritedMovie", isFavoritedMovie);

            // Forward request to DisplayMovieInfo.jsp
            request.getRequestDispatcher(route.DETAIL_MOVIE_PAGE).forward(request, response);

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
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN); // Chuyển hướng tới trang đăng nhập nếu userID không tồn tại
            return;
        }

        int movieID = Integer.parseInt(request.getParameter("movieID"));
        boolean isAddingToFavorite = "true".equals(request.getParameter("isAddingToFavorite"));

        if (isAddingToFavorite) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String favoritedAt = currentDateTime.format(formatter);

            try {

                favoriteMoviesDAO.insertFavouriteMovie(userID, movieID, favoritedAt);
            } catch (Exception ex) {
                Logger.getLogger(HandleDisplayMovieInfo.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi thêm vào yêu thích.");
                return;
            }
        }

        // Chuyển hướng đến trang mong muốn sau khi xử lý xong
        response.sendRedirect("/Unove/HandleDisplayMovieInfo?movieID=" + movieID);
    }

}
