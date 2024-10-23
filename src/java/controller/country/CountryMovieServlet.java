package controller.country;

import DAO.cinemaChainOwnerDAO.CountryDAO;
import DAO.movie.MovieDAO;
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
import model.owner.Movie;
import util.RouterJSP;

@WebServlet(name = "FilterMovieServlet", urlPatterns = {"/FilterMovieServlet"})
public class CountryMovieServlet extends HttpServlet {

    private CountryDAO countryDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo MovieDAO với ServletContext
        try {
            ServletContext context = getServletContext();
            countryDAO = new CountryDAO(context);
        } catch (Exception e) {
            throw new ServletException("Error initializing MovieDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> countries = null;

        try {
            countries = countryDAO.getAllCountries(); // Gọi phương thức từ đối tượng
        } catch (SQLException ex) {
            Logger.getLogger(CountryMovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(countries);
        request.setAttribute("countries", countries);
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
        return "Servlet for fetching movie countries";
    }
}
