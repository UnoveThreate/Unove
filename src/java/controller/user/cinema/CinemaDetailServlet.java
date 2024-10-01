/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.cinema;

import DAO.CinemaDAO;
import controller.user.UpdateUserInfo;
import model.Cinema;
import model.CinemaReview;
import database.MySQLConnect;

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cinemaId = Integer.parseInt(request.getParameter("1"));
        Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
        List<CinemaReview> reviews = cinemaDAO.getReviewsByCinemaId(cinemaId);

        request.setAttribute("cinema", cinema);
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher(router.CINEMA_DETAIL_PAGE).forward(request, response);
    }
}
