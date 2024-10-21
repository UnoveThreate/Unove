package controller.owner;

import DAO.cinemaChainOwnerDAO.CinemaDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Role;
import util.RouterURL;

@WebServlet("/owner/delete/cinema")
public class DeleteCinemaServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(DeleteCinemaServlet.class.getName());
    private CinemaDAO cinemaDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            cinemaDAO = new CinemaDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(DeleteCinemaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra xem người dùng đã đăng nhập và có vai trò OWNER hay không
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        String cinemaIDStr = request.getParameter("cinemaID");
        int cinemaID;

        try {
            cinemaID = Integer.parseInt(cinemaIDStr);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Invalid cinema ID format", e);
            response.sendRedirect(RouterURL.ERROR_PAGE);
            return;
        }

        try {
            // Gọi phương thức xóa cinema từ DAO
            boolean isDeleted = cinemaDAO.deleteCinema(cinemaID);
            if (isDeleted) {
                // Nếu xóa thành công, chuyển hướng về trang chủ hoặc trang danh sách rạp
                response.sendRedirect(RouterURL.MANAGE_CINEMA);
            } else {
                // Nếu không xóa được, chuyển hướng về trang lỗi
                response.sendRedirect(RouterURL.ERROR_PAGE);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during deletion", e);
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for deleting cinemas";
    }
}
