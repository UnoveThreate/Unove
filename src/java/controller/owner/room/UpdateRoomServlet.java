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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/owner/room/updateRoom")
public class UpdateRoomServlet extends HttpServlet {

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

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        Integer roomID = Integer.parseInt(request.getParameter("roomID"));
        try {
            Room room = roomDAO.getRoom(roomID); // Lấy phòng từ cơ sở dữ liệu
            request.setAttribute("room", room); // Đưa đối tượng Room vào request
            request.getRequestDispatcher(RouterJSP.OWNER_ROOM_UPDATE_PAGE).forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve room information", e);
        }
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    String role = (String) session.getAttribute("role");
    Integer userID = (Integer) session.getAttribute("userID");

    if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
        response.sendRedirect(RouterURL.LOGIN);
        return;
    }

        Integer roomID = Integer.parseInt(request.getParameter("roomID"));
        String roomName = request.getParameter("roomName");
        String[] roomTypeArray = request.getParameterValues("roomType");

        // Kiểm tra xem có loại phòng nào được chọn không
        if (roomTypeArray == null || roomTypeArray.length == 0) {
            request.setAttribute("errorMessage", "Please select at least one room type.");
            request.getRequestDispatcher(RouterJSP.OWNER_ROOM_UPDATE_PAGE).forward(request, response);
            return;
        }

        List<String> roomTypes = Arrays.asList(roomTypeArray);
        Integer capacity = Integer.parseInt(request.getParameter("capacity"));
        String screenType = request.getParameter("screenType");
        Integer cinemaID = Integer.parseInt(request.getParameter("cinemaID"));

        Room updatedRoom = new Room();
        updatedRoom.setRoomID(roomID);
        updatedRoom.setRoomName(roomName);
        updatedRoom.setCapacity(capacity);
        updatedRoom.setScreenType(screenType);
        updatedRoom.setCinemaID(cinemaID);
        updatedRoom.setRoomTypes(roomTypes);

        try {
            roomDAO.updateRoom(updatedRoom);
            response.sendRedirect(RouterURL.MANAGE_ROOM + "?cinemaID=" + updatedRoom.getCinemaID());
        } catch (Exception e) {
            throw new ServletException("Error updating room information", e);
        }
    }


}
