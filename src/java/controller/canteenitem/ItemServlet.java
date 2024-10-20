/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.canteenitem;

import DAO.landingPageMovieDAO.MovieDAO;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import Order.OrderDAO;
import jakarta.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CanteenItem;
import util.RouterJSP;

/**
 *
 * @author ASUS
 */
@WebServlet("/canteenItem")

public class ItemServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        try {
            ServletContext context = getServletContext();
            orderDAO = new OrderDAO(context);  // Initialize MovieDAO using the ServletContext
        } catch (Exception e) {
            throw new ServletException("Error initializing MovieDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cinemaIDParam = request.getParameter("cinemaID");
        List<CanteenItem> canteenItemList = null;

        // Kiểm tra xem cinemaID có hợp lệ không
        if (cinemaIDParam == null || cinemaIDParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing cinemaID");
            return;
        }

        int cinemaID;
        try {
            cinemaID = Integer.parseInt(cinemaIDParam); // Chuyển đổi cinemaID sang kiểu int
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cinemaID");
            return;
        }

        try {
            canteenItemList = orderDAO.getAllCanteenItemByCinemaID(cinemaID); // Gọi phương thức với cinemaID
        } catch (SQLException ex) {
            Logger.getLogger(ItemServlet.class.getName()).log(Level.SEVERE, "Error retrieving canteen items", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve items.");
            return;
        }

        if (canteenItemList == null || canteenItemList.isEmpty()) {
            request.setAttribute("message", "No items available.");
        } else {
            request.setAttribute("canteenItemList", canteenItemList);
        }

        request.getRequestDispatcher(RouterJSP.ORDER_ITEM).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Bạn có thể thêm logic xử lý POST tại đây nếu cần
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 

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
