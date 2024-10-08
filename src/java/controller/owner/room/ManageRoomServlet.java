package controller.owner.room;

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
import java.util.List;

@WebServlet("/owner/manageRoom")
public class ManageRoomServlet extends HttpServlet {

    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            roomDAO = new RoomDAO(context);
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

        // Get cinemaID from the request
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not provided
            return;
        }

        try {
            Integer cinemaID = Integer.parseInt(cinemaIDStr); // Parse cinemaID to Integer
            List<Room> rooms = roomDAO.getRoomsByCinemaID(cinemaID); // Get rooms for the specific cinema
            request.setAttribute("rooms", rooms);
            request.setAttribute("cinemaID", cinemaID); // Pass cinemaID to the JSP for further use
            request.getRequestDispatcher(RouterJSP.OWNER_MANAGE_ROOM_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not a valid integer
        } catch (Exception e) {
            throw new ServletException("Error retrieving rooms for cinema ID: " + cinemaIDStr, e);
        }
    }
}
