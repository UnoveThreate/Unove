package controller.HistoryOrdersServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.order.HistoryOrderDAO;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;

@WebServlet(name = "historyOrders", urlPatterns = {"/history"})
public class HistoryOrdersServlet extends HttpServlet {

    private HistoryOrderDAO historyOrderDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo DAO tại đây
        try {
            historyOrderDAO = new HistoryOrderDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Error initializing DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra xem userID có tồn tại trong session không
        if (userID == null) {
            System.out.println("userID không có trong session");
            response.sendRedirect("page/auth/Login.jsp"); // chuyển hướng đến trang đăng nhập nếu userID không tồn tại
            return;
        }

        try {
            // Sử dụng userID đã lấy từ session
            List<HashMap<String, Object>> ordersList = historyOrderDAO.getHistoryOrdersByUserId(userID);
            request.setAttribute("historyOrders", ordersList);

            // Chuyển đến JSP để hiển thị lịch sử đơn hàng
            request.getRequestDispatcher(RouterJSP.HISTORY_ORDER).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(HistoryOrdersServlet.class.getName()).log(Level.SEVERE, "Error fetching order history", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch order history");
        }
    }
}
