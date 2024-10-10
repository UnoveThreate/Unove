/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import model.Movie;
import util.RouterJSP;
import database.MySQLConnect;


/**
 *
 * @author Admin
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

 
    Connection connection ;

    @Override

    public void init() throws ServletException {
        try {
           connection = new MySQLConnect().connect(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Unable to load database properties", e);
        }
    }

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
        List<Movie> movies = getListMovies();
        System.out.println(movies);
        request.setAttribute("movies", movies);
        request.getRequestDispatcher(new RouterJSP().LANDING_PAGE).forward(request, response);

    }

    private List<Movie> getListMovies() {
        List<Movie> movieList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Movie where Status = TRUE"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieID(rs.getInt("movieID"));
                movie.setTitle(rs.getString("title"));
                movie.setSynopsis(rs.getString("synopsis"));
                movie.setDatePublished(rs.getString("datePublished"));
                movie.setImageURL(rs.getString("imageURL"));
                movie.setRating(rs.getFloat("rating"));
                movie.setCountry(rs.getString("country"));
                movie.setLinkTrailer(rs.getString("linkTrailer"));
                movie.setCinemaID(rs.getInt("cinemaID"));
                movieList.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý lỗi ở đây
        }
        return movieList;
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
