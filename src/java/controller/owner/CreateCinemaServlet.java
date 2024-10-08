/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import DAO.cinemaChainOwnerDAO.CinemaDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import model.CinemaChain;
import util.FileUploader;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author Per
 */
@WebServlet("/owner/create/cinema")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 30, // 30 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB 
)
public class CreateCinemaServlet extends HttpServlet {

    private final RouterJSP router = new RouterJSP();
    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            cinemaChainDAO = new CinemaChainDAO((ServletContext) getServletContext());
            cinemaDAO = new CinemaDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CreateCinemaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = (HttpSession) request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            // note for sendRedirect 
            response.sendRedirect(RouterURL.LOGIN);

            return;
        }
        try {
            CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserID(userID);

            if (cinemaChain == null) {
                // Nếu user chưa có CinemaChain, yêu cầu tạo mới

                request.getRequestDispatcher(router.OWNER_CMC).forward(request, response);
            } else {
                // Nếu đã có, chuyển hướng tới trang quản lý

                String cinemaChainIDStr = request.getParameter("cinemaChainID");
                request.setAttribute("cinemaChainID", cinemaChainIDStr);
                request.getRequestDispatcher(RouterJSP.OWNER_CREATE_CINEMA_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String province = request.getParameter("provinceName"); // Sử dụng tên tỉnh
        String district = request.getParameter("districtName"); // Sử dụng tên quận/huyện
        String commune = request.getParameter("communeName"); // Sử dụng tên xã/phường
        String cinemaChainIDStr = request.getParameter("cinemaChainID");
        int cinemaChainID = Integer.parseInt(cinemaChainIDStr);

        // Tạo đối tượng Cinema
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setAddress(address);
        cinema.setProvince(province);
        cinema.setDistrict(district);
        cinema.setCommune(commune);
        cinema.setCinemaChainID(cinemaChainID);

        try {
            // Lưu cinema vào cơ sở dữ liệu
            cinemaDAO.createCinema(cinema);
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect to owner page after creation
        } catch (SQLException e) {
            Logger.getLogger(CreateCinemaServlet.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect(RouterURL.ERROR_PAGE); // Redirect to error page if there's an issue
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
