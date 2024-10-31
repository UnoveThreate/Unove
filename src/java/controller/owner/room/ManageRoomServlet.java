package controller.owner.room;

import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import DAO.cinemaChainOwnerDAO.CinemaDAO;
import DAO.cinemaChainOwnerDAO.RoomDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.owner.Room;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Cinema;

@WebServlet("/owner/room/manageRoom")
public class ManageRoomServlet extends HttpServlet {

    private RoomDAO roomDAO;
    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            roomDAO = new RoomDAO(context);
            cinemaChainDAO = new CinemaChainDAO(context);
            cinemaDAO = new CinemaDAO(context);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize RoomDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Check if user is logged in and has the owner role
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }
        request.setCharacterEncoding("UTF-8");  // Cấu hình mã hóa đầu vào
        response.setCharacterEncoding("UTF-8"); // Cấu hình mã hóa đầu ra

        loadCinemas(request);

        // Get cinemaID from the request
        String cinemaIDStr = request.getParameter("cinemaID");
        Integer selectedCinemaID = null;

        if (cinemaIDStr != null && !cinemaIDStr.isEmpty()) {
            try {
                selectedCinemaID = Integer.parseInt(cinemaIDStr); // Parse cinemaID to Integer
                List<Room> rooms = roomDAO.getRoomsByCinemaID(selectedCinemaID); // Get rooms for the specific cinema
                request.setAttribute("rooms", rooms);
            } catch (NumberFormatException e) {
                response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not a valid integer
                return;
            } catch (Exception e) {
                throw new ServletException("Error retrieving rooms for cinema ID: " + cinemaIDStr, e);
            }
        }

        request.setAttribute("selectedCinemaID", selectedCinemaID); // Set selectedCinemaID for JSP
        request.getRequestDispatcher(RouterJSP.OWNER_MANAGE_ROOM_PAGE).forward(request, response);
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
