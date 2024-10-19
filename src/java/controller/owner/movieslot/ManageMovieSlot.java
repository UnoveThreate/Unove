package controller.owner.movieslot;

import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.cinemaChainOwnerDAO.MovieDAO;
import DAO.cinemaChainOwnerDAO.RoomDAO;
import model.owner.MovieSlot;
import model.owner.Movie;
import model.owner.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterURL;
import util.Role;
import util.RouterJSP;

@WebServlet("/owner/movieSlot/manageMovieSlot")
public class ManageMovieSlot extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra người dùng
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Lấy cinemaID từ tham số URL
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA);
            return;
        }

        Integer cinemaID = Integer.valueOf(cinemaIDStr);
        session.setAttribute("cinemaID", cinemaID);

        try {
            MovieSlotDAO movieSlotDAO = new MovieSlotDAO(getServletContext());

            // Lấy roomID và movieID từ tham số URL để lọc
            String roomIDStr = request.getParameter("roomID");
            String movieIDStr = request.getParameter("movieID");
            Integer roomID = roomIDStr != null && !roomIDStr.isEmpty() ? Integer.valueOf(roomIDStr) : null;
            Integer movieID = movieIDStr != null && !movieIDStr.isEmpty() ? Integer.valueOf(movieIDStr) : null;

            // Lấy danh sách suất chiếu với việc lọc theo cinemaID, roomID, và movieID
            List<MovieSlot> movieSlots = movieSlotDAO.getAllMovieSlots(cinemaID, roomID, movieID);

            // Lấy danh sách phim
            MovieDAO movieDAO = new MovieDAO(getServletContext());
            List<Movie> movies = movieDAO.getAllMovies(cinemaID);
            Map<Integer, String> movieNames = new HashMap<>();
            for (Movie movie : movies) {
                if (movie.isStatus()) {
                    movieNames.put(movie.getMovieID(), movie.getTitle());
                }
            }

            // Lấy danh sách phòng
            RoomDAO roomDAO = new RoomDAO(getServletContext());
            List<Room> rooms = roomDAO.getRoomsByCinemaID(cinemaID);
            Map<Integer, String> roomNames = new HashMap<>();
            for (Room room : rooms) {
                roomNames.put(room.getRoomID(), room.getRoomName());
            }

            // Gửi dữ liệu tới JSP
            request.setAttribute("movieSlots", movieSlots);
            request.setAttribute("movieNames", movieNames);
            request.setAttribute("roomNames", roomNames);
            request.setAttribute("rooms", rooms);
            request.setAttribute("selectedRoomID", roomID);
            request.setAttribute("selectedMovieID", movieID); // Cập nhật selectedMovieID

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error occurred.");
        } catch (Exception ex) {
            Logger.getLogger(ManageMovieSlot.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.getRequestDispatcher(RouterJSP.OWNER_MANAGE_MOVIESLOT).forward(request, response);
    }
}
