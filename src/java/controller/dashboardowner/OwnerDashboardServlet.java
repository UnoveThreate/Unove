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
import util.Role;
import model.dashboardowner.DashboardData;
import util.RouterURL;

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
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        //check role đăng nhập với owner
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        try {
            DashboardData dashboardData = (DashboardData) session.getAttribute("dashboardData");

            boolean refreshData = "true".equals(request.getParameter("refresh"));

            if (dashboardData == null || refreshData) {
                dashboardData = new DashboardData();

                // Tổng quan
                dashboardData.setTotalRevenue(dashboardDAO.getTotalRevenue(userID, refreshData));
                dashboardData.setTotalTicketsSold(dashboardDAO.getTotalTicketsSold(userID, refreshData));
                dashboardData.setTotalMovieSlots(dashboardDAO.getTotalMovieSlots(userID, refreshData));
                dashboardData.setAverageSeatOccupancy(dashboardDAO.getAverageSeatOccupancy(userID, refreshData));

                // Thông tin phim
                dashboardData.setTopMovies(dashboardDAO.getTopMovies(userID, 5, refreshData));
                dashboardData.setCurrentMoviesCount(dashboardDAO.getCurrentMoviesCount(userID, refreshData));
                dashboardData.setUpcomingMoviesCount(dashboardDAO.getUpcomingMoviesCount(userID, refreshData));

                // Thông tin rạp
                dashboardData.setTotalCinemas(dashboardDAO.getTotalCinemas(userID, refreshData));
                dashboardData.setTopCinema(dashboardDAO.getTopCinema(userID, refreshData));
                dashboardData.setCinemaOccupancyRates(dashboardDAO.getCinemaOccupancyRates(userID, refreshData));

                // Thông tin khách hàng
                dashboardData.setNewCustomersCount(dashboardDAO.getNewCustomersCount(userID, refreshData));
                dashboardData.setTotalMembers(dashboardDAO.getTotalMembers(userID, refreshData));
                dashboardData.setTopCustomers(dashboardDAO.getTopCustomers(userID, 5, refreshData));

                // Thống kê doanh thu của chuỗi rạp và từng rạp
                dashboardData.setRevenueStats(dashboardDAO.getRevenueStats(userID, refreshData));

                // Thống kê số lượng vé của chuỗi rạp và từng rạp
                dashboardData.setTicketStats(dashboardDAO.getTicketStats(userID, refreshData));

                // Thống kê doanh thu theo từng bộ phim toàn rạp
                dashboardData.setMovieRevenueStats(dashboardDAO.getMovieRevenueStats(userID, refreshData));

                // Lấy thông tin lịch chiếu các bộ phim sắp tới
                dashboardData.setUpcomingMovieSchedule(dashboardDAO.getUpcomingMovieSchedule(userID, 7, 10, refreshData));

                // dữ liệu thành json thống kê cho các biểu đồ
                Gson gson = new Gson();
                dashboardData.setRevenueChartDataJson(gson.toJson(dashboardDAO.getRevenueChartData(userID, refreshData)));
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