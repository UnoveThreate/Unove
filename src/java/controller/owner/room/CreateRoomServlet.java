package controller.owner.room;

import DAO.cinemaChainOwnerDAO.RoomDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import model.owner.Room;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/room/createRoom")
@MultipartConfig
public class CreateRoomServlet extends HttpServlet {

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

        // Lấy cinemaID từ request
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not provided
            return;
        }

        try {
            Integer cinemaID = Integer.parseInt(cinemaIDStr); // Parse cinemaID to Integer
            request.setAttribute("cinemaID", cinemaID); // Gán cinemaID cho request

            // Chuyển hướng đến trang tạo phòng
            request.getRequestDispatcher(RouterJSP.OWNER_ROOM_CREATE_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not a valid integer
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");
        Integer cinemaID = Integer.parseInt(request.getParameter("cinemaID")); // Lấy cinemaID từ request

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Lấy thông tin phòng từ request
        String roomName = request.getParameter("roomName");
        String[] roomTypeArray = request.getParameterValues("roomType"); // Lấy danh sách các loại phòng từ form
        List<String> roomTypes = Arrays.asList(roomTypeArray); // Chuyển thành List<String>

        // Tạo một đối tượng Room mới
        Room newRoom = new Room();
        newRoom.setRoomName(roomName);
        newRoom.setRoomTypes(roomTypes);
        newRoom.setCinemaID(cinemaID); // Gán cinemaID cho phòng

        try {
            // Thêm phòng vào cơ sở dữ liệu
            roomDAO.createRoom(newRoom); // Gọi phương thức createRoom
            // Chuyển hướng về trang danh sách phòng của cinema
            response.sendRedirect(RouterURL.MANAGE_ROOM + "?cinemaID=" + cinemaID);
        } catch (Exception e) {
            throw new ServletException("Error creating new room", e);
        }
    }
}
