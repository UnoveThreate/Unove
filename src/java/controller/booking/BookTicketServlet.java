/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.booking;

import DAO.canteenItem.CanteenItemSelectDAO;
import DAO.payment.PaymentDAO;
import model.BookingSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.CanteenItem;
import model.Cinema;
import model.Movie;
import model.canteenItemTotal.CanteenItemOrder;
import util.RouterJSP;

/**
 *
 * @author DELL
 */
@WebServlet(name = "BookTicketServlet", urlPatterns = {"/bookTicket"})
public class BookTicketServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(BookTicketServlet.class.getName());
    private PaymentDAO paymentDAO;
    private CanteenItemSelectDAO canteenItemselect;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();

            this.paymentDAO = new PaymentDAO(context);
            this.canteenItemselect = new CanteenItemSelectDAO(context);
            LOGGER.info("SelectSeatServlet initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing SelectSeatServlet", e);
            throw new ServletException("Không thể khởi tạo DAO", e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookTicketServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookTicketServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

        if (bookingSession == null) {
            request.getRequestDispatcher(RouterJSP.LOGIN).forward(request, response);
            return;
        }

        Integer movieSlotID = bookingSession.getMovieSlotID();
        List<CanteenItemOrder> canteenItems = bookingSession.getItemOrders();
        List<Map<String, Object>> itemDetails = new ArrayList<>();

        for (CanteenItemOrder itemOrder : canteenItems) {
            int canteenItemID = itemOrder.getCanteenItemID();
            int quantity = itemOrder.getQuantity();

            CanteenItem canteenItem = canteenItemselect.getItemBycanteenItemID(canteenItemID);

            if (canteenItem != null) {
                Map<String, Object> itemDetail = new HashMap<>();
                itemDetail.put("name", canteenItem.getName());
                itemDetail.put("price", canteenItem.getPrice());
                itemDetail.put("quantity", quantity);
                itemDetails.add(itemDetail);
            } else {
                LOGGER.warning("Canteen item with ID " + canteenItemID + " not found.");
            }
        }

        request.setAttribute("selectedCanteenItems", itemDetails);

        // Lấy các thuộc tính khác từ `bookingSession` nếu cần thiết
        request.setAttribute("movie", paymentDAO.getMovieByMovieSlotID(movieSlotID));
        request.setAttribute("cinema", paymentDAO.getCinemaByMovieSlot(movieSlotID));
        request.setAttribute("movieSlot", bookingSession.getMovieSlot());
        request.setAttribute("selectedSeats", bookingSession.getListSeats());
        request.setAttribute("totalPrice", bookingSession.getTotalPrice());

//        bookingSession.clearItem();

        // Điều hướng đến trang JSP hiển thị
        request.getRequestDispatcher(RouterJSP.ORDER_DETAIL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
