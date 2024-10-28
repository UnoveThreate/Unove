package controller.filter_movie;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.landingPageMovieDAO.MovieDAO;
import model.owner.Movie;
import util.RouterJSP;
import jakarta.servlet.ServletContext;
import java.sql.SQLException;

@WebServlet("/searchMovies")
public class SearchMovieByTitleServlet extends HttpServlet {

    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            movieDAO = new MovieDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(SearchMovieByTitleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");
        List<Movie> movies = null;

        try {
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                movies = movieDAO.searchMoviesByTitle(searchQuery);
            } else {
                // Nếu không có từ khóa tìm kiếm, có thể xử lý theo cách khác
                movies = movieDAO.getAllMovies(); // Hoặc để trống
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchMovieByTitleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Đặt danh sách phim vào thuộc tính request
        request.setAttribute("movies", movies);
        // Chuyển tiếp đến trang JSP
        request.getRequestDispatcher(RouterJSP.FILTER_MOVIE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for searching movies by title";
    }
}
