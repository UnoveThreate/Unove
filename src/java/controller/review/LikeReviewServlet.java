/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.review;

import DAO.UserDAO;
import DAO.payment.OrderDAO;
import DAO.payment.PaymentDAO;
import DAO.review.LikeReviewDAO;
import DAO.ticket.TicketDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Role;
import util.RouterURL;

/**
 *
 * @author DELL
 */
@WebServlet(name = "LikeReviewServlet", urlPatterns = {"/LikeReviewServlet"})
public class LikeReviewServlet extends HttpServlet {

    private LikeReviewDAO likeReviewDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {

            likeReviewDAO = new LikeReviewDAO(getServletContext());

        } catch (Exception ex) {

            throw new ServletException("Failed to initialize DAOs.", ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LikeReviewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LikeReviewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy các tham số từ form
        HttpSession session = request.getSession();

        int reviewID = Integer.parseInt(request.getParameter("reviewID"));
        int userID = (Integer) session.getAttribute("userID");
        int movieID = Integer.parseInt(request.getParameter("movieID"));

        // Kiểm tra xem người dùng đã thích bài đánh giá này chưa
        boolean isLiked = likeReviewDAO.isUserLikedReview(userID, reviewID);

        boolean success = false;

        // Nếu người dùng chưa thích, thêm lượt thích vào bảng; nếu đã thích, xóa lượt thích khỏi bảng
        if (isLiked) {
            // Nếu đã thích, thì xóa lượt thích
            success = likeReviewDAO.removeLike(userID, reviewID);
        } else {
            // Nếu chưa thích, thì thêm lượt thích
            success = likeReviewDAO.addLike(userID, reviewID);
        }

        // Trả về kết quả cho client dưới dạng JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"success\": " + success + "}");
        out.flush();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
