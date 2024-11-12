/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.orderHistoryDetail;

import DAO.payment.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Seat;
import model.booking.OrderDetail;
import model.canteenItemTotal.CanteenItemOrder;
import util.RouterJSP;

/**
 *
 * @author DELL
 */
@WebServlet(name = "OrderHistoryDetailServlet", urlPatterns = {"/OrderHistoryDetailServlet"})
public class OrderHistoryDetailServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.orderDAO = new OrderDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(OrderHistoryDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderHistoryDetailServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderHistoryDetailServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        try {
            // Get the orderID from the request parameter
            String orderIDParam = request.getParameter("orderID");

            // Check if orderID is valid (non-null and numeric)
            if (orderIDParam != null && !orderIDParam.isEmpty()) {
                try {
                    Integer orderID = Integer.parseInt(orderIDParam);

                    // Lấy thông tin đơn hàng chi tiết từ DAO
                    OrderDetail orderDetail = orderDAO.getOrderDetails(orderID);
                    List<Seat> selectedSeats = orderDAO.getSeatsByOrderID(orderID);
                    List<CanteenItemOrder> selectedCanteenItems = orderDAO.getCanteenItemsByOrderID(orderID);
                    String qrCodeUrl = orderDAO.getQRCodeUrlByOrderID(orderID);
                    String status = orderDAO.getStatusByOrderID(orderID);

                    // Đặt các thuộc tính vào request để chuyển đến JSP hiển thị
                    request.setAttribute("orderDetail", orderDetail);
                    request.setAttribute("selectedSeats", selectedSeats);
                    request.setAttribute("selectedCanteenItems", selectedCanteenItems);
                    request.setAttribute("qrCodeUrl", qrCodeUrl);
                    request.setAttribute("status", status);

                    // Điều hướng đến trang JSP hiển thị chi tiết đơn hàng
                    request.getRequestDispatcher(RouterJSP.HISTORY_ORDER_DETAIL).forward(request, response);
                } catch (NumberFormatException e) {
                    // Handle the case where orderID is invalid (not an integer)
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID format");
                }
            } else {
                // If orderID is missing, redirect back to history page
                response.sendRedirect(request.getContextPath() + "/history");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderHistoryDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
