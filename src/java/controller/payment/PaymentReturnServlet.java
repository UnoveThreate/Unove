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

// Định nghĩa servlet xử lý phản hồi từ VNPAY
@WebServlet(name = "VnPayReturnServlet", urlPatterns = {"/payment/vnpay/return"})
public class PaymentReturnServlet extends HttpServlet {

    // Khai báo các đối tượng DAO cần thiết
    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;
    private TicketDAO ticketDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Khởi tạo các DAO từ servlet context
            paymentDAO = new PaymentDAO(getServletContext());
            orderDAO = new OrderDAO(getServletContext());
            ticketDAO = new TicketDAO(getServletContext());
            userDAO = new UserDAO(getServletContext());
        } catch (Exception ex) {
            // Ghi log lỗi nếu có vấn đề trong khởi tạo
            Logger.getLogger(PaymentReturnServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy session và BookingSession từ request
        HttpSession session = request.getSession();
        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

        // Lấy userID từ session
        Integer userId = (Integer) session.getAttribute("userID");
        String email = (String) session.getAttribute("email");
        String amountStr = request.getParameter("vnp_Amount"); // Lấy số tiền thanh toán
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode"); // Mã phản hồi từ VNPAY
        String vnp_TxnRef = request.getParameter("vnp_TxnRef"); // Mã giao dịch từ VNPAY
        List<Seat> listSeats = bookingSession.getListSeats(); // Danh sách ghế đã chọn

        // Kiểm tra userId và số tiền
        if (userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "UserID not found in session!"); // Nếu không có userId, trả lỗi
            return;
        }

        // Kiểm tra số tiền nhận được
        if (amountStr == null || amountStr.isEmpty()) {
            response.getWriter().write("Invalid amount!"); // Nếu số tiền không hợp lệ, trả thông báo
            return;
        }

        // Chuyển đổi mã giao dịch từ String sang int
        int orderID = Integer.parseInt(vnp_TxnRef);
        // Kiểm tra mã phản hồi để xử lý thanh toán thành công hay thất bại
        if ("00".equals(vnp_ResponseCode)) {

            try {
                // Cập nhật trạng thái đơn hàng thành công
                if (email == null) {
                    email = userDAO.getEmailByUserId(userId); // Lấy email của người dùng
                }
                orderDAO.updateOrderStatus(orderID, "success");

                for (Seat seat : listSeats) {
                    ticketDAO.updateTicketStatus(orderID, seat.getSeatID(), "Success");
                }

                // Cập nhật trạng thái vé thành công
                String code = Util.generateActivationCodeOrder(); // Tạo mã kích hoạt đơn hàng
                System.out.println("Generated activation code: " + code);
                String qrCodeText = "http://localhost:8080/Unove/order/confirm?orderID=" + orderID + "&userID=" + userId + "&code=" + code; // Tạo URL cho QR code
                String fileName = "qrcode_" + orderID + "_" + userId; // Tên file QR code
                String uploadFolder = "QRCode_F"; // Thư mục tải lên QR code

                // Tạo và tải lên QR code
                String qrCode = Util.generateQRCodeAndUpload(qrCodeText, fileName, uploadFolder);
                // Cập nhật mã và QR code cho đơn hàng trong cơ sở dữ liệu
                boolean isUpdated = orderDAO.updateOrderWithCodeAndQRCode(orderID, code, qrCode);

                if (isUpdated) {
                    // Gửi email xác nhận
                    EmailService emailService = new EmailService(ticketDAO);
                    try {
                        boolean emailSent = emailService.sendEmailTicketOrder(email, code, qrCode, orderID); // Gửi email xác nhận vé
                        if (emailSent) {
                            System.out.println("Email sent successfully!"); // Thông báo gửi email thành công
                        } else {
                            System.out.println("Failed to send email."); // Thông báo gửi email thất bại
                        }
                    } catch (Exception e) {
                        System.out.println("Error sending email: " + e.getMessage()); // Xử lý lỗi gửi email
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Failed to update order code and QR code."); // Thông báo cập nhật mã và QR code thất bại
                }
            } catch (Exception e) {
                System.out.println("Error generating or uploading QR code: " + e.getMessage()); // Xử lý lỗi tạo hoặc tải lên QR code
                e.printStackTrace();
            }

            request.setAttribute("message", "Success");
        } else {
            // Nếu thanh toán thất bại
            orderDAO.updateOrderStatus(orderID, "Failed"); // Cập nhật trạng thái đơn hàng thành công
            for (Seat seat : listSeats) {
                // Cập nhật trạng thái ghế thất bại
                ticketDAO.updateTicketStatus(orderID, seat.getSeatID(), "Failed");
            }

            request.setAttribute("message", "Failed"); // Thiết lập thông báo thanh toán thất bại
        }

        // Thiết lập các thuộc tính giao dịch cho request để hiển thị
        request.setAttribute("vnp_TxnRef", vnp_TxnRef);
        request.setAttribute("vnp_Amount", amountStr);
        request.setAttribute("vnp_OrderInfo", request.getParameter("vnp_OrderInfo"));
        request.setAttribute("vnp_ResponseCode", vnp_ResponseCode);
        request.setAttribute("vnp_TransactionNo", request.getParameter("vnp_TransactionNo"));
        request.setAttribute("vnp_BankCode", request.getParameter("vnp_BankCode"));
        request.setAttribute("vnp_PayDate", request.getParameter("vnp_PayDate"));
        request.setAttribute("vnp_TransactionStatus", request.getParameter("vnp_TransactionStatus"));

        // Chuyển tiếp đến trang kết quả giao dịch
        request.getRequestDispatcher(RouterJSP.RETURN_TRACSACTION_BOOKING_TICKET).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Xử lý yêu cầu POST như yêu cầu GET
    }

    @Override
    public String getServletInfo() {
        return "Payment Return Servlet for handling VNPAY response."; // Thông tin về servlet
    }
}
