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
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

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
            String userRole = (String) session.getAttribute("role");

            if (userRole == null) {
                response.sendRedirect(RouterURL.LOGIN);
                return;
            }
            boolean isOwnerRole = Role.isRoleValid(userRole, Role.OWNER);
            if (!isOwnerRole) {
                // Redirect to error page if role is invalid
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }

            // Retrieve parameters from the URL
            String orderIdParam = request.getParameter("orderID");
            String userIdParam = request.getParameter("userID");
            String codeParam = request.getParameter("code");

            int orderId = 0;
            int userId = 0;
            boolean IsParametersValid = true;

            try {
                orderId = Integer.parseInt(orderIdParam);
            } catch (NumberFormatException e) {
                IsParametersValid = false;
                e.printStackTrace();
                // Handle invalid orderID
            }

            try {
                userId = Integer.parseInt(userIdParam);
            } catch (NumberFormatException e) {
                IsParametersValid = false;
                e.printStackTrace();
                // Handle invalid userID
            }

            if (!IsParametersValid) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }

            // Forward to the confirmation JSP with order details
            request.setAttribute("code", codeParam);

            request.setAttribute("orderID", orderId);
            request.setAttribute("userID", userId);
            request.getRequestDispatcher(RouterJSP.CONFIRM_TICKET).forward(request, response);
        } catch (Exception ex) { // Catch general exceptions instead of SQLException
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

        boolean isOrderConfirmed = confirmDAO.checkConfirmOrder(orderID, userID, codeParam);
        boolean isTicketConfirmed = confirmDAO.checkConfirmTicket(orderID);
        String resultMessage;

        if (isOrderConfirmed || isTicketConfirmed) {

            resultMessage = "Xác nhận vé đặt thành công.";
        } else {
            resultMessage = "Đã xảy ra lỗi khi cập nhật trạng thái vé.";
        }

        request.setAttribute("message", resultMessage);
        request.getRequestDispatcher(RouterJSP.VIEW_RESULT_TICKET).forward(request, response);
    }
}
