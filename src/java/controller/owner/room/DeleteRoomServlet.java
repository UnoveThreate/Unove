package controller.owner.room;

import DAO.cinemaChainOwnerDAO.RoomDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.Role;
import util.RouterURL;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/owner/deleteRoom")
public class DeleteRoomServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        Integer roomID = Integer.parseInt(request.getParameter("roomID")); // Lấy RoomID từ form

        try {
            roomDAO.deleteRoom(roomID); // Gọi phương thức xóa trong RoomDAO
            response.sendRedirect(RouterURL.MANAGE_ROOM + "?cinemaID=" + request.getParameter("cinemaID")); // Redirect về trang quản lý phòng
        } catch (SQLException e) {
            throw new ServletException("Error deleting room", e);
        }
    }
}
