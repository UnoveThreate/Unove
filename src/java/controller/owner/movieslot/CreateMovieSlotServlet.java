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

@WebServlet("/owner/movieSlot/createMovieSlot")
public class CreateMovieSlotServlet extends HttpServlet {

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

        // Lấy danh sách phim và phòng
        List<Movie> movies = null;
        List<Room> rooms = null;
        try {
            movies = new MovieDAO(getServletContext()).getAllMovies(cinemaID);
            rooms = new RoomDAO(getServletContext()).getRoomsByCinemaID(cinemaID);
        } catch (Exception ex) {
            Logger.getLogger(CreateMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("cinemaID", cinemaID);
        request.setAttribute("movies", movies);
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher(RouterJSP.OWNER_CREATE_MOVIESLOT).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer cinemaID = (Integer) session.getAttribute("cinemaID");
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        String[] roomIDs = request.getParameterValues("roomIDs");
        int movieID = Integer.parseInt(request.getParameter("movieID"));
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String type = request.getParameter("type");
        float price = Float.parseFloat(request.getParameter("price"));
        float discount = Float.parseFloat(request.getParameter("discount"));

        // Đặt trạng thái mặc định là "Active"
        String status = "Active"; // Gán trực tiếp giá trị "Active"

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

        if (startTime.isBefore(LocalDateTime.now())) {
            request.setAttribute("errorMessage", "Start time must be in the future.");
            doGet(request, response);
            return;
        }
        if (endTime.isBefore(startTime)) {
            request.setAttribute("errorMessage", "End time must be after start time.");
            doGet(request, response);
            return;
        }

        MovieSlotDAO movieSlotDAO;
        RoomDAO roomDAO;
        try {
            movieSlotDAO = new MovieSlotDAO(getServletContext());
            roomDAO = new RoomDAO(getServletContext());

            List<String> overlappingRooms = new ArrayList<>();

            for (String roomID : roomIDs) {
                if (movieSlotDAO.checkOverlap(startTime, endTime, Integer.parseInt(roomID))) {
                    Room room = roomDAO.getRoom(Integer.parseInt(roomID));
                    if (room != null) {
                        overlappingRooms.add(room.getRoomName());
                    }
                }
            }

            if (!overlappingRooms.isEmpty()) {
                String errorMessage = "There is a time overlap with existing movie slots in the following rooms: "
                        + String.join(", ", overlappingRooms);
                request.setAttribute("errorMessage", errorMessage);
                doGet(request, response);
                return;
            }

            for (String roomID : roomIDs) {
                MovieSlot newMovieSlot = new MovieSlot();
                newMovieSlot.setRoomID(Integer.parseInt(roomID));
                newMovieSlot.setMovieID(movieID);
                newMovieSlot.setStartTime(startTime);
                newMovieSlot.setEndTime(endTime);
                newMovieSlot.setType(type);
                newMovieSlot.setPrice(price);
                newMovieSlot.setDiscount(discount);
                newMovieSlot.setStatus(status); // Sử dụng giá trị "Active"

                movieSlotDAO.createMovieSlot(newMovieSlot);
            }

            response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT + "?cinemaID=" + cinemaID);
        } catch (SQLException e) {
            Logger.getLogger(CreateMovieSlotServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("errorMessage", "Database error occurred.");
            doGet(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CreateMovieSlotServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", "An unexpected error occurred.");
            doGet(request, response);
        }
    }

}
