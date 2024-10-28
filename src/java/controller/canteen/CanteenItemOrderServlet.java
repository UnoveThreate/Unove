/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.canteen;

import DAO.canteenItem.CanteenItemSelectDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.BookingSession;
import model.canteenItemTotal.CanteenItemOrder;
import util.RouterURL;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CanteenItem;

/**
 *
 * @author ASUS
 */
@WebServlet("/submitCanteenItems")
public class CanteenItemOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CanteenItemSelectDAO canteenItemSelect;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Khởi tạo các DAO từ servlet context

            canteenItemSelect = new CanteenItemSelectDAO(getServletContext());
        } catch (Exception ex) {
            // Ghi log lỗi nếu có vấn đề trong khởi tạo
            Logger.getLogger(CanteenItemOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            out.println("<title>Servlet CanteenItemOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CanteenItemOrderServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

        if (bookingSession == null) {
            bookingSession = new BookingSession();
        } else if ( bookingSession.getItemOrders() != null && bookingSession.getItemOrders().isEmpty() == false) {
            bookingSession.getItemOrders().clear();
            session.setAttribute("bookingSession", bookingSession);
        }

        // Lấy totalPrice từ SelectSeatServlet (nếu có) và giữ giá trị đã lưu
        double totalPrice = bookingSession.getTotalPrice();

        // Duyệt qua các tham số để tìm và xử lý các `quantity` của `canteenItemID`
        for (String param
                : request.getParameterMap()
                        .keySet()) {
            if (param.startsWith("quantity_")) {
                try {
                    int canteenItemID = Integer.parseInt(param.split("_")[1]);
                    int quantity = Integer.parseInt(request.getParameter(param));

                    if (quantity > 0) {
                        // Thêm `canteenItemOrder` vào `BookingSession`
                        bookingSession.addCanteenItemOrder(canteenItemID, quantity);

                    }
                } catch (NumberFormatException e) {
                    // Xử lý nếu có lỗi định dạng số
                    e.printStackTrace();
                }
            }
        }
        double canteenTotalPrice = calculateCanteenTotalPrice(bookingSession.getItemOrders());
        totalPrice += canteenTotalPrice;

        bookingSession.setTotalPrice(totalPrice);

        session.setAttribute("bookingSession", bookingSession);

        // Điều hướng đến trang xác nhận thanh toán hoặc trang tiếp theo
        response.sendRedirect(RouterURL.ORDER_DETAIL);
    }

    private double calculateCanteenTotalPrice(List<CanteenItemOrder> canteenItemOrders) {
        double totalPrice = 0.0;
        for (CanteenItemOrder itemOrder : canteenItemOrders) {
            double itemPrice = canteenItemSelect.getCanteenItemPriceById(itemOrder.getCanteenItemID());
            totalPrice = itemPrice * itemOrder.getQuantity();
        }
        return totalPrice;
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
