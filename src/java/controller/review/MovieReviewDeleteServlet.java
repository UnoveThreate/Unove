package controller.review;

import DAO.review.MovieReviewDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/movie/deleteReview")
public class MovieReviewDeleteServlet extends HttpServlet {

    private MovieReviewDAO mreviews;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        try {
            this.mreviews = new MovieReviewDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(MovieReviewDeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        int movieID = Integer.parseInt(request.getParameter("movieID"));

        try {
            int rowsAffected = mreviews.deleteReview(userID, movieID);
            if (rowsAffected > 0) {
                // Nếu xóa thành công, chuyển hướng về trang chi tiết phim
                response.sendRedirect(RouterURL.DETAIL_MOVIE_PAGE + "?movieID=" + movieID);
            } else {
                // Nếu không có bài đánh giá để xóa
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Không tìm thấy bài đánh giá\"}");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieReviewDeleteServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(RouterJSP.ERROR_PAGE); // Điều hướng đến trang lỗi
        }
    }
}
