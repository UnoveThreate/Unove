package model.dashboardowner;

import java.util.List;
import java.util.Map;

public class DashboardData {
    private double totalRevenue;
    private int totalTicketsSold;
    private int totalMovieSlots;
    private double averageSeatOccupancy;
    private List<Map<String, Object>> topMovies;
    private int currentMoviesCount;
    private int upcomingMoviesCount;
    private int totalCinemas;
    private Map<String, Object> topCinema;
    private List<Map<String, Object>> cinemaOccupancyRates;
    private int newCustomersCount;
    private int totalMembers;
    private List<Map<String, Object>> topCustomers;
    private List<Map<String, Object>> revenueChartData;
    private String revenueChartDataJson;
    private String cinemaComparisonDataJson;
    private Map<String, Double> revenueStats;
    private Map<String, Map<String, Integer>> ticketStats;
    private List<Map<String, Object>> movieRevenueStats;
    private String revenueStatsJson;
    private String ticketStatsJson;
    private String movieRevenueStatsJson;
    private List<Map<String, Object>> upcomingMovieSchedule;
    private String upcomingMovieScheduleJson;

   

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(int totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public int getTotalMovieSlots() {
        return totalMovieSlots;
    }

    public void setTotalMovieSlots(int totalMovieSlots) {
        this.totalMovieSlots = totalMovieSlots;
    }

    public double getAverageSeatOccupancy() {
        return averageSeatOccupancy;
    }

    public void setAverageSeatOccupancy(double averageSeatOccupancy) {
        this.averageSeatOccupancy = averageSeatOccupancy;
    }

    public List<Map<String, Object>> getTopMovies() {
        return topMovies;
    }

    public void setTopMovies(List<Map<String, Object>> topMovies) {
        this.topMovies = topMovies;
    }

    public int getCurrentMoviesCount() {
        return currentMoviesCount;
    }

    public void setCurrentMoviesCount(int currentMoviesCount) {
        this.currentMoviesCount = currentMoviesCount;
    }

    public int getUpcomingMoviesCount() {
        return upcomingMoviesCount;
    }

    public void setUpcomingMoviesCount(int upcomingMoviesCount) {
        this.upcomingMoviesCount = upcomingMoviesCount;
    }

    public int getTotalCinemas() {
        return totalCinemas;
    }

    public void setTotalCinemas(int totalCinemas) {
        this.totalCinemas = totalCinemas;
    }

    public Map<String, Object> getTopCinema() {
        return topCinema;
    }

    public void setTopCinema(Map<String, Object> topCinema) {
        this.topCinema = topCinema;
    }

    public List<Map<String, Object>> getCinemaOccupancyRates() {
        return cinemaOccupancyRates;
    }

    public void setCinemaOccupancyRates(List<Map<String, Object>> cinemaOccupancyRates) {
        this.cinemaOccupancyRates = cinemaOccupancyRates;
    }

    public int getNewCustomersCount() {
        return newCustomersCount;
    }

    public void setNewCustomersCount(int newCustomersCount) {
        this.newCustomersCount = newCustomersCount;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public List<Map<String, Object>> getTopCustomers() {
        return topCustomers;
    }

    public void setTopCustomers(List<Map<String, Object>> topCustomers) {
        this.topCustomers = topCustomers;
    }

    public List<Map<String, Object>> getRevenueChartData() {
        return revenueChartData;
    }

    public void setRevenueChartData(List<Map<String, Object>> revenueChartData) {
        this.revenueChartData = revenueChartData;
    }

    public String getRevenueChartDataJson() {
        return revenueChartDataJson;
    }

    public void setRevenueChartDataJson(String revenueChartDataJson) {
        this.revenueChartDataJson = revenueChartDataJson;
    }

    public String getCinemaComparisonDataJson() {
        return cinemaComparisonDataJson;
    }

    public void setCinemaComparisonDataJson(String cinemaComparisonDataJson) {
        this.cinemaComparisonDataJson = cinemaComparisonDataJson;
    }

    public Map<String, Double> getRevenueStats() {
        return revenueStats;
    }

    public void setRevenueStats(Map<String, Double> revenueStats) {
        this.revenueStats = revenueStats;
    }

    public Map<String, Map<String, Integer>> getTicketStats() {
        return ticketStats;
    }

    public void setTicketStats(Map<String, Map<String, Integer>> ticketStats) {
        this.ticketStats = ticketStats;
    }

    public List<Map<String, Object>> getMovieRevenueStats() {
        return movieRevenueStats;
    }

    public void setMovieRevenueStats(List<Map<String, Object>> movieRevenueStats) {
        this.movieRevenueStats = movieRevenueStats;
    }

    public String getRevenueStatsJson() {
        return revenueStatsJson;
    }

    public void setRevenueStatsJson(String revenueStatsJson) {
        this.revenueStatsJson = revenueStatsJson;
    }

    public String getTicketStatsJson() {
        return ticketStatsJson;
    }

    public void setTicketStatsJson(String ticketStatsJson) {
        this.ticketStatsJson = ticketStatsJson;
    }

    public String getMovieRevenueStatsJson() {
        return movieRevenueStatsJson;
    }

    public void setMovieRevenueStatsJson(String movieRevenueStatsJson) {
        this.movieRevenueStatsJson = movieRevenueStatsJson;
    }
    public List<Map<String, Object>> getUpcomingMovieSchedule() {
        return upcomingMovieSchedule;
    }

    public void setUpcomingMovieSchedule(List<Map<String, Object>> upcomingMovieSchedule) {
        this.upcomingMovieSchedule = upcomingMovieSchedule;
    }

    public String getUpcomingMovieScheduleJson() {
        return upcomingMovieScheduleJson;
    }

    public void setUpcomingMovieScheduleJson(String upcomingMovieScheduleJson) {
        this.upcomingMovieScheduleJson = upcomingMovieScheduleJson;
    }
}