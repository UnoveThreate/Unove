package controller.owner.movieslot;

import DAO.cinemaChainOwnerDAO.MovieDAO;
import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.cinemaChainOwnerDAO.RoomDAO;
import model.owner.MovieSlot;
import model.owner.Room;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.owner.Movie;
import util.RouterURL;
import util.Role;
import util.RouterJSP;

@WebServlet("/owner/movieSlot/editMovieSlot")
public class EditMovieSlotServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        Integer cinemaID = (Integer) session.getAttribute("cinemaID");
        if (cinemaID == null) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA);
            return;
        }

        String movieSlotID = request.getParameter("movieSlotID");
        MovieSlotDAO movieSlotDAO = null;
        List<Movie> movies = null;
        try {
            movieSlotDAO = new MovieSlotDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(EditMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        RoomDAO roomDAO = null;
        try {
            roomDAO = new RoomDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(EditMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            movies = new MovieDAO(getServletContext()).getAllMovies(cinemaID);
            MovieSlot movieSlot = movieSlotDAO.getMovieSlotById(Integer.parseInt(movieSlotID));
            List<Room> rooms = roomDAO.getRoomsByCinemaID(cinemaID);

            request.setAttribute("movieSlot", movieSlot);
            request.setAttribute("rooms", rooms);
            request.setAttribute("movies", movies);
            request.getRequestDispatcher(RouterJSP.OWNER_EDIT_MOVIESLOT).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(EditMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        Integer cinemaID = (Integer) session.getAttribute("cinemaID");
        String movieSlotID = request.getParameter("movieSlotID");
        String roomID = request.getParameter("roomID");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String movieIDStr = request.getParameter("movieID"); // Lấy MovieID từ request

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

        MovieSlotDAO movieSlotDAO = null;
        try {
            movieSlotDAO = new MovieSlotDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(EditMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Kiểm tra trùng lặp
            if (movieSlotDAO.checkOverlap(startTime, endTime, Integer.parseInt(roomID))) {
                request.setAttribute("errorMessage", "Room is already booked during this time.");
                doGet(request, response);
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Cập nhật movie slot
        MovieSlot updatedMovieSlot = new MovieSlot();
        updatedMovieSlot.setMovieSlotID(Integer.parseInt(movieSlotID));
        updatedMovieSlot.setRoomID(Integer.parseInt(roomID));
        updatedMovieSlot.setStartTime(startTime);
        updatedMovieSlot.setEndTime(endTime);
        updatedMovieSlot.setMovieID(Integer.parseInt(movieIDStr)); // Thiết lập MovieID từ request

        try {
            movieSlotDAO.updateMovieSlot(updatedMovieSlot);
        } catch (SQLException ex) {
            Logger.getLogger(EditMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT + "?cinemaID=" + cinemaID);
    }

}
