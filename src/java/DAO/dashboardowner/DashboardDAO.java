package DAO.dashboardowner;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DashboardDAO extends MySQLConnect {

    private static final long CACHE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(5); // 5 phút
    private final ConcurrentHashMap<String, CachedData<?>> cache = new ConcurrentHashMap<>();

    public DashboardDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    private static class CachedData<T> {

        final T data;
        final long timestamp;

        CachedData(T data) {
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRATION_TIME;
        }
    }

    @FunctionalInterface
    private interface SqlFunction<T, R> {

        R apply(T t) throws SQLException;
    }

    private <T> T getCachedData(String key, SqlFunction<Integer, T> dbFetcher, int userID, boolean bypassCache) throws SQLException {
        if (bypassCache) {
            return dbFetcher.apply(userID);
        }
        String cacheKey = key + "_" + userID;
        CachedData<T> cachedData = (CachedData<T>) cache.get(cacheKey);
        if (cachedData != null && !cachedData.isExpired()) {
            return cachedData.data;
        }
        T freshData = dbFetcher.apply(userID);
        cache.put(cacheKey, new CachedData<>(freshData));
        return freshData;
    }

    public void clearCache() {
        cache.clear();
    }

    public void clearCacheForKey(String key, int userID) {
        String cacheKey = key + "_" + userID;
        cache.remove(cacheKey);
    }

    public int getTotalMovies(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("totalMovies", this::fetchTotalMovies, userID, bypassCache);
    }

    private int fetchTotalMovies(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Movie m JOIN Cinema c ON m.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int getTotalCinemas(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("totalCinemas", this::fetchTotalCinemas, userID, bypassCache);
    }

    private int fetchTotalCinemas(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Cinema c "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public double getTotalRevenue(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("totalRevenue", this::fetchTotalRevenue, userID, bypassCache);
    }

    private double fetchTotalRevenue(int userID) throws SQLException {
        String sql = "SELECT SUM(ms.Price * (1 - IFNULL(ms.Discount, 0))) AS TotalRevenue "
                + "FROM `Order` o "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "JOIN Ticket t ON o.OrderID = t.OrderID "
                + "WHERE cc.UserID = ? AND t.Status = 'Success'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("TotalRevenue");
                }
            }
        }
        return 0;
    }

    public List<Map<String, Object>> getRevenueByMonth(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("revenueByMonth", this::fetchRevenueByMonth, userID, bypassCache);
    }

    private List<Map<String, Object>> fetchRevenueByMonth(int userID) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT MONTH(ms.StartTime) as month, YEAR(ms.StartTime) as year, SUM(t.Price) as revenue "
                + "FROM Ticket t "
                + "JOIN MovieSlot ms ON t.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? "
                + "GROUP BY YEAR(ms.StartTime), MONTH(ms.StartTime) "
                + "ORDER BY YEAR(ms.StartTime), MONTH(ms.StartTime)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> entry = new HashMap<>();
                    entry.put("month", rs.getInt("month"));
                    entry.put("year", rs.getInt("year"));
                    entry.put("revenue", rs.getDouble("revenue"));
                    result.add(entry);
                }
            }
        }
        return result;
    }

    public int getTotalTicketsSold(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("totalTicketsSold", this::fetchTotalTicketsSold, userID, bypassCache);
    }

    private int fetchTotalTicketsSold(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) AS TotalTickets "
                + "FROM Ticket t "
                + "JOIN `Order` o ON t.OrderID = o.OrderID "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND t.Status = 'Success'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("TotalTickets");
                }
            }
        }
        return 0;
    }

    public int getTotalMovieSlots(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("totalMovieSlots", this::fetchTotalMovieSlots, userID, bypassCache);
    }

    private int fetchTotalMovieSlots(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public double getAverageSeatOccupancy(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("averageSeatOccupancy", this::fetchAverageSeatOccupancy, userID, bypassCache);
    }

    private double fetchAverageSeatOccupancy(int userID) throws SQLException {
        String sql = "SELECT AVG(occupancy_rate) FROM ("
                + "SELECT COUNT(t.TicketID) * 100.0 / r.Capacity as occupancy_rate "
                + "FROM MovieSlot ms "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "LEFT JOIN `Order` o ON ms.MovieSlotID = o.MovieSlotID "
                + "LEFT JOIN Ticket t ON o.OrderID = t.OrderID "
                + "WHERE cc.UserID = ? "
                + "GROUP BY ms.MovieSlotID) as occupancy_rates";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        }
        return 0;
    }

    public List<Map<String, Object>> getTopMovies(int userID, int limit, boolean bypassCache) throws SQLException {
        return getCachedData("topMovies_" + limit, id -> fetchTopMovies(id, limit), userID, bypassCache);
    }

    private List<Map<String, Object>> fetchTopMovies(int userID, int limit) throws SQLException {
        List<Map<String, Object>> topMovies = new ArrayList<>();
        String sql = "SELECT m.Title, SUM(ms.Price) as Revenue "
                + "FROM Movie m "
                + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
                + "JOIN `Order` o ON ms.MovieSlotID = o.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND o.Status = 'Completed' "
                + "GROUP BY m.MovieID "
                + "ORDER BY Revenue DESC "
                + "LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> movie = new HashMap<>();
                    movie.put("title", rs.getString("Title"));
                    movie.put("revenue", rs.getDouble("Revenue"));
                    topMovies.add(movie);
                }
            }
        }
        return topMovies;
    }

    public int getCurrentMoviesCount(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("currentMoviesCount", this::fetchCurrentMoviesCount, userID, bypassCache);
    }

    private int fetchCurrentMoviesCount(int userID) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT m.MovieID) "
                + "FROM Movie m "
                + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND ms.StartTime <= CURDATE() AND ms.EndTime >= CURDATE()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int getUpcomingMoviesCount(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("upcomingMoviesCount", this::fetchUpcomingMoviesCount, userID, bypassCache);
    }

    private int fetchUpcomingMoviesCount(int userID) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT m.MovieID) "
                + "FROM Movie m "
                + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND ms.StartTime > CURDATE()";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public Map<String, Object> getTopCinema(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("topCinema", this::fetchTopCinema, userID, bypassCache);
    }

    private Map<String, Object> fetchTopCinema(int userID) throws SQLException {
        Map<String, Object> topCinema = new HashMap<>();
        String sql = "SELECT c.Name, SUM(ms.Price) as Revenue "
                + "FROM Cinema c "
                + "JOIN Room r ON c.CinemaID = r.CinemaID "
                + "JOIN MovieSlot ms ON r.RoomID = ms.RoomID "
                + "JOIN `Order` o ON ms.MovieSlotID = o.MovieSlotID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND o.Status = 'Completed' "
                + "GROUP BY c.CinemaID "
                + "ORDER BY Revenue DESC "
                + "LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    topCinema.put("name", rs.getString("Name"));
                    topCinema.put("revenue", rs.getDouble("Revenue"));
                }
            }
        }
        return topCinema;
    }

    public int getNewCustomersCount(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("newCustomersCount", this::fetchNewCustomersCount, userID, bypassCache);
    }

    private int fetchNewCustomersCount(int userID) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT u.UserID) "
                + "FROM User u "
                + "JOIN `Order` o ON u.UserID = o.UserID "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND u.Birthday >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int getTotalMembers(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("totalMembers", this::fetchTotalMembers, userID, bypassCache);
    }

    private int fetchTotalMembers(int userID) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT u.UserID) "
                + "FROM User u "
                + "JOIN `Order` o ON u.UserID = o.UserID "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<Map<String, Object>> getTopCustomers(int userID, int limit, boolean bypassCache) throws SQLException {
        return getCachedData("topCustomers_" + limit, id -> fetchTopCustomers(id, limit), userID, bypassCache);
    }

    private List<Map<String, Object>> fetchTopCustomers(int userID, int limit) throws SQLException {
        List<Map<String, Object>> topCustomers = new ArrayList<>();
        String sql = "SELECT u.Fullname, SUM(ms.Price) as TotalSpent "
                + "FROM User u "
                + "JOIN `Order` o ON u.UserID = o.UserID "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND o.Status = 'Completed' "
                + "GROUP BY u.UserID "
                + "ORDER BY TotalSpent DESC "
                + "LIMIT ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> customer = new HashMap<>();
                    customer.put("name", rs.getString("Fullname"));
                    customer.put("totalSpent", rs.getDouble("TotalSpent"));
                    topCustomers.add(customer);
                }
            }
        }
        return topCustomers;
    }

    public List<Map<String, Object>> getRevenueChartData(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("revenueChartData", this::fetchRevenueChartData, userID, bypassCache);
    }

    private List<Map<String, Object>> fetchRevenueChartData(int userID) throws SQLException {
        List<Map<String, Object>> chartData = new ArrayList<>();
        String sql = "SELECT DATE(o.TimeCreated) as Date, SUM(ms.Price) as Revenue "
                + "FROM `Order` o "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND o.Status = 'Completed' "
                + "GROUP BY Date "
                + "ORDER BY Date";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> dataPoint = new HashMap<>();
                    dataPoint.put("date", rs.getString("Date"));
                    dataPoint.put("revenue", rs.getDouble("Revenue"));
                    chartData.add(dataPoint);
                }
            }
        }
        return chartData;
    }

    public List<Map<String, Object>> getCinemaOccupancyRates(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("cinemaOccupancyRates", this::fetchCinemaOccupancyRates, userID, bypassCache);
    }

    private List<Map<String, Object>> fetchCinemaOccupancyRates(int userID) throws SQLException {
        List<Map<String, Object>> occupancyRates = new ArrayList<>();
        String sql = "SELECT c.Name, AVG(occupancy_rate) as AverageOccupancy "
                + "FROM ("
                + "  SELECT ms.MovieSlotID, r.CinemaID, COUNT(t.TicketID) * 100.0 / r.Capacity as occupancy_rate "
                + "  FROM MovieSlot ms "
                + "  JOIN Room r ON ms.RoomID = r.RoomID "
                + "  LEFT JOIN `Order` o ON ms.MovieSlotID = o.MovieSlotID "
                + "  LEFT JOIN Ticket t ON o.OrderID = t.OrderID "
                + "  GROUP BY ms.MovieSlotID"
                + ") as slot_occupancy "
                + "JOIN Cinema c ON slot_occupancy.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? "
                + "GROUP BY c.CinemaID";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> cinemaOccupancy = new HashMap<>();
                    cinemaOccupancy.put("name", rs.getString("Name"));
                    cinemaOccupancy.put("occupancyRate", rs.getDouble("AverageOccupancy"));
                    occupancyRates.add(cinemaOccupancy);
                }
            }
        }
        return occupancyRates;
    }

    public Map<String, Double> getRevenueStats(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("revenueStats", this::fetchRevenueStats, userID, bypassCache);
    }

    private Map<String, Double> fetchRevenueStats(int userID) throws SQLException {
        Map<String, Double> revenueStats = new LinkedHashMap<>();
        String sql = "SELECT c.Name AS CinemaName, "
                + "COALESCE(SUM(CASE WHEN subquery.Status = 'Success' THEN subquery.Price * (1 - IFNULL(subquery.Discount, 0)) ELSE 0 END), 0) AS Revenue "
                + "FROM CinemaChain cc "
                + "JOIN Cinema c ON cc.CinemaChainID = c.CinemaChainID "
                + "LEFT JOIN ("
                + "    SELECT r.CinemaID, ms.Price, ms.Discount, t.Status "
                + "    FROM Room r "
                + "    JOIN MovieSlot ms ON r.RoomID = ms.RoomID "
                + "    JOIN `Order` o ON ms.MovieSlotID = o.MovieSlotID "
                + "    JOIN Ticket t ON o.OrderID = t.OrderID "
                + ") AS subquery ON c.CinemaID = subquery.CinemaID "
                + "WHERE cc.UserID = ? "
                + "GROUP BY c.CinemaID, c.Name "
                + "ORDER BY Revenue DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    revenueStats.put(rs.getString("CinemaName"), rs.getDouble("Revenue"));
                }
            }
        }
        return revenueStats;
    }

    // số lượng vé theo rạp
    public Map<String, Map<String, Integer>> getTicketStats(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("ticketStats", this::fetchTicketStats, userID, bypassCache);
    }

    private Map<String, Map<String, Integer>> fetchTicketStats(int userID) throws SQLException {
        Map<String, Map<String, Integer>> ticketStats = new LinkedHashMap<>();
        String sql = "SELECT CinemaName, Status, TotalCount, OrderPriority FROM ("
                + "    SELECT cc.Name as CinemaName, 'TotalChain' as Type, t.Status, COUNT(*) AS TotalCount, 0 as OrderPriority "
                + "    FROM Ticket t "
                + "    JOIN `Order` o ON t.OrderID = o.OrderID "
                + "    JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "    JOIN Room r ON ms.RoomID = r.RoomID "
                + "    JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "    JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "    WHERE cc.UserID = ? "
                + "    GROUP BY cc.Name, t.Status "
                + "    UNION ALL "
                + "    SELECT c.Name as CinemaName, 'Cinema' as Type, t.Status, COUNT(*) AS TotalCount, 1 as OrderPriority "
                + "    FROM Ticket t "
                + "    JOIN `Order` o ON t.OrderID = o.OrderID "
                + "    JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "    JOIN Room r ON ms.RoomID = r.RoomID "
                + "    JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "    JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "    WHERE cc.UserID = ? "
                + "    GROUP BY c.Name, t.Status"
                + ") as SubQuery "
                + "ORDER BY OrderPriority, CinemaName";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String cinemaName = rs.getString("CinemaName");
                    String status = rs.getString("Status");
                    int count = rs.getInt("TotalCount");
                    ticketStats.computeIfAbsent(cinemaName, k -> new HashMap<>()).put(status, count);
                }
            }
        }
        return ticketStats;
    }

    public List<Map<String, Object>> getMovieRevenueStats(int userID, boolean bypassCache) throws SQLException {
        return getCachedData("movieRevenueStats", this::fetchMovieRevenueStats, userID, bypassCache);
    }

    private List<Map<String, Object>> fetchMovieRevenueStats(int userID) throws SQLException {
        List<Map<String, Object>> movieStats = new ArrayList<>();
        String sql = "SELECT m.MovieID, m.Title, "
                + "COUNT(t.TicketID) AS TotalTickets, "
                + "SUM(CASE WHEN t.Status = 'Success' THEN 1 ELSE 0 END) AS SuccessTickets, "
                + "SUM(CASE WHEN t.Status != 'Success' THEN 1 ELSE 0 END) AS FailedTickets, "
                + "SUM(CASE WHEN t.Status = 'Success' THEN ms.Price * (1 - IFNULL(ms.Discount, 0)) ELSE 0 END) AS TotalRevenue "
                + "FROM Movie m "
                + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "LEFT JOIN `Order` o ON ms.MovieSlotID = o.MovieSlotID "
                + "LEFT JOIN Ticket t ON o.OrderID = t.OrderID "
                + "WHERE cc.UserID = ? "
                + "GROUP BY m.MovieID, m.Title "
                + "HAVING TotalTickets > 0 OR TotalRevenue > 0 "
                + "ORDER BY TotalRevenue DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> stat = new HashMap<>();
                    stat.put("movieID", rs.getInt("MovieID"));
                    stat.put("title", rs.getString("Title"));
                    stat.put("totalTickets", rs.getInt("TotalTickets"));
                    stat.put("successTickets", rs.getInt("SuccessTickets"));
                    stat.put("failedTickets", rs.getInt("FailedTickets"));
                    stat.put("totalRevenue", rs.getDouble("TotalRevenue"));
                    movieStats.add(stat);
                }
            }
        }
        return movieStats;
    }

    public List<Map<String, Object>> getUpcomingMovieSchedule(int userID, int daysAhead, int limit, boolean bypassCache) throws SQLException {
        return getCachedData("upcomingMovieSchedule_" + daysAhead + "_" + limit,
                id -> fetchUpcomingMovieSchedule(id, daysAhead, limit), userID, bypassCache);
    }

    private List<Map<String, Object>> fetchUpcomingMovieSchedule(int userID, int daysAhead, int limit) throws SQLException {
        List<Map<String, Object>> upcomingSchedule = new ArrayList<>();
        String sql = "SELECT m.MovieID, m.Title, m.ImageURL, ms.MovieSlotID, ms.StartTime, ms.EndTime, "
                + "r.RoomName, c.Name as CinemaName "
                + "FROM Movie m "
                + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? "
                + "AND ms.StartTime BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY) "
                + "ORDER BY ms.StartTime ASC "
                + "LIMIT ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, daysAhead);
            stmt.setInt(3, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> movieSlot = new HashMap<>();
                    movieSlot.put("movieID", rs.getInt("MovieID"));
                    movieSlot.put("title", rs.getString("Title"));
                    movieSlot.put("imageURL", rs.getString("ImageURL"));
                    movieSlot.put("movieSlotID", rs.getInt("MovieSlotID"));
                    movieSlot.put("startTime", rs.getTimestamp("StartTime").toInstant().toString());
                    movieSlot.put("endTime", rs.getTimestamp("EndTime").toInstant().toString());
                    movieSlot.put("roomName", rs.getString("RoomName"));
                    movieSlot.put("cinemaName", rs.getString("CinemaName"));
                    upcomingSchedule.add(movieSlot);
                }
            }
        }
        return upcomingSchedule;
    }
}
