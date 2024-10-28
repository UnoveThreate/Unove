package controller.payment;

import DAO.UserDAO;
import DAO.payment.OrderDAO;
import DAO.payment.PaymentDAO;
import DAO.ticket.TicketDAO;
import model.BookingSession;
import model.Order;
import model.Seat;
import service.EmailService;
import util.RouterJSP;
import util.Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CanteenItem;
import model.canteenItemTotal.CanteenItemOrder;

@WebServlet(name = "VnPayReturnServlet", urlPatterns = {"/payment/vnpay/return"})
public class PaymentReturnServlet extends HttpServlet {

    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;
    private TicketDAO ticketDAO;
    private UserDAO userDAO;
    private static final Logger LOGGER = Logger.getLogger(PaymentReturnServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            paymentDAO = new PaymentDAO(getServletContext());
            orderDAO = new OrderDAO(getServletContext());
            ticketDAO = new TicketDAO(getServletContext());
            userDAO = new UserDAO(getServletContext());
            LOGGER.log(Level.INFO, "DAOs initialized successfully.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error initializing DAOs in PaymentReturnServlet: {0}", ex.getMessage());
            throw new ServletException("Failed to initialize DAOs.", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");
        
        Integer userId = (Integer) session.getAttribute("userID");
        String email = (String) session.getAttribute("email");
        String amountStr = request.getParameter("vnp_Amount");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        List<Seat> listSeats = bookingSession.getListSeats();
        List<CanteenItemOrder> itemOrders = bookingSession.getItemOrders();

        if (userId == null) {
            LOGGER.log(Level.WARNING, "UserID not found in session.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "UserID not found in session!");
            return;
        }

        if (amountStr == null || amountStr.isEmpty()) {
            LOGGER.log(Level.WARNING, "Invalid amount received in VNPAY response.");
            response.getWriter().write("Invalid amount!");
            return;
        }

        int orderID = Integer.parseInt(vnp_TxnRef);

        if ("00".equals(vnp_ResponseCode)) {
            try {
                if (email == null) {
                    email = userDAO.getEmailByUserId(userId);
                }
                orderDAO.updateOrderStatus(orderID, "success");
                LOGGER.log(Level.INFO, "Order status updated to 'success' for OrderID: {0}", orderID);

                for (Seat seat : listSeats) {
                    ticketDAO.updateTicketStatus(orderID, seat.getSeatID(), "Success");
                    LOGGER.log(Level.INFO, "Ticket status updated to 'Success' for SeatID: {0} in OrderID: {1}", new Object[]{seat.getSeatID(), orderID});
                }

                for (CanteenItemOrder itemOrder : itemOrders) {
                    if (itemOrder == null) {
                        LOGGER.log(Level.WARNING, "CanteenItemOrder object is null.");
                        continue;
                    }

                    Integer canteenItemID = itemOrder.getCanteenItemID();
                    Integer quantity = itemOrder.getQuantity();

                    LOGGER.log(Level.INFO, "Processing CanteenItemOrder - OrderID: {0}, CanteenItemID: {1}, Quantity: {2}", new Object[]{orderID, canteenItemID, quantity});

                    if (canteenItemID == null || quantity == null) {
                        LOGGER.log(Level.WARNING, "Invalid CanteenItemOrder data - CanteenItemID: {0}, Quantity: {1}", new Object[]{canteenItemID, quantity});
                        continue;
                    }

                    boolean isAdded = paymentDAO.addCanteenItemToOrder(orderID, canteenItemID, quantity);
                    if (isAdded) {
                        LOGGER.log(Level.INFO, "Successfully added canteen item to order with OrderID: {0}, CanteenItemID: {1}, Quantity: {2}", new Object[]{orderID, canteenItemID, quantity});
                    } else {
                        LOGGER.log(Level.SEVERE, "Failed to add canteen item to order with OrderID: {0}, CanteenItemID: {1}, Quantity: {2}", new Object[]{orderID, canteenItemID, quantity});
                    }
                }

                String code = Util.generateActivationCodeOrder();
                LOGGER.log(Level.INFO, "Generated activation code: {0} for OrderID: {1}", new Object[]{code, orderID});

                String qrCodeText = "http://54.255.150.15:8080/Unove/order/confirm?orderID=" + orderID + "&userID=" + userId + "&code=" + code;
                String fileName = "qrcode_" + orderID + "_" + userId;
                String uploadFolder = "QRCode_F";

                String qrCode = Util.generateQRCodeAndUpload(qrCodeText, fileName, uploadFolder);
                boolean isUpdated = orderDAO.updateOrderWithCodeAndQRCode(orderID, code, qrCode);

                if (isUpdated) {
                    EmailService emailService = new EmailService(ticketDAO);
                    try {
                        boolean emailSent = emailService.sendEmailTicketOrder(email, code, qrCode, orderID);
                        if (emailSent) {
                            LOGGER.log(Level.INFO, "Confirmation email sent successfully for OrderID: {0}", orderID);
                        } else {
                            LOGGER.log(Level.WARNING, "Failed to send confirmation email for OrderID: {0}", orderID);
                        }
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error sending confirmation email for OrderID: {0} - {1}", new Object[]{orderID, e.getMessage()});
                        e.printStackTrace();
                    }
                } else {
                    LOGGER.log(Level.SEVERE, "Failed to update order with code and QR code for OrderID: {0}", orderID);
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing successful payment for OrderID: {0} - {1}", new Object[]{orderID, e.getMessage()});
                e.printStackTrace();
            }

            request.setAttribute("message", "Success");
        } else {
            orderDAO.updateOrderStatus(orderID, "Failed");
            LOGGER.log(Level.INFO, "Order status updated to 'Failed' for OrderID: {0}", orderID);

            for (Seat seat : listSeats) {
                ticketDAO.updateTicketStatus(orderID, seat.getSeatID(), "Failed");
                LOGGER.log(Level.INFO, "Ticket status updated to 'Failed' for SeatID: {0} in OrderID: {1}", new Object[]{seat.getSeatID(), orderID});
            }

            request.setAttribute("message", "Failed");
        }

        request.setAttribute("vnp_TxnRef", vnp_TxnRef);
        request.setAttribute("vnp_Amount", amountStr);
        request.setAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
        request.setAttribute("vnp_ResponseCode", vnp_ResponseCode);
        request.setAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
        request.setAttribute("vnp_BankCode", request.getParameter("vnp_BankCode"));
        request.setAttribute("vnp_PayDate", request.getParameter("vnp_PayDate"));
        request.setAttribute("vnp_TransactionStatus", request.getParameter("vnp_TransactionStatus"));

        request.getRequestDispatcher(RouterJSP.RETURN_TRACSACTION_BOOKING_TICKET).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Payment Return Servlet for handling VNPAY response.";
    }
}
