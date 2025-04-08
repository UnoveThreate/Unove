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
        
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Kiểm tra userID có hợp lệ không
        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Kiểm tra movieID có hợp lệ không
        String movieIDStr = request.getParameter("movieID");
        if (movieIDStr == null || movieIDStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Movie ID không hợp lệ\"}");
            return;
        }

        int movieID;
        try {
            movieID = Integer.parseInt(movieIDStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Định dạng movie ID không đúng\"}");
            return;
        }

        try {
            // Lấy chi tiết phim
            Map<String, Object> movieDetails = mreviews.getMovieDetails(movieID);
            if (movieDetails == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy thông tin phim\"}");
                return;
            }

            boolean canReview = mreviews.canReview(userID, movieID);
            boolean hasReviewed = mreviews.hasReviewed(userID, movieID);

            if (canReview && !hasReviewed) {
                // Nếu thỏa mãn điều kiện, chuyển hướng đến trang JSP
                request.setAttribute("movieID", movieID);
                request.setAttribute("movieTitle", movieDetails.get("title"));
                request.setAttribute("movieImageURL", movieDetails.get("imageURL"));
                request.setAttribute("movieRating", movieDetails.get("rating"));
                request.setAttribute("genres", movieDetails.get("genres"));

                // Chuyển hướng tới trang JSP (không trả về JSON)
                request.getRequestDispatcher(RouterJSP.WRITE_REVIEW_MOVIE).forward(request, response);

            } else {
                // Nếu không thỏa mãn điều kiện, trả về JSON báo lỗi
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("{\"message\": \"Bạn chưa xem phim hoặc đã đánh giá phim này\"}");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieReviewServlet.class.getName()).log(Level.SEVERE, "Database error", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi hệ thống, vui lòng thử lại sau\"}");
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
