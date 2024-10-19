package controller.dashboardowner;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import com.google.gson.Gson;
import DAO.dashboardowner.DashboardDAO;
import util.RouterJSP;
import model.dashboardowner.DashboardData;

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
            DashboardData dashboardData = (DashboardData) session.getAttribute("dashboardData");
            
            boolean refreshData = "true".equals(request.getParameter("refresh"));
            
            if (dashboardData == null || refreshData) {
                dashboardData = new DashboardData();
                int testUserID = 1; // Sử dụng ID người dùng cố định để test

                // Tổng quan
                dashboardData.setTotalRevenue(dashboardDAO.getTotalRevenue(testUserID, refreshData));
                dashboardData.setTotalTicketsSold(dashboardDAO.getTotalTicketsSold(testUserID, refreshData));
                dashboardData.setTotalMovieSlots(dashboardDAO.getTotalMovieSlots(testUserID, refreshData));
                dashboardData.setAverageSeatOccupancy(dashboardDAO.getAverageSeatOccupancy(testUserID, refreshData));

                // Thông tin phim
                dashboardData.setTopMovies(dashboardDAO.getTopMovies(testUserID, 5, refreshData));
                dashboardData.setCurrentMoviesCount(dashboardDAO.getCurrentMoviesCount(testUserID, refreshData));
                dashboardData.setUpcomingMoviesCount(dashboardDAO.getUpcomingMoviesCount(testUserID, refreshData));

                // Thông tin rạp
                dashboardData.setTotalCinemas(dashboardDAO.getTotalCinemas(testUserID, refreshData));
                dashboardData.setTopCinema(dashboardDAO.getTopCinema(testUserID, refreshData));
                dashboardData.setCinemaOccupancyRates(dashboardDAO.getCinemaOccupancyRates(testUserID, refreshData));

                // Thông tin khách hàng
                dashboardData.setNewCustomersCount(dashboardDAO.getNewCustomersCount(testUserID, refreshData));
                dashboardData.setTotalMembers(dashboardDAO.getTotalMembers(testUserID, refreshData));
                dashboardData.setTopCustomers(dashboardDAO.getTopCustomers(testUserID, 5, refreshData));

                // Thống kê doanh thu của chuỗi rạp và từng rạp
                dashboardData.setRevenueStats(dashboardDAO.getRevenueStats(testUserID, refreshData));

                // Thống kê số lượng vé của chuỗi rạp và từng rạp
                dashboardData.setTicketStats(dashboardDAO.getTicketStats(testUserID, refreshData));

                // Thống kê doanh thu theo từng bộ phim toàn rạp
                dashboardData.setMovieRevenueStats(dashboardDAO.getMovieRevenueStats(testUserID, refreshData));

                // Lấy thông tin lịch chiếu các bộ phim sắp tới
                dashboardData.setUpcomingMovieSchedule(dashboardDAO.getUpcomingMovieSchedule(testUserID, 7, 10, refreshData));

                // dữ liệu thành json cho các biểu đồ
                Gson gson = new Gson();
                dashboardData.setRevenueChartDataJson(gson.toJson(dashboardDAO.getRevenueChartData(testUserID, refreshData)));
                dashboardData.setCinemaComparisonDataJson(gson.toJson(dashboardData.getCinemaOccupancyRates()));
                dashboardData.setRevenueStatsJson(gson.toJson(dashboardData.getRevenueStats()));
                dashboardData.setTicketStatsJson(gson.toJson(dashboardData.getTicketStats()));
                dashboardData.setMovieRevenueStatsJson(gson.toJson(dashboardData.getMovieRevenueStats()));
                dashboardData.setUpcomingMovieScheduleJson(gson.toJson(dashboardData.getUpcomingMovieSchedule()));

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