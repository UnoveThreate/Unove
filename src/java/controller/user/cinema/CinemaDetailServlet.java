/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.cinema;

import DAO.CinemaDAO;
import controller.user.UpdateUserInfo;
import model.Cinema;
import model.CinemaReview;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

/**
 *
 * @author Kaan
 */
@WebServlet("/cinemaDetail")
public class CinemaDetailServlet extends HttpServlet {

    private CinemaDAO cinemaDAO;
    private RouterJSP router;

    @Override
    public void init() throws ServletException {

        try {
            cinemaDAO = new DAO.CinemaDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy CinemaID từ request
            int cinemaId = Integer.parseInt(request.getParameter("cinemaId"));

            // Gọi CinemaDAO để lấy thông tin rạp chiếu và đánh giá
            Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
            List<CinemaReview> reviews = cinemaDAO.getReviewsByCinemaId(cinemaId);

            // Đặt các đối tượng Cinema và Review vào request attribute
            request.setAttribute("cinema", cinema);
            request.setAttribute("reviews", reviews);

            // Chuyển tiếp tới trang JSP chi tiết của Cinema
            request.getRequestDispatcher(RouterJSP.CINEMA_DETAIL_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CinemaID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching cinema details");
        }
    }

}
