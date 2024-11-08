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

@WebServlet(name = "historyOrders", urlPatterns = {"order/history"})
public class HistoryOrdersServlet extends HttpServlet {

    private HistoryOrderDAO historyOrderDAO;
    public static final String LANDING_PAGE = "/page/landingPage/LandingPage.jsp";

    @Override
    public void init() throws ServletException {
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

        if (userID == null) {
            System.out.println("userID không có trong session");
            request.getRequestDispatcher("page/auth/Login.jsp").forward(request, response);
            return;
        }

        try {
            List<HashMap<String, Object>> ordersList = historyOrderDAO.getHistoryOrdersByUserId(userID);
            request.setAttribute("historyOrders", ordersList);
            request.getRequestDispatcher(RouterJSP.HISTORY_ORDER).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(HistoryOrdersServlet.class.getName()).log(Level.SEVERE, "Error fetching order history", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to fetch order history");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("back".equals(action)) {
            // Forward to the landing page
            request.getRequestDispatcher(LANDING_PAGE).forward(request, response);
        } else {
            doGet(request, response);
        }
    }
}
