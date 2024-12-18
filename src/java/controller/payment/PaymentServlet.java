package controller.payment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import DAO.payment.PaymentDAO;
import DAO.payment.OrderDAO;
import DAO.ticket.TicketDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.booking.BookTicketServlet;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BookingSession;
import model.Order;
import model.Seat;
import util.RouterJSP;
import util.RouterURL;
import util.Util;
import util.VnPayConfig;

/**
 *
 * @author DELL
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/payment/vnpay"})
public class PaymentServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PaymentServlet.class.getName());
    private OrderDAO orderDAO;
    private TicketDAO ticketDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();

            this.orderDAO = new OrderDAO(context);
            this.ticketDAO = new TicketDAO(context);
            LOGGER.info("SelectSeatServlet initialized successfully");

        } catch (Exception e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, e);
            LOGGER.log(Level.SEVERE, "Error initializing SelectSeatServlet", e);
            throw new ServletException("Không thể khởi tạo DAO", e);
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Payment Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Payment Processing</h1>");
            // Thêm thông tin về thanh toán ở đây
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
            // lấy từ session ra
            HttpSession session = request.getSession();
            BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

            // lấy userID trực tiếp từ người dùng
            Integer userID = (Integer) session.getAttribute("userID");

            if (bookingSession == null && userID == null) {
                request.setAttribute("errorMessage", "Thông tin đặt vé không đầy đủ");
                response.sendRedirect(RouterURL.LOGIN);
                return;
            }

            //set userId vào session
            session.setAttribute("userID", userID);

            // Lấy các thuộc tính trong session
            int movieSlotID = bookingSession.getMovieSlotID();
            double totalPrice = bookingSession.getTotalPrice();
            String status = "Pending";
            List<Seat> listSeats = bookingSession.getListSeats();

            // Lấy premiumTypeID từ bảng user
            Integer premiumTypeID = orderDAO.getPremiumTypeIDByUserId(userID);
//            if (premiumTypeID == null) {
//                request.setAttribute("errorMessage", "Không tìm thấy thông tin người dùng.");
//                request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
//                return;
//            }

            // Tạo order ban đầu mà không có code và QR code
            int orderID = orderDAO.insertOrder(userID, movieSlotID, premiumTypeID, status, null, null);

            if (orderID != -1) {
                ticketDAO.insertTickets(orderID, listSeats, "Pending");
                setOrderCookies(response, orderID, userID, movieSlotID, totalPrice, premiumTypeID, "Pending");
                PayMentService(orderID, totalPrice, request, response);
            } else {
                response.getWriter().println("Đã có lỗi xảy ra khi tạo đơn hàng.");
            }
        } catch (Exception e) {
            Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void PayMentService(int OrderID, double totalPrice, HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, IOException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        System.out.println("amout:" + (String) req.getParameter("amount"));

        int amount = (int) (totalPrice * 100);
        System.out.println("Total Price: " + totalPrice);

        String bankCode = req.getParameter("bankCode");

        System.out.println("Order ID Payment: " + OrderID);

        String vnp_TxnRef = String.valueOf(OrderID);

        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();

        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = req.getParameter("language");

        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }

        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 60 * 24);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();

        res.sendRedirect(paymentUrl);

    }

    private String hmacSHA512(String key, String data) {
        // Thực hiện mã hóa HMAC SHA512 (sử dụng thư viện hoặc tự implement)
        return ""; // Mã checksum
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    private void setOrderCookies(HttpServletResponse response, int orderID, int userID, int movieSlotID,
            double totalPrice, Integer premiumTypeID, String status) {
        Cookie orderIdCookie = new Cookie("orderID", String.valueOf(orderID));
        Cookie userIdCookie = new Cookie("userID", String.valueOf(userID));
        Cookie movieSlotIdCookie = new Cookie("movieSlotID", String.valueOf(movieSlotID));
        Cookie totalPriceCookie = new Cookie("totalPrice", String.valueOf(totalPrice));
        Cookie premiumTypeIdCookie = new Cookie("premiumTypeID", String.valueOf(premiumTypeID));
        Cookie statusCookie = new Cookie("Pending", status);

        // Thiết lập thời gian tồn tại của cookies (ví dụ: 30 phút)
        int cookieExpiry = 30 * 60;
        orderIdCookie.setMaxAge(cookieExpiry);
        userIdCookie.setMaxAge(cookieExpiry);
        movieSlotIdCookie.setMaxAge(cookieExpiry);
        totalPriceCookie.setMaxAge(cookieExpiry);
        premiumTypeIdCookie.setMaxAge(cookieExpiry);
        statusCookie.setMaxAge(cookieExpiry);

        // Thêm cookies vào phản hồi
        response.addCookie(orderIdCookie);
        response.addCookie(userIdCookie);
        response.addCookie(movieSlotIdCookie);
        response.addCookie(totalPriceCookie);
        response.addCookie(premiumTypeIdCookie);
        response.addCookie(statusCookie);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
