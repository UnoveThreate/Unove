/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import model.owner.Genre;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/owner/updateMovie")
public class UpdateMovieServlet extends HttpServlet {

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

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Lấy movieID từ request
        String movieIDStr = request.getParameter("movieID");
        if (movieIDStr == null || movieIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect if movieID is not provided
            return;
        }

        try {
            Integer movieID = Integer.parseInt(movieIDStr); // Parse movieID to Integer
            Movie movie = movieDAO.getMovieByID(movieID); // Lấy thông tin bộ phim từ database
            List<Genre> genres = movieDAO.getAllGenres(); // Lấy danh sách thể loại

            // Gán thông tin bộ phim và danh sách thể loại vào request
            request.setAttribute("movie", movie);
            request.setAttribute("genres", genres);

            // Chuyển hướng đến trang cập nhật phim
            request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_UPDATE_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect if movieID is not a valid integer
        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve movie or genres", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Lấy thông tin bộ phim từ request
        Integer movieID = Integer.parseInt(request.getParameter("movieID")); // Lấy movieID từ request
        Integer cinemaID = Integer.parseInt(request.getParameter("cinemaID")); // Lấy movieID từ request
        String title = request.getParameter("title");
        String synopsis = request.getParameter("synopsis");
        String datePublishedStr = request.getParameter("datePublished");
        String imageURL = request.getParameter("imageURL");
        double rating = Double.parseDouble(request.getParameter("rating"));
        String country = request.getParameter("country");
        String linkTrailer = request.getParameter("linkTrailer");
        String[] genreIDs = request.getParameterValues("genreIDs");

        // Chuyển đổi datePublished từ String sang Date
        Date datePublished = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày
        try {
            datePublished = dateFormat.parse(datePublishedStr); // Chuyển đổi thành Date
        } catch (ParseException e) {
            throw new ServletException("Invalid date format. Please use yyyy-MM-dd.", e);
        }

        // Tạo một đối tượng Movie mới
        Movie movie = new Movie();
        movie.setMovieID(movieID); // Gán movieID cho phim
        movie.setTitle(title);
        movie.setSynopsis(synopsis);
        movie.setDatePublished(datePublished); // Gán đối tượng Date vào
        movie.setImageURL(imageURL);
        movie.setRating((float) rating); // Chuyển đổi từ double sang float
        movie.setCountry(country);
        movie.setLinkTrailer(linkTrailer);

        try {
            // Cập nhật phim trong cơ sở dữ liệu
            movieDAO.updateMovie(movie, genreIDs); // Gọi phương thức updateMovie
            // Chuyển hướng về trang danh sách phim của cinema
            response.sendRedirect(RouterURL.MANAGE_MOVIES + "?cinemaID=" + cinemaID);
        } catch (SQLException e) {
            throw new ServletException("Error updating movie", e);
        }
    }
}
