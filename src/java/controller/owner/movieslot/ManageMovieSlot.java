package controller.owner.movieslot;

import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import DAO.cinemaChainOwnerDAO.CinemaDAO;
import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
import DAO.cinemaChainOwnerDAO.MovieDAO;
import DAO.cinemaChainOwnerDAO.RoomDAO;
import DAO.cinemaChainOwnerDAO.SeatDAO;
import jakarta.servlet.ServletContext;
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
import model.Cinema;
import util.RouterURL;
import util.Role;
import util.RouterJSP;

@WebServlet("/owner/movieSlot/manageMovieSlot")
public class ManageMovieSlot extends HttpServlet {

    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            cinemaChainDAO = new CinemaChainDAO(context);
            cinemaDAO = new CinemaDAO(context);
            roomDAO = new RoomDAO(context);
        } catch (Exception e) {
            throw new ServletException("Error initializing DAO", e);
        }
    }

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

        request.setCharacterEncoding("UTF-8");  // Cấu hình mã hóa đầu vào
        response.setCharacterEncoding("UTF-8"); // Cấu hình mã hóa đầu ra
        loadCinemas(request);
        // Lấy selectedCinemaID từ request nếu có, mặc định là -1
        String selectedCinemaID = request.getParameter("cinemaID");
        if (selectedCinemaID != null) {
            request.setAttribute("selectedCinemaID", selectedCinemaID);
        }

        request.getRequestDispatcher(RouterJSP.OWNER_MANAGE_MOVIESLOT).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra người dùng
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        loadCinemas(request);  // Luôn lấy danh sách cinema

        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA);
            return;
        }

        // Lưu cinemaID vào session
        int cinemaID = Integer.parseInt(cinemaIDStr);
        session.setAttribute("cinemaID", cinemaID);

        // Lưu selectedCinemaID vào request
        request.setAttribute("selectedCinemaID", cinemaIDStr);

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

        // Giữ lại cinemaID đã chọn
        request.setAttribute("selectedCinemaID", cinemaIDStr);

        request.getRequestDispatcher(RouterJSP.OWNER_MANAGE_MOVIESLOT).forward(request, response);
    }

    private void loadCinemas(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Kiểm tra xem userID có trong session không
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID == null) {
            System.out.println("userID không có trong session");
            return;
        }

        try {
            int cinemaChainID = cinemaChainDAO.getCinemaChainByUserID(userID).getCinemaChainID();
            List<Cinema> cinemas = cinemaDAO.getCinemasByCinemaChainID(cinemaChainID);

            if (cinemas == null || cinemas.isEmpty()) {
                System.out.println("Không có cinema nào trong cinemaChainID: " + cinemaChainID);
            } else {
                System.out.println("Danh sách cinema đã lấy thành công.");
            }

            request.setAttribute("cinemas", cinemas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
