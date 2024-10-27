package DAO.admindashboard;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticsDAO extends MySQLConnect {

    public StatisticsDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public Map<String, Double> getRevenueByCinemaChain() {
        Map<String, Double> revenueByCinemaChain = new LinkedHashMap<>();
        String sql = "SELECT cc.Name, SUM(ms.Price * t.TicketCount) AS revenue " +
                     "FROM cinemachain cc " +
                     "JOIN cinema c ON cc.CinemaChainID = c.CinemaChainID " +
                     "JOIN room r ON c.CinemaID = r.CinemaID " +
                     "JOIN movieslot ms ON r.RoomID = ms.RoomID " +
                     "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                     "JOIN (SELECT OrderID, COUNT(*) AS TicketCount FROM ticket GROUP BY OrderID) t ON o.OrderID = t.OrderID " +
                     "WHERE o.Status = 'Success' " +
                     "GROUP BY cc.CinemaChainID ORDER BY revenue DESC";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                revenueByCinemaChain.put(rs.getString("Name"), rs.getDouble("revenue"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueByCinemaChain;
    }

    public Map<String, Integer> getTicketsSoldByCinemaChain() {
        Map<String, Integer> ticketsSoldByCinemaChain = new LinkedHashMap<>();
        String sql = "SELECT cc.Name, COUNT(t.TicketID) AS tickets_sold " +
                     "FROM cinemachain cc " +
                     "JOIN cinema c ON cc.CinemaChainID = c.CinemaChainID " +
                     "JOIN room r ON c.CinemaID = r.CinemaID " +
                     "JOIN movieslot ms ON r.RoomID = ms.RoomID " +
                     "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                     "JOIN ticket t ON o.OrderID = t.OrderID " +
                     "WHERE o.Status = 'Success' " +
                     "GROUP BY cc.CinemaChainID ORDER BY tickets_sold DESC";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ticketsSoldByCinemaChain.put(rs.getString("Name"), rs.getInt("tickets_sold"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketsSoldByCinemaChain;
    }

   public Map<String, Integer> getTicketsSoldByMovie() {
    Map<String, Integer> ticketsSoldByMovie = new LinkedHashMap<>();
    String sql = "SELECT m.Title, COUNT(t.TicketID) AS tickets_sold " +
                 "FROM movie m " +
                 "JOIN movieslot ms ON m.MovieID = ms.MovieID " +
                 "JOIN room r ON ms.RoomID = r.RoomID " +
                 "JOIN cinema c ON r.CinemaID = c.CinemaID " +
                 "JOIN cinemachain cc ON c.CinemaChainID = cc.CinemaChainID " +
                 "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                 "JOIN ticket t ON o.OrderID = t.OrderID " +
                 "WHERE o.Status = 'Success' " +
                 "GROUP BY m.MovieID " +
                 "ORDER BY tickets_sold DESC " +
                 "LIMIT 10";
    try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            ticketsSoldByMovie.put(rs.getString("Title"), rs.getInt("tickets_sold"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ticketsSoldByMovie;
}
    public double getTotalRevenue() {
        double totalRevenue = 0;
        String sql = "SELECT SUM(ms.Price * t.TicketCount) AS total_revenue " +
                     "FROM cinemachain cc " +
                     "JOIN cinema c ON cc.CinemaChainID = c.CinemaChainID " +
                     "JOIN room r ON c.CinemaID = r.CinemaID " +
                     "JOIN movieslot ms ON r.RoomID = ms.RoomID " +
                     "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                     "JOIN (SELECT OrderID, COUNT(*) AS TicketCount FROM ticket GROUP BY OrderID) t ON o.OrderID = t.OrderID " +
                     "WHERE o.Status = 'Success'";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }

    
    public Map<String, Map<String, Object>> getRevenueAndPercentageByCinemaChain() {
        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        double totalRevenue = getTotalRevenue();
        
        String sql = "SELECT cc.Name, SUM(ms.Price * t.TicketCount) AS revenue " +
                     "FROM cinemachain cc " +
                     "JOIN cinema c ON cc.CinemaChainID = c.CinemaChainID " +
                     "JOIN room r ON c.CinemaID = r.CinemaID " +
                     "JOIN movieslot ms ON r.RoomID = ms.RoomID " +
                     "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                     "JOIN (SELECT OrderID, COUNT(*) AS TicketCount FROM ticket GROUP BY OrderID) t ON o.OrderID = t.OrderID " +
                     "WHERE o.Status = 'Success' " +
                     "GROUP BY cc.CinemaChainID ORDER BY revenue DESC";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("Name");
                double revenue = rs.getDouble("revenue");
                double percentage = (revenue / totalRevenue) * 100;
                
                Map<String, Object> chainData = new LinkedHashMap<>();
                chainData.put("revenue", revenue);
                chainData.put("percentage", percentage);
                
                result.put(name, chainData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Map<String, Map<String, Double>> getRevenueByMovieForAllCinemaChains() {
    Map<String, Map<String, Double>> revenueByMovieAndChain = new LinkedHashMap<>();
    String sql = "SELECT cc.Name AS ChainName, m.Title, SUM(ms.Price * t.TicketCount) AS revenue " +
                 "FROM movie m " +
                 "JOIN movieslot ms ON m.MovieID = ms.MovieID " +
                 "JOIN room r ON ms.RoomID = r.RoomID " +
                 "JOIN cinema c ON r.CinemaID = c.CinemaID " +
                 "JOIN cinemachain cc ON c.CinemaChainID = cc.CinemaChainID " +
                 "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                 "JOIN (SELECT OrderID, COUNT(*) AS TicketCount FROM ticket GROUP BY OrderID) t ON o.OrderID = t.OrderID " +
                 "WHERE o.Status = 'Success' " +
                 "GROUP BY cc.CinemaChainID, m.MovieID " +
                 "ORDER BY cc.Name, revenue DESC";

    try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            String chainName = rs.getString("ChainName");
            String movieTitle = rs.getString("Title");
            double revenue = rs.getDouble("revenue");

            revenueByMovieAndChain
                .computeIfAbsent(chainName, k -> new LinkedHashMap<>())
                .put(movieTitle, revenue);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return revenueByMovieAndChain;
}
    public Map<String, Object> getTotalRevenueAndTickets() {
        Map<String, Object> result = new LinkedHashMap<>();
        String sql = "SELECT SUM(ms.Price * t.TicketCount) AS total_revenue, " +
                     "SUM(t.TicketCount) AS total_tickets " +
                     "FROM cinemachain cc " +
                     "JOIN cinema c ON cc.CinemaChainID = c.CinemaChainID " +
                     "JOIN room r ON c.CinemaID = r.CinemaID " +
                     "JOIN movieslot ms ON r.RoomID = ms.RoomID " +
                     "JOIN `order` o ON ms.MovieSlotID = o.MovieSlotID " +
                     "JOIN (SELECT OrderID, COUNT(*) AS TicketCount FROM ticket GROUP BY OrderID) t ON o.OrderID = t.OrderID " +
                     "WHERE o.Status = 'Success'";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                result.put("totalRevenue", rs.getDouble("total_revenue"));
                result.put("totalTickets", rs.getInt("total_tickets"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}