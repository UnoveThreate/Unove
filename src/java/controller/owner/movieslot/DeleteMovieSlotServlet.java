package controller.owner.movieslot;

import DAO.cinemaChainOwnerDAO.MovieSlotDAO;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/owner/movieSlot/deleteMovieSlot")
public class DeleteMovieSlotServlet extends HttpServlet {

    private MovieSlotDAO movieSlotDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            movieSlotDAO = new MovieSlotDAO(context);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize MovieSlotDAO", e);
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

        // Lấy movieSlotID từ request
        String movieSlotIDStr = request.getParameter("movieSlotID");
        if (movieSlotIDStr == null || movieSlotIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT); // Redirect nếu movieSlotID không được cung cấp
            return;
        }

        try {
            Integer movieSlotID = Integer.parseInt(movieSlotIDStr); // Chuyển đổi movieSlotID sang Integer

            // Gọi phương thức xóa mềm movie slot
            movieSlotDAO.softDeleteMovieSlot(movieSlotID); // Xóa mềm movie slot khỏi cơ sở dữ liệu

            // Chuyển hướng về trang quản lý movie slot
            response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT); // Có thể chuyển hướng với cinemaID nếu cần
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT); // Redirect nếu movieSlotID không phải là số hợp lệ
        } catch (SQLException e) {
            throw new ServletException("Failed to delete movie slot", e);
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

        // Lấy movieSlotID từ request
        String movieSlotIDStr = request.getParameter("movieSlotID");
        String cinemaIDStr = request.getParameter("cinemaID");
        if (movieSlotIDStr == null || movieSlotIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT); // Redirect nếu movieSlotID không được cung cấp
            return;
        }

        try {
            Integer movieSlotID = Integer.parseInt(movieSlotIDStr); // Chuyển đổi movieSlotID sang Integer

            // Gọi phương thức xóa mềm movie slot
            movieSlotDAO.softDeleteMovieSlot(movieSlotID); // Xóa mềm movie slot khỏi cơ sở dữ liệu

            // Chuyển hướng về trang quản lý movie slot
            if (cinemaIDStr != null && !cinemaIDStr.isEmpty()) {
                response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT + "?cinemaID=" + cinemaIDStr);
            } else {
                response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT); // Redirect nếu không có cinemaID
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_MOVIE_SLOT); // Redirect nếu movieSlotID không phải là số hợp lệ
        } catch (SQLException e) {
            throw new ServletException("Failed to delete movie slot", e);
        }
    }
}
