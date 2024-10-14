package DAO.dashboard;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDAO extends MySQLConnect {

    public DashboardDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public int getTotalMovies(int userID) throws SQLException {
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

    public int getTotalCinemas(int userID) throws SQLException {
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

   public double getTotalRevenue(int userID) throws SQLException {
    String sql = "SELECT SUM(ms.Price) FROM `Order` o " +
                 "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID " +
                 "JOIN Room r ON ms.RoomID = r.RoomID " +
                 "JOIN Cinema c ON r.CinemaID = c.CinemaID " +
                 "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID " +
                 "WHERE cc.UserID = ? AND o.Status = 'Completed'";
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

    public List<Map<String, Object>> getRevenueByMonth(int userID) throws SQLException {
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

    public int getTotalTicketsSold(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Ticket t "
                + "JOIN `Order` o ON t.OrderID = o.OrderID "
                + "JOIN MovieSlot ms ON o.MovieSlotID = ms.MovieSlotID "
                + "JOIN Room r ON ms.RoomID = r.RoomID "
                + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
                + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
                + "WHERE cc.UserID = ? AND o.Status = 'Completed'";
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

    public int getTotalMovieSlots(int userID) throws SQLException {
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

    public double getAverageSeatOccupancy(int userID) throws SQLException {
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

    public List<Map<String, Object>> getTopMovies(int userID, int limit) throws SQLException {
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

    public int getCurrentMoviesCount(int userID) throws SQLException {
    String sql = "SELECT COUNT(DISTINCT m.MovieID) "
            + "FROM Movie m "
            + "JOIN MovieSlot ms ON m.MovieID = ms.MovieID "
            + "JOIN Room r ON ms.RoomID = r.RoomID "
            + "JOIN Cinema c ON r.CinemaID = c.CinemaID "
            + "JOIN CinemaChain cc ON c.CinemaChainID = cc.CinemaChainID "
            + "WHERE cc.UserID = ? AND ms.StartTime <= CURDATE() AND ms.EndTime >= CURDATE()";
    System.out.println("Executing SQL: " + sql);
    System.out.println("UserID: " + userID);
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, userID);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Current movies count: " + count);
                return count;
            }
        }
    }
    System.out.println("No results found for current movies count");
    return 0;
}

    public int getUpcomingMoviesCount(int userID) throws SQLException {
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

    public Map<String, Object> getTopCinema(int userID) throws SQLException {
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

    public int getNewCustomersCount(int userID) throws SQLException {
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

    public int getTotalMembers(int userID) throws SQLException {
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

    public List<Map<String, Object>> getTopCustomers(int userID, int limit) throws SQLException {
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

    public List<Map<String, Object>> getRevenueChartData(int userID) throws SQLException {
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

    public List<Map<String, Object>> getCinemaOccupancyRates(int userID) throws SQLException {
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
}
