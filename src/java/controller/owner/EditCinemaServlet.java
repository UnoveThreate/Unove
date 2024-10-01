/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.cinemaChainOwnerDAO.CinemaDAO;
import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/edit/cinema")

public class EditCinemaServlet extends HttpServlet {

    private CinemaDAO cinemaDAO;
    private CinemaChainDAO cinemaChainDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            cinemaDAO = new CinemaDAO((ServletContext) getServletContext());
            cinemaChainDAO = new CinemaChainDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(EditCinemaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        try {
            String cinemaIDStr = request.getParameter("cinemaID");
            int cinemaID = Integer.parseInt(cinemaIDStr);

            Cinema cinema = cinemaDAO.getCinemaById(cinemaID);

            if (cinema == null) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }

            // Lấy thông tin về tỉnh, quận, huyện hiện tại của rạp
            String province = cinema.getProvince();
            String district = cinema.getDistrict();
            String commune = cinema.getCommune();

            // Gửi thông tin rạp hiện tại và các trường tỉnh/quận/huyện tới JSP
            request.setAttribute("cinema", cinema);
            request.setAttribute("currentProvince", province);
            request.setAttribute("currentDistrict", district);
            request.setAttribute("currentCommune", commune);

            // Forward tới trang JSP chỉnh sửa
            request.getRequestDispatcher(RouterJSP.OWNER_EDIT_CINEMA_PAGE).forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            Logger.getLogger(EditCinemaServlet.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String address = request.getParameter("address");
        String province = request.getParameter("provinceName"); // Sử dụng tên tỉnh
        String district = request.getParameter("districtName"); // Sử dụng tên quận/huyện
        String commune = request.getParameter("communeName"); // Sử dụng tên xã/phường

        // Tạo đối tượng Cinema
        Cinema cinema = new Cinema();
        cinema.setAddress(address);
        cinema.setProvince(province);
        cinema.setDistrict(district);
        cinema.setCommune(commune);
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        cinema.setCinemaID(cinemaID);

        try {
            // Lưu cinema vào cơ sở dữ liệu
            cinemaDAO.updateCinema(cinema);
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect to owner page after creation
        } catch (SQLException e) {
            Logger.getLogger(CreateCinemaServlet.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect(RouterURL.ERROR_PAGE); // Redirect to error page if there's an issue
        }
    }

}
