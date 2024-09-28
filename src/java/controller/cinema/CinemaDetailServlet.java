/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.cinema;

import DAO.CinemaDAO;
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


/**
 *
 * @author Kaan
 */
@WebServlet("/cinemaDetail")
public class CinemaDetailServlet extends HttpServlet {
    private CinemaDAO cinemaDAO;

    @Override
    public void init() throws ServletException {
        MySQLConnect mySQLConnect = new MySQLConnect();
        cinemaDAO = new CinemaDAO(mySQLConnect);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cinemaId = Integer.parseInt(request.getParameter("id"));
        Cinema cinema = cinemaDAO.getCinemaById(cinemaId);
        List<CinemaReview> reviews = cinemaDAO.getReviewsByCinemaId(cinemaId);

        request.setAttribute("cinema", cinema);
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("cinemaDetail.jsp").forward(request, response);
    }
}
