/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.movie;

import DAO.movie.FavouriteMoviesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import util.RouterJSP;

/**
 *
 * @author ACER
 */
@WebServlet(name = "FavouriteMoviesServlet", urlPatterns = {"/myfavouritemovie"})
public class FavouriteMoviesServlet extends HttpServlet {
    FavouriteMoviesDAO fmd;

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
            out.println("<title>Servlet FavouriteMovieServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FavouriteMovieServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userID = (int)session.getAttribute("1");
        try {
            fmd = new FavouriteMoviesDAO(request.getServletContext());
            List<Movie> favouriteMovies = fmd.queryFavouriteMovies(userID);
//            Collections.sort(favouriteMovies, new StatusMovieComparator());
            request.setAttribute("favouriteMovies", favouriteMovies);
            for(Movie movie : favouriteMovies) {
                System.out.println(movie);
            }
            request.getRequestDispatcher(RouterJSP.FAVOURITE_MOVIE_PAGE).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FavouriteMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FavouriteMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
        HttpSession session = request.getSession();
        int userID = (int)session.getAttribute("userID");
        int movieID = Integer.parseInt(request.getParameter("deletedFavouriteMovieInput"));
        boolean isDeletingInMovieInfo = request.getParameter("isDeletingInMovieInfo") != null && request.getParameter("isDeletingInMovieInfo").equals("true");
        try {
            fmd = new FavouriteMoviesDAO(request.getServletContext());
            fmd.deleteFavouriteMovie(userID, movieID);
            if(isDeletingInMovieInfo) {
                response.sendRedirect("HandleDisplayMovieInfo?movieID=" + movieID);
                return;
            }
            List<Movie> favouriteMovies = fmd.queryFavouriteMovies(userID);
            Collections.sort(favouriteMovies, new StatusMovieComparator());
            request.setAttribute("favouriteMovies", favouriteMovies);
            request.getRequestDispatcher(RouterJSP.FAVOURITE_MOVIE_PAGE).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(FavouriteMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

class StatusMovieComparator implements Comparator<Movie> {

    @Override
    public int compare(Movie movie1, Movie movie2) {
        if(movie1.getStatus().equals(movie2.getStatus())) return 0;
        if(movie1.getStatus().equals("SHOWING")) return -1;
        return 1;
    }
    
}