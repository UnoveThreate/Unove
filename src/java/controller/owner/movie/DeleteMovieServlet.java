/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.movie;

import DAO.cinemaChainOwnerDAO.MovieDAO;
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

@WebServlet("/owner/deleteMovie")
public class DeleteMovieServlet extends HttpServlet {

    private MovieDAO movieDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            movieDAO = new MovieDAO(context);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize MovieDAO", e);
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

        // Lấy movieID từ request
        String movieIDStr = request.getParameter("movieID");
        if (movieIDStr == null || movieIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect if movieID is not provided
            return;
        }

        try {
            Integer movieID = Integer.parseInt(movieIDStr); // Parse movieID to Integer

            // Gọi phương thức xóa phim (nếu muốn có thể xác nhận trước khi xóa)
            movieDAO.deleteMovie(movieID); // Xóa phim khỏi cơ sở dữ liệu

            // Chuyển hướng về trang danh sách phim
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Có thể chuyển hướng với cinemaID nếu cần
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect if movieID is not a valid integer
        } catch (SQLException e) {
            throw new ServletException("Failed to delete movie", e);
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

        // Lấy movieID từ request
        String movieIDStr = request.getParameter("movieID");
        String cinemaIDStr = request.getParameter("cinemaID");
        if (movieIDStr == null || movieIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect if movieID is not provided
            return;
        }

        try {
            Integer movieID = Integer.parseInt(movieIDStr); // Parse movieID to Integer

            // Gọi phương thức xóa phim
            movieDAO.deleteMovie(movieID); // Xóa phim khỏi cơ sở dữ liệu

            // Chuyển hướng về trang danh sách phim
            if (cinemaIDStr != null && !cinemaIDStr.isEmpty()) {
                response.sendRedirect(RouterURL.MANAGE_MOVIES + "?cinemaID=" + cinemaIDStr);
            } else {
                response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect nếu không có cinemaID
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_MOVIES); // Redirect if movieID is not a valid integer
        } catch (SQLException e) {
            throw new ServletException("Failed to delete movie", e);
        }
    }
}
