/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.cinemaChainOwnerDAO.MovieDAO;
import controller.owner.movie.MovieServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.owner.Movie;
import util.RouterJSP;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "FilterMovieByCountryServlet", urlPatterns = {"/FilterMovieByCountryServlet"})
public class FilterMovieByCountryServlet extends HttpServlet {

    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        try {
            movieDAO = new MovieDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Error initializing MovieDAO", e);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String country = request.getParameter("country"); // Nhận quốc gia từ tham số yêu cầu
        List<Movie> movies = null;

        try {
            movies = movieDAO.getMoviesByCountry(country);
        } catch (SQLException ex) {
            Logger.getLogger(MovieServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("movies", movies);
        request.getRequestDispatcher(RouterJSP.FILTER_MOVIE).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
