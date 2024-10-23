package controller.genre;

import DAO.movie.GenreDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.owner.Genre;
import util.RouterJSP;

@WebServlet(name = "GenreMovieServlet", urlPatterns = {"/GenreMovieServlet"})
public class GenreMovieServlet extends HttpServlet {

    private GenreDAO genreDAO;

    @Override
    public void init() throws ServletException {
        // Chỉ cần khởi tạo GenreDAO với ServletContext
        try {
            ServletContext context = getServletContext();
            genreDAO = new GenreDAO(context);
        } catch (Exception e) {
            throw new ServletException("Error initializing GenreDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Genre> genres = null;

        try {
            genres = genreDAO.getAllGenres(); // Gọi phương thức từ đối tượng
        } catch (SQLException ex) {
            Logger.getLogger(GenreMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(genres);
        request.setAttribute("genres", genres);
        request.getRequestDispatcher(RouterJSP.FILTER_MOVIE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for fetching movie genres";
    }
}
