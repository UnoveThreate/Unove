package controller.dashboardowner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import DAO.dashboard.DashboardDAO;
import util.RouterJSP;

@WebServlet("/owner/dashboard")
public class OwnerDashboardServlet extends HttpServlet {
    
    private DashboardDAO dashboardDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            dashboardDAO = new DashboardDAO(getServletContext());
        } catch (Exception ex) {
            throw new ServletException("Không thể khởi tạo DashboardDAO", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Map<String, Object> dashboardData = (Map<String, Object>) session.getAttribute("dashboardData");
            
            if (dashboardData == null) {
                dashboardData = new HashMap<>();
                int testUserID = 1; // Sử dụng ID người dùng cố định để test

                // Tổng quan
                dashboardData.put("totalRevenue", dashboardDAO.getTotalRevenue(testUserID));
                dashboardData.put("totalTicketsSold", dashboardDAO.getTotalTicketsSold(testUserID));
                dashboardData.put("totalMovieSlots", dashboardDAO.getTotalMovieSlots(testUserID));
                dashboardData.put("averageSeatOccupancy", dashboardDAO.getAverageSeatOccupancy(testUserID));

                // Thông tin phim
                dashboardData.put("topMovies", dashboardDAO.getTopMovies(testUserID, 5));
                dashboardData.put("currentMoviesCount", dashboardDAO.getCurrentMoviesCount(testUserID));
                dashboardData.put("upcomingMoviesCount", dashboardDAO.getUpcomingMoviesCount(testUserID));

                // Thông tin rạp
                dashboardData.put("totalCinemas", dashboardDAO.getTotalCinemas(testUserID));
                dashboardData.put("topCinema", dashboardDAO.getTopCinema(testUserID));
                dashboardData.put("cinemaOccupancyRates", dashboardDAO.getCinemaOccupancyRates(testUserID));

                // Thông tin khách hàng
                dashboardData.put("newCustomersCount", dashboardDAO.getNewCustomersCount(testUserID));
                dashboardData.put("totalMembers", dashboardDAO.getTotalMembers(testUserID));
                dashboardData.put("topCustomers", dashboardDAO.getTopCustomers(testUserID, 5));

                // Dữ liệu cho biểu đồ
                dashboardData.put("revenueChartData", dashboardDAO.getRevenueChartData(testUserID));

                // Chuyển đổi dữ liệu thành json cho các biểu đồ
                Gson gson = new Gson();
                dashboardData.put("revenueChartDataJson", gson.toJson(dashboardData.get("revenueChartData")));
                dashboardData.put("cinemaComparisonDataJson", gson.toJson(dashboardData.get("cinemaComparisonData")));

                // Lưu dữ liệu vào session
                session.setAttribute("dashboardData", dashboardData);
            }

            request.setAttribute("dashboardData", dashboardData);
            request.getRequestDispatcher(RouterJSP.OWNER_DASHBOARD).forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Lỗi khi lấy dữ liệu dashboard", e);
        }
    }
}