package controller.owner.confirm;

import DAO.owner.confirm.ConfirmDAO;
import DAO.payment.OrderDAO;
import model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Seat;
import util.Role;
import util.RouterJSP;
import util.RouterURL;
import model.canteenItemTotal.CanteenItemOrder;
import model.booking.OrderDetail;

@WebServlet(name = "OrderConfirmServlet", urlPatterns = {"/order/confirm"})
public class ConfirmOrderServlet extends HttpServlet {

    private OrderDAO orderDAO;
    private ConfirmDAO confirmDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {
            orderDAO = new OrderDAO(getServletContext());
            confirmDAO = new ConfirmDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(ConfirmOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("role");

            if (role == null) {
                response.sendRedirect(RouterURL.LOGIN);
                return;
            }

            boolean isValidRole = Role.isRoleValid(role, Role.OWNER);

            if (!isValidRole) {
                // Redirect to error page if role is invalid
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }

            String orderIdParam = request.getParameter("orderID");
            String userIdParam = request.getParameter("userID");
            String code = request.getParameter("code");

            int orderId = 0;
            int userId = 0;
            boolean validParams = true;

            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                validParams = false;
                e.printStackTrace();
                // Handle invalid orderID
            }

            try {
                userId = Integer.parseInt(userIdParam);
            } catch (NumberFormatException e) {
                validParams = false;
                e.printStackTrace();
                // Handle invalid userID
            }
            //
            if (!validParams) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }
            boolean isValidQrCode = confirmDAO.isValidQRCodeOrder(orderId, userId, code);

            System.out.println("valid qrcode :" + isValidQrCode);

            if (!isValidQrCode) {

                request.setAttribute("message", "Mã QR Không Tồn Tại!");
                request.getRequestDispatcher(RouterJSP.VIEW_RESULT_TICKET).forward(request, response);
                return;

            }
            //
            OrderDetail orderDetails = orderDAO.getOrderDetails(orderId);
            List<Seat> seats = orderDAO.getSeatsByOrderID(orderId);
            List<CanteenItemOrder> canteenItems = orderDAO.getCanteenItemsByOrderID(orderId);
            System.out.println("seats" + seats);
            System.out.println("canteenItems" + canteenItems);
            request.setAttribute("orderDetails", orderDetails);
            request.setAttribute("seats", seats);
            request.setAttribute("canteenItems", canteenItems);
            request.setAttribute("orderID", orderId);
            request.setAttribute("userID", userId);
            request.setAttribute("code", code);
            
            //

            request.getRequestDispatcher(RouterJSP.CONFIRM_TICKET).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ConfirmOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderID");
        String userIdParam = request.getParameter("userID");
        String codeParam = request.getParameter("code");
      
    

        int orderID = 0;
        int userID = 0;

        boolean IsParametersValid = true;

        try {
            orderID = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            IsParametersValid = false;
            e.printStackTrace();
            // Handle invalid orderID
        }

        try {
            userID = Integer.parseInt(userIdParam); // Sửa lại từ orderIdStr thành userIdStr
        } catch (NumberFormatException e) {
            IsParametersValid = false;
            e.printStackTrace();
            // Handle invalid userID
        }

        if (!IsParametersValid) {
            response.sendRedirect(RouterURL.ERROR_PAGE);
            return;
        }
        System.out.println("OrderID: " + orderID);
        System.out.println("UserID: " + userID);
        System.out.println("Code: " + codeParam);

        boolean isOrderConfirmed = confirmDAO.checkConfirmOrder(orderID, userID, codeParam);
        System.out.println("isConfirmed:" + isOrderConfirmed);

        String resultMessage;

        if (isOrderConfirmed) {

            resultMessage = "Xác nhận vé đặt thành công.";
        } else {
            resultMessage = "Đã xảy ra lỗi khi cập nhật trạng thái vé.";
        }

        request.setAttribute("message", resultMessage);
        request.getRequestDispatcher(RouterJSP.VIEW_RESULT_TICKET).forward(request, response);
    }
}
