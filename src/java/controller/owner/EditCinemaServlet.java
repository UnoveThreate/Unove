/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.CinemaChainDAO;
import DAO.CinemaDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.FileUploader;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author nguyendacphong
 */
@WebServlet("/owner/edit/cinema")
public class EditCinemaServlet extends HttpServlet {

    private CinemaDAO cinemaDAO;
    RouterJSP router = new RouterJSP();

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            
            cinemaDAO = new CinemaDAO(getServletContext()); // Khởi tạo cinemaDAO
        } catch (Exception ex) {
            Logger.getLogger(CinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        try {
            Cinema cinema = cinemaDAO.getCinemaById(cinemaID);
            request.setAttribute("cinema", cinema);
            request.getRequestDispatcher(router.OWNER_EDIT_CINEMA_PAGE).forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
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
        int cinemaID = Integer.parseInt(request.getParameter("cinemaID"));
        String address = request.getParameter("address");
        String province = request.getParameter("provinceName"); // Sử dụng tên tỉnh
        String district = request.getParameter("districtName"); // Sử dụng tên quận/huyện
        String commune = request.getParameter("communeName"); // Sử dụng tên xã/phường

        Cinema cinema = new Cinema();
        cinema.setCinemaID(cinemaID);
        cinema.setAddress(address);
        cinema.setProvince(province);
        cinema.setDistrict(district);
        cinema.setCommune(commune);

        try {
            cinemaDAO.updateCinema(cinema);
            response.sendRedirect(RouterURL.OWNER_PAGE); // Quay lại trang quản lý sau khi cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }
}
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
   

