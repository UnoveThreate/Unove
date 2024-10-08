/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.movie;

import DAO.cinemaChainOwnerDAO.MovieDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import model.owner.Movie;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import model.owner.Genre;
import util.Role;
import util.RouterJSP;
import util.RouterURL;
import util.FileUploader;

@WebServlet("/owner/createMovie")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 30, // 30 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class CreateMovieServlet extends HttpServlet {

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

        // Lấy cinemaID từ request
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not provided
            return;
        }

        try {
            Integer cinemaID = Integer.parseInt(cinemaIDStr); // Parse cinemaID to Integer
            List<Genre> genres = movieDAO.getAllGenres(); // Lấy danh sách thể loại từ database

            // Gán cinemaID cho request để truyền đến JSP
            request.setAttribute("cinemaID", cinemaID);
            request.setAttribute("genres", genres); // Truyền danh sách thể loại tới JSP

            // Chuyển hướng đến trang tạo phim
            request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_CREATE_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not a valid integer
        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve genres", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");
        Integer cinemaID = Integer.parseInt(request.getParameter("cinemaID")); // Lấy cinemaID từ request

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Lấy thông tin bộ phim từ request
        String title = request.getParameter("title");
        String synopsis = request.getParameter("synopsis");
        String datePublishedStr = request.getParameter("datePublished");
        String country = request.getParameter("country");
        String linkTrailer = request.getParameter("linkTrailer");
        String[] genreIDs = request.getParameterValues("genreIDs");
        String type = request.getParameter("type"); // Lấy type từ form

        // Chuyển đổi datePublished từ String sang Date
        Date datePublished = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày
        try {
            datePublished = dateFormat.parse(datePublishedStr); // Chuyển đổi thành Date
        } catch (ParseException e) {
            throw new ServletException("Invalid date format. Please use yyyy-MM-dd.", e);
        }

        // Xử lý việc upload ảnh cho phim
        String imageURL = "";
        Part imagePart = request.getPart("imageURL");
        if (imagePart != null && imagePart.getSize() > 0) {
            // Tạo file tạm cho ảnh
            File imageFile = File.createTempFile("movie_", "_" + imagePart.getSubmittedFileName());
            imagePart.write(imageFile.getAbsolutePath());

            // Upload lên Cloudinary
            FileUploader fileUploader = new FileUploader();
            imageURL = fileUploader.uploadAndReturnUrl(imageFile, "movie_" + title, "movie/poster");
            if (imageURL.isEmpty()) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }
            System.out.println("Uploaded image URL: " + imageURL);

        }

        // Tạo một đối tượng Movie mới
        Movie newMovie = new Movie();
        newMovie.setTitle(title);
        newMovie.setSynopsis(synopsis);
        newMovie.setDatePublished(datePublished); // Gán đối tượng Date vào
        newMovie.setImageURL(imageURL); // Gán URL ảnh sau khi upload
        newMovie.setRating(0.0f); // Chuyển đổi từ double sang float
        newMovie.setCountry(country);
        newMovie.setLinkTrailer(linkTrailer);
        newMovie.setType(type); // Gán giá trị type vào đối tượng Movie
        newMovie.setCinemaID(cinemaID); // Gán cinemaID cho phim

        try {
            // Thêm phim vào cơ sở dữ liệu
            movieDAO.createMovie(newMovie, genreIDs); // Gọi phương thức createMovie
            // Chuyển hướng về trang danh sách phim của cinema
            response.sendRedirect(RouterURL.MANAGE_MOVIES + "?cinemaID=" + cinemaID);
        } catch (Exception e) {
            throw new ServletException("Error creating new movie", e);
        }
    }

}
