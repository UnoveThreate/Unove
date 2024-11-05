/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.review;

import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.review.MovieReviewDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author nguyendacphong
 */
@WebServlet("/movie/reviewMovie")
public class MovieReviewServlet extends HttpServlet {
    
    private MovieReviewDAO mreviews;
    private MovieSlotDAO mslots;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = (ServletContext) getServletContext();
            this.mreviews = new MovieReviewDAO(context);
        } catch (Exception e) {
            throw new ServletException("Cannot initialize DAO", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra người dùng
        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String movieIDStr = request.getParameter("movieID");
        int movieID = Integer.parseInt(movieIDStr);
        request.setAttribute("movieID", movieID);
        
        try {
            Map<String, Object> movieDetails = mreviews.getMovieDetails(movieID); // Use Map<String, Object> here
            if (movieDetails != null) {
                // Retrieve movie details and set them as request attributes
                request.setAttribute("movieTitle", movieDetails.get("title"));
                request.setAttribute("movieImageURL", movieDetails.get("imageURL"));
                request.setAttribute("movieRating", movieDetails.get("rating"));
                request.setAttribute("genres", movieDetails.get("genres")); // Set genres as an attribute
            }
            boolean canReview = mreviews.canReview(userID, movieID);
            boolean hasReview = mreviews.hasReviewed(userID, movieID);
            
            Logger.getLogger(MovieReviewServlet.class.getName()).log(Level.INFO, "Can review: " + canReview);
            Logger.getLogger(MovieReviewServlet.class.getName()).log(Level.INFO, "Has reviewed: " + hasReview);
            
            // Nếu người dùng có quyền review và chưa review thì chuyển hướng đến trang viết review
            if (canReview && hasReview) {
                request.getRequestDispatcher(RouterJSP.WRITE_REVIEW_MOVIE).forward(request, response);
                return;
            }
            // Nếu người dùng đã review, điều hướng đến trang chi tiết phim
            response.sendRedirect(RouterURL.DETAIL_MOVIE_PAGE + "?movieID=" + movieID);
        } catch (SQLException ex) {
            Logger.getLogger(MovieReviewServlet.class.getName()).log(Level.SEVERE, "Database error", ex);
            response.sendRedirect(RouterJSP.ERROR_PAGE);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        
        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }
        
        int movieID = Integer.parseInt(request.getParameter("movieID"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String content = request.getParameter("content");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeCreated = LocalDateTime.now().format(formatter);
        
        try {
            mreviews.insertReview(userID, movieID, rating, timeCreated, content);
            mreviews.updateMovieRating(movieID);
            response.sendRedirect(RouterURL.DETAIL_MOVIE_PAGE + "?movieID=" + movieID); // Điều hướng về trang chi tiết phim
        } catch (SQLException ex) {
            Logger.getLogger(MovieReviewServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(RouterJSP.ERROR_PAGE); // Điều hướng đến trang lỗi
        }
    }
}
