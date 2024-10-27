package controller.admindashboard;

import DAO.admindashboard.UserManagementDAO;
import DAO.admindashboard.CinemaChainManagementDAO;
import DAO.admindashboard.CinemaManagementDAO;
import DAO.admindashboard.MovieManagementDAO;
import DAO.admindashboard.StatisticsDAO;
import com.google.gson.Gson;
import model.CinemaChain;
import model.Cinema;
import model.moviemanagementadmin.Movie;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import util.Role;
import util.RouterURL;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    private CinemaChainManagementDAO cinemaChainManagementDAO;
    private CinemaManagementDAO cinemaManagementDAO;
    private MovieManagementDAO movieManagementDAO;
    private StatisticsDAO statisticsDAO;
    private UserManagementDAO userManagementDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            cinemaChainManagementDAO = new CinemaChainManagementDAO(getServletContext());
            cinemaManagementDAO = new CinemaManagementDAO(getServletContext());
            movieManagementDAO = new MovieManagementDAO(getServletContext());
            statisticsDAO = new StatisticsDAO(getServletContext());
            userManagementDAO = new UserManagementDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Error initializing DAOs", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        // check role admin
        if (!isAdmin(userID, role, response)) {
            return;
        }

        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/dashboard":
                showDashboard(request, response, session);
                break;
            case "/cinemaChains":
                manageCinemaChains(request, response, session);
                break;
            case "/cinemas":
                manageCinemas(request, response, session);
                break;
            case "/movies":
                manageMovies(request, response, session);
                break;
            case "/statistics":
                showStatistics(request, response, session);
                break;
            case "/getCinemasForMovie":
                getCinemasForMovie(request, response);
                break;
                case "/users":
                manageUsers(request, response, session);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (!isAdmin(userID, role, response)) {
            return;
        }
        String action = request.getPathInfo();
        if (action == null) {
            action = "/";
        }

        switch (action) {
            case "/cinemaChains":
                processCinemaChainAction(request, response, session);
                break;
            case "/cinemas":
                processCinemaAction(request, response, session);
                break;

            case "/movies":
                processMovieAction(request, response, session);
                break;
            case "/users":
                processUserAction(request, response, session);
            default:
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
        }
    }

    private boolean isAdmin(Integer userID, String role, HttpServletResponse response) throws IOException {
        if (userID == null || !Role.isRoleValid(role, Role.ADMIN)) {
            response.sendRedirect(RouterURL.LOGIN);
            return false;
        }
        return true;
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {

        List<CinemaChain> cinemaChains = cinemaChainManagementDAO.getAllCinemaChains();
        int totalCinemaChains = cinemaChains.size();
        int totalCinemas = cinemaManagementDAO.getTotalCinemas();
        int totalMovies = movieManagementDAO.getTotalMovies();
        Map<String, Integer> ticketsSoldByCinemaChain = statisticsDAO.getTicketsSoldByCinemaChain();
        Map<String, Double> revenueByCinemaChain = statisticsDAO.getRevenueByCinemaChain();

        Gson gson = new Gson();
        request.setAttribute("revenueByCinemaChainJson", gson.toJson(revenueByCinemaChain));
        request.setAttribute("ticketsSoldByCinemaChainJson", gson.toJson(ticketsSoldByCinemaChain));

        session.setAttribute("totalCinemaChains", totalCinemaChains);
        session.setAttribute("totalCinemas", totalCinemas);
        session.setAttribute("totalMovies", totalMovies);
        session.setAttribute("revenueByCinemaChain", revenueByCinemaChain);
        session.setAttribute("ticketsSoldByCinemaChain", ticketsSoldByCinemaChain);

        request.getRequestDispatcher("/page/admin/dashboard.jsp").forward(request, response);
    }

    private void manageCinemaChains(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        List<CinemaChain> cinemaChains = cinemaChainManagementDAO.getAllCinemaChains();
        Map<Integer, Integer> cinemaCounts = new HashMap<>();

        for (CinemaChain chain : cinemaChains) {
            int count = cinemaChainManagementDAO.getCinemaCountForChain(chain.getCinemaChainID());
            cinemaCounts.put(chain.getCinemaChainID(), count);
        }

        request.setAttribute("cinemaChains", cinemaChains);
        request.setAttribute("cinemaCounts", cinemaCounts);
        request.getRequestDispatcher("/page/admin/cinemaChains.jsp").forward(request, response);
    }

    private void manageCinemas(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        List<CinemaChain> cinemaChains = cinemaChainManagementDAO.getAllCinemaChains();
        List<Movie> movies = movieManagementDAO.getAllMovies();
        request.setAttribute("movies", movies);

        Map<Integer, List<Cinema>> cinemasByChain = new HashMap<>();

        for (CinemaChain chain : cinemaChains) {
            List<Cinema> cinemasInChain = cinemaManagementDAO.getCinemasByCinemaChainID(chain.getCinemaChainID());
            cinemasByChain.put(chain.getCinemaChainID(), cinemasInChain);
        }

        session.setAttribute("cinemaChains", cinemaChains);
        session.setAttribute("cinemasByChain", cinemasByChain);
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("/page/admin/cinemas.jsp").forward(request, response);
    }

    private void manageMovies(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
    try {
        // handle phân trang
        int page = 1;
        int recordsPerPage = 5; // mỗi page hiện tối đa 5 phim
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        // handle sắp xếp
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        if (sortBy == null) {
            sortBy = "Title";
        }
        if (sortOrder == null) {
            sortOrder = "ASC";
        }

        String keyword = request.getParameter("keyword");

        int totalMovies;
        List<Movie> movies;
        if (keyword != null && !keyword.trim().isEmpty()) {
            totalMovies = movieManagementDAO.getTotalSearchResults(keyword);
            movies = movieManagementDAO.searchMovies(keyword, (page - 1) * recordsPerPage, recordsPerPage, sortBy, sortOrder);
        } else {
            totalMovies = movieManagementDAO.getTotalMovies();
            movies = movieManagementDAO.getMovies((page - 1) * recordsPerPage, recordsPerPage, sortBy, sortOrder);
        }

        // Lấy thông tin về chuỗi rạp cho mỗi phim
        Map<Integer, List<CinemaChain>> movieCinemaChains = new HashMap<>();
        for (Movie movie : movies) {
            List<CinemaChain> chains = cinemaChainManagementDAO.getCinemaChainsForMovie(movie.getMovieID());
            movieCinemaChains.put(movie.getMovieID(), chains);
        }

        List<Cinema> cinemas = cinemaManagementDAO.getAllCinemas();
        request.setAttribute("cinemas", cinemas);

        request.setAttribute("movies", movies);
        request.setAttribute("movieCinemaChains", movieCinemaChains);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalMovies / recordsPerPage));
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/page/admin/movies.jsp").forward(request, response);
    } catch (Exception e) {
        request.setAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách phim: " + e.getMessage());
        request.getRequestDispatcher("/page/admin/error.jsp").forward(request, response);
    }
}

    private void showStatistics(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        Map<String, Double> revenueByCinemaChain = statisticsDAO.getRevenueByCinemaChain();
        Map<String, Integer> ticketsSoldByCinemaChain = statisticsDAO.getTicketsSoldByCinemaChain();
        Map<String, Integer> ticketsSoldByMovie = statisticsDAO.getTicketsSoldByMovie();
        Map<String, Map<String, Object>> revenueAndPercentageByCinemaChain = statisticsDAO.getRevenueAndPercentageByCinemaChain();
        double totalRevenue = statisticsDAO.getTotalRevenue();

        Map<String, Map<String, Double>> revenueByMovieAndChain = statisticsDAO.getRevenueByMovieForAllCinemaChains();

        session.setAttribute("revenueByCinemaChain", revenueByCinemaChain);
        session.setAttribute("ticketsSoldByCinemaChain", ticketsSoldByCinemaChain);
        session.setAttribute("ticketsSoldByMovie", ticketsSoldByMovie);
        session.setAttribute("revenueAndPercentageByCinemaChain", revenueAndPercentageByCinemaChain);
        session.setAttribute("totalRevenue", totalRevenue);

        session.setAttribute("revenueByMovieAndChain", revenueByMovieAndChain);
        // chuyển dữ liệu sang json để convert sang chart
        Gson gson = new Gson();
        request.setAttribute("revenueByCinemaChainJson", gson.toJson(revenueByCinemaChain));
        request.setAttribute("ticketsSoldByCinemaChainJson", gson.toJson(ticketsSoldByCinemaChain));
        request.setAttribute("ticketsSoldByMovieJson", gson.toJson(ticketsSoldByMovie));
        request.setAttribute("revenueAndPercentageByCinemaChainJson", gson.toJson(revenueAndPercentageByCinemaChain));
        request.setAttribute("totalRevenueJson", gson.toJson(totalRevenue));

        request.setAttribute("revenueByMovieAndChainJson", gson.toJson(revenueByMovieAndChain));

        request.getRequestDispatcher("/page/admin/statistics.jsp").forward(request, response);
    }

    private int getRandomUserID() {
        List<Integer> userIDs = cinemaChainManagementDAO.getAllUserIDs();
        if (userIDs.isEmpty()) {
            throw new IllegalStateException("Không có UserID nào trong hệ thống");
        }
        Random random = new Random();
        return userIDs.get(random.nextInt(userIDs.size()));
    }
private void manageUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        List<User> users = userManagementDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/page/admin/usersmanager.jsp").forward(request, response);
    }
private void processUserAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String action = request.getParameter("action");

            switch (action) {
                case "add":
                    String username = request.getParameter("username");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String role = request.getParameter("role");

                    if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty() 
                            || password == null || password.trim().isEmpty() || role == null || role.trim().isEmpty()) {
                        throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin");
                    }

                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setEmail(email);
                    newUser.setPassword(hashedPassword);
                    newUser.setRole(role);

                    int addedUserId = userManagementDAO.addUser(newUser);
                    if (addedUserId != -1) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Thêm người dùng thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể thêm người dùng");
                    }
                    break;

                case "update":
                    String userIdStr = request.getParameter("userId");
                    if (userIdStr == null || userIdStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID người dùng không hợp lệ");
                    }
                    int userId = Integer.parseInt(userIdStr);
                    User userToUpdate = userManagementDAO.getUserById(userId);
                    if (userToUpdate == null) {
                        throw new IllegalArgumentException("Không tìm thấy người dùng với ID: " + userId);
                    }

                    username = request.getParameter("username");
                    email = request.getParameter("email");
                    role = request.getParameter("role");

                    if (username != null && !username.trim().isEmpty()) {
                        userToUpdate.setUsername(username);
                    }
                    if (email != null && !email.trim().isEmpty()) {
                        userToUpdate.setEmail(email);
                    }
                    if (role != null && !role.trim().isEmpty()) {
                        userToUpdate.setRole(role);
                    }

                    boolean updateSuccess = userManagementDAO.updateUser(userToUpdate);
                    if (updateSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Cập nhật người dùng thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể cập nhật người dùng");
                    }
                    break;

                case "delete":
                    userIdStr = request.getParameter("userId");
                    if (userIdStr == null || userIdStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID người dùng không hợp lệ");
                    }
                    userId = Integer.parseInt(userIdStr);
                    boolean deleteSuccess = userManagementDAO.deleteUser(userId);
                    if (deleteSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Xóa người dùng thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể xóa người dùng");
                    }
                    break;
                case "ban":
                String banUserIdStr = request.getParameter("userId");
                if (banUserIdStr == null || banUserIdStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("ID người dùng không hợp lệ");
                }
                int banUserId = Integer.parseInt(banUserIdStr);
                boolean banSuccess = userManagementDAO.banUser(banUserId);
                if (banSuccess) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Khóa tài khoản người dùng thành công");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Không thể khóa tài khoản người dùng");
                }
                break;

            case "unban":
                String unbanUserIdStr = request.getParameter("userId");
                if (unbanUserIdStr == null || unbanUserIdStr.trim().isEmpty()) {
                    throw new IllegalArgumentException("ID người dùng không hợp lệ");
                }
                int unbanUserId = Integer.parseInt(unbanUserIdStr);
                boolean unbanSuccess = userManagementDAO.unbanUser(unbanUserId);
                if (unbanSuccess) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Mở khóa tài khoản người dùng thành công");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Không thể mở khóa tài khoản người dùng");
                }
                break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Hành động không hợp lệ");
                    break;
            }

            // Cập nhật danh sách người dùng mới vào session
            List<User> updatedUsers = userManagementDAO.getAllUsers();
            session.setAttribute("users", updatedUsers);

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
    private void processCinemaChainAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String action = request.getParameter("action");

            switch (action) {
                case "add":
                    String chainName = request.getParameter("chainName");
                    String avatarURL = request.getParameter("avatarURL");
                    String information = request.getParameter("information");

                    if (chainName == null || chainName.trim().isEmpty()) {
                        throw new IllegalArgumentException("Tên chuỗi rạp không được để trống");
                    }

                    // auto tạo tài khoản owner
                    String ownerUsername = generateUniqueUsername(chainName);
                    String tempPassword = generateRandomPassword();
                    String ownerEmail = generateDefaultEmail(chainName);

                    // mã hóa mật khẩu
                    String hashedPassword = BCrypt.hashpw(tempPassword, BCrypt.gensalt());

                    // tạo tài khoản owner 
                    User newOwner = new User();
                    newOwner.setUsername(ownerUsername);
                    newOwner.setPassword(hashedPassword);
                    newOwner.setEmail(ownerEmail);
                    newOwner.setRole("OWNER");
                    int ownerID = userManagementDAO.addUser(newOwner);

                    if (ownerID == -1) {
                        throw new IllegalStateException("Không thể tạo tài khoản owner");
                    }

                    CinemaChain newChain = new CinemaChain();
                    newChain.setName(chainName);
                    newChain.setAvatarURL(avatarURL);
                    newChain.setInformation(information);
                    newChain.setUserID(ownerID);

                    boolean addSuccess = cinemaChainManagementDAO.addCinemaChain(newChain);
                    if (addSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Thêm chuỗi rạp và tạo tài khoản owner thành công");
                        jsonResponse.put("ownerUsername", ownerUsername);
                        jsonResponse.put("tempPassword", tempPassword);
                        jsonResponse.put("ownerEmail", ownerEmail);
                    } else {
                        // nếu như case thêm chuỗi rạp thất bại và sẽ xóa đi
                        userManagementDAO.deleteUser(ownerID);
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể thêm chuỗi rạp");
                    }
                    break;
                case "update":
                    String cinemaChainIDStr = request.getParameter("cinemaChainID");
                    if (cinemaChainIDStr == null || cinemaChainIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID chuỗi rạp không hợp lệ");
                    }
                    int cinemaChainID = Integer.parseInt(cinemaChainIDStr);
                    CinemaChain chainToUpdate = cinemaChainManagementDAO.getCinemaChainByID(cinemaChainID);
                    if (chainToUpdate == null) {
                        throw new IllegalArgumentException("Không tìm thấy chuỗi rạp với ID: " + cinemaChainID);
                    }
                    String updatedName = request.getParameter("chainName");
                    String updatedAvatarURL = request.getParameter("avatarURL");
                    String updatedInformation = request.getParameter("information");
                    if (updatedName == null || updatedName.trim().isEmpty()) {
                        throw new IllegalArgumentException("Tên chuỗi rạp không được để trống");
                    }
                    chainToUpdate.setName(updatedName);
                    chainToUpdate.setAvatarURL(updatedAvatarURL);
                    chainToUpdate.setInformation(updatedInformation);
                    boolean updateSuccess = cinemaChainManagementDAO.updateCinemaChain(chainToUpdate);
                    if (updateSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Cập nhật chuỗi rạp thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể cập nhật chuỗi rạp");
                    }
                    break;
                case "delete":
                    cinemaChainIDStr = request.getParameter("cinemaChainID");
                    if (cinemaChainIDStr == null || cinemaChainIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID chuỗi rạp không hợp lệ");
                    }
                    cinemaChainID = Integer.parseInt(cinemaChainIDStr);
                    int cinemaCount = cinemaChainManagementDAO.getCinemaCountForChain(cinemaChainID);
                    if (cinemaCount > 0) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể xóa chuỗi rạp vì còn " + cinemaCount + " rạp thuộc chuỗi này.");
                    } else {
                        boolean deleteSuccess = cinemaChainManagementDAO.deleteCinemaChain(cinemaChainID);
                        if (deleteSuccess) {
                            jsonResponse.put("success", true);
                            jsonResponse.put("message", "Xóa chuỗi rạp thành công");
                        } else {
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "Không thể xóa chuỗi rạp");
                        }
                    }
                    break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Hành động không hợp lệ");
                    break;
            }
// cập nhhaats danh sách chuỗi rạp mới lưu vào session
            List<CinemaChain> updatedCinemaChains = cinemaChainManagementDAO.getAllCinemaChains();
            session.setAttribute("cinemaChains", updatedCinemaChains);

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private void processCinemaAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String action = request.getParameter("action");

            switch (action) {
                case "add":
                    String cinemaName = request.getParameter("cinemaName");
                    String address = request.getParameter("address");
                    String province = request.getParameter("province");
                    String district = request.getParameter("district");
                    String commune = request.getParameter("commune");
                    String cinemaChainIDStr = request.getParameter("cinemaChainID");

                    if (cinemaName == null || cinemaName.trim().isEmpty() || address == null || address.trim().isEmpty()
                            || province == null || province.trim().isEmpty() || district == null || district.trim().isEmpty()
                            || commune == null || commune.trim().isEmpty() || cinemaChainIDStr == null || cinemaChainIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin");
                    }

                    int cinemaChainID = Integer.parseInt(cinemaChainIDStr);

                    // check xem rạp đã tồn tại hay k
                    if (cinemaManagementDAO.isCinemaExist(cinemaName, address)) {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Rạp đã tồn tại với tên và địa chỉ này");
                    } else {
                        Cinema newCinema = new Cinema();
                        newCinema.setName(cinemaName);
                        newCinema.setAddress(address);
                        newCinema.setProvince(province);
                        newCinema.setDistrict(district);
                        newCinema.setCommune(commune);
                        newCinema.setCinemaChainID(cinemaChainID);

                        boolean addSuccess = cinemaManagementDAO.addCinema(newCinema);
                        if (addSuccess) {
                            jsonResponse.put("success", true);
                            jsonResponse.put("message", "Thêm rạp thành công");
                        } else {
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "Không thể thêm rạp");
                        }
                    }
                    break;

                case "update":
                    String cinemaIDStr = request.getParameter("cinemaID");
                    if (cinemaIDStr == null || cinemaIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID rạp không hợp lệ");
                    }
                    int cinemaID = Integer.parseInt(cinemaIDStr);
                    Cinema cinemaToUpdate = cinemaManagementDAO.getCinemaByID(cinemaID);
                    if (cinemaToUpdate == null) {
                        throw new IllegalArgumentException("Không tìm thấy rạp với ID: " + cinemaID);
                    }

                    cinemaName = request.getParameter("cinemaName");
                    address = request.getParameter("address");
                    province = request.getParameter("province");
                    district = request.getParameter("district");
                    commune = request.getParameter("commune");
                    cinemaChainIDStr = request.getParameter("cinemaChainID");

                    if (cinemaName == null || cinemaName.trim().isEmpty() || address == null || address.trim().isEmpty()
                            || province == null || province.trim().isEmpty() || district == null || district.trim().isEmpty()
                            || commune == null || commune.trim().isEmpty() || cinemaChainIDStr == null || cinemaChainIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin");
                    }

                    // check rạp đã tồn tại thì k dc thêm vào
                    if (!cinemaName.equals(cinemaToUpdate.getName()) || !address.equals(cinemaToUpdate.getAddress())) {
                        if (cinemaManagementDAO.isCinemaExist(cinemaName, address)) {
                            jsonResponse.put("success", false);
                            jsonResponse.put("message", "Rạp đã tồn tại với tên và địa chỉ này");
                            break;
                        }
                    }

                    cinemaToUpdate.setName(cinemaName);
                    cinemaToUpdate.setAddress(address);
                    cinemaToUpdate.setProvince(province);
                    cinemaToUpdate.setDistrict(district);
                    cinemaToUpdate.setCommune(commune);
                    cinemaToUpdate.setCinemaChainID(Integer.parseInt(cinemaChainIDStr));

                    boolean updateSuccess = cinemaManagementDAO.updateCinema(cinemaToUpdate);
                    if (updateSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Cập nhật rạp thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể cập nhật rạp");
                    }
                    break;

                case "delete":
                    cinemaIDStr = request.getParameter("cinemaID");
                    if (cinemaIDStr == null || cinemaIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID rạp không hợp lệ");
                    }
                    cinemaID = Integer.parseInt(cinemaIDStr);
                    boolean deleteSuccess = cinemaManagementDAO.deleteCinema(cinemaID);
                    if (deleteSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Xóa rạp thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể xóa rạp");
                    }
                    break;

                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Hành động không hợp lệ");
                    break;
            }

            // cập nhật dự liệu danh sách rạp mới vào session
            List<CinemaChain> updatedCinemaChains = cinemaChainManagementDAO.getAllCinemaChains();
            Map<Integer, List<Cinema>> updatedCinemasByChain = new HashMap<>();
            for (CinemaChain chain : updatedCinemaChains) {
                List<Cinema> cinemasInChain = cinemaManagementDAO.getCinemasByCinemaChainID(chain.getCinemaChainID());
                updatedCinemasByChain.put(chain.getCinemaChainID(), cinemasInChain);
            }
            session.setAttribute("cinemaChains", updatedCinemaChains);
            session.setAttribute("cinemasByChain", updatedCinemasByChain);

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private void processMovieAction(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String action = request.getParameter("action");

            switch (action) {
                case "add":
                    String title = request.getParameter("title");
                    String synopsis = request.getParameter("synopsis");
                    String datePublishedStr = request.getParameter("datePublished");
                    String imageURL = request.getParameter("imageURL");
                    String ratingStr = request.getParameter("rating");
                    String country = request.getParameter("country");
                    String linkTrailer = request.getParameter("linkTrailer");
                    String cinemaIDStr = request.getParameter("cinemaID");
                    String type = request.getParameter("type");
                    String statusStr = request.getParameter("status");

                    if (title == null || title.trim().isEmpty()) {
                        throw new IllegalArgumentException("Vui lòng nhập tiêu đề phim");
                    }

                    Movie newMovie = new Movie();
                    newMovie.setTitle(title);
                    newMovie.setSynopsis(synopsis);
                    if (datePublishedStr != null && !datePublishedStr.trim().isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date utilDate = sdf.parse(datePublishedStr);
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            newMovie.setDatePublished(sqlDate);
                        } catch (ParseException e) {
                            throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-dd");
                        }
                    }
                    newMovie.setImageURL(imageURL);
                    if (ratingStr != null && !ratingStr.trim().isEmpty()) {
                        try {
                            newMovie.setRating(Float.parseFloat(ratingStr));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Định dạng đánh giá không hợp lệ");
                        }
                    }
                    newMovie.setCountry(country);
                    newMovie.setLinkTrailer(linkTrailer);
                    if (cinemaIDStr != null && !cinemaIDStr.trim().isEmpty()) {
                        try {
                            newMovie.setCinemaID(Integer.parseInt(cinemaIDStr));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("ID rạp chiếu không hợp lệ");
                        }
                    }
                    newMovie.setType(type);
                    if (statusStr != null && !statusStr.trim().isEmpty()) {
                        newMovie.setStatus(Boolean.parseBoolean(statusStr));
                    }

                    boolean addSuccess = movieManagementDAO.addMovie(newMovie);
                    if (addSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Thêm phim thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể thêm phim");
                    }
                    break;

                case "update":
                    String movieIDStr = request.getParameter("movieID");
                    if (movieIDStr == null || movieIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID phim không hợp lệ");
                    }
                    int movieID;
                    try {
                        movieID = Integer.parseInt(movieIDStr);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("ID phim phải là số nguyên");
                    }
                    Movie movieToUpdate = movieManagementDAO.getMovieByID(movieID);
                    if (movieToUpdate == null) {
                        throw new IllegalArgumentException("Không tìm thấy phim với ID: " + movieID);
                    }

                    title = request.getParameter("title");
                    synopsis = request.getParameter("synopsis");
                    datePublishedStr = request.getParameter("datePublished");
                    imageURL = request.getParameter("imageURL");
                    ratingStr = request.getParameter("rating");
                    country = request.getParameter("country");
                    linkTrailer = request.getParameter("linkTrailer");
                    cinemaIDStr = request.getParameter("cinemaID");
                    type = request.getParameter("type");
                    statusStr = request.getParameter("status");
                    String[] genreIDsStr = request.getParameterValues("genreIDs");

                    if (title != null && !title.trim().isEmpty()) {
                        movieToUpdate.setTitle(title);
                    }
                    movieToUpdate.setSynopsis(synopsis);
                    if (datePublishedStr != null && !datePublishedStr.trim().isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date utilDate = sdf.parse(datePublishedStr);
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            movieToUpdate.setDatePublished(sqlDate);
                        } catch (ParseException e) {
                            throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng yyyy-MM-dd");
                        }
                    }
                    movieToUpdate.setImageURL(imageURL);
                    if (ratingStr != null && !ratingStr.trim().isEmpty()) {
                        try {
                            movieToUpdate.setRating(Float.parseFloat(ratingStr));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Định dạng đánh giá không hợp lệ");
                        }
                    }
                    movieToUpdate.setCountry(country);
                    movieToUpdate.setLinkTrailer(linkTrailer);
                    if (cinemaIDStr != null && !cinemaIDStr.trim().isEmpty()) {
                        try {
                            movieToUpdate.setCinemaID(Integer.parseInt(cinemaIDStr));
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("ID rạp chiếu không hợp lệ");
                        }
                    }
                    movieToUpdate.setType(type);
                    if (statusStr != null && !statusStr.trim().isEmpty()) {
                        movieToUpdate.setStatus(Boolean.parseBoolean(statusStr));
                    }

                    List<Integer> genreIDs = new ArrayList<>();
                    if (genreIDsStr != null) {
                        for (String genreIDStr : genreIDsStr) {
                            try {
                                genreIDs.add(Integer.parseInt(genreIDStr));
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("ID thể loại không hợp lệ: " + genreIDStr);
                            }
                        }
                    }

                    boolean updateSuccess = movieManagementDAO.updateMovie(movieToUpdate, genreIDs);
                    if (updateSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Cập nhật phim thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể cập nhật phim");
                    }
                    break;

                case "updateStatus":
                    movieIDStr = request.getParameter("movieID");
                    statusStr = request.getParameter("status");
                    if (movieIDStr == null || movieIDStr.trim().isEmpty() || statusStr == null || statusStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID phim hoặc trạng thái không hợp lệ");
                    }
                    try {
                        movieID = Integer.parseInt(movieIDStr);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("ID phim phải là số nguyên");
                    }
                    boolean status = Boolean.parseBoolean(statusStr);
                    boolean updateStatusSuccess = movieManagementDAO.updateMovieStatus(movieID, status);
                    if (updateStatusSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Cập nhật trạng thái phim thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể cập nhật trạng thái phim");
                    }
                    break;

                case "delete":
                    String deleteMovieIDStr = request.getParameter("movieID");
                    if (deleteMovieIDStr == null || deleteMovieIDStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("ID phim không hợp lệ");
                    }
                    int deleteMovieID = Integer.parseInt(deleteMovieIDStr);
                    boolean deleteSuccess = movieManagementDAO.deleteMovie(deleteMovieID);
                    if (deleteSuccess) {
                        jsonResponse.put("success", true);
                        jsonResponse.put("message", "Xóa phim thành công");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("message", "Không thể xóa phim");
                    }
                    break;
                default:
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Hành động không hợp lệ");
                    break;
            }

            // cập nhật dữ liệu danh sách phim mới vào session
            List<Movie> updatedMovies = movieManagementDAO.getAllMovies();
            session.setAttribute("movies", updatedMovies);

        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private String generateUniqueUsername(String chainName) {
        String baseUsername = chainName.replaceAll("\\s+", "").toLowerCase();
        String username = baseUsername;
        int counter = 1;
        while (userManagementDAO.userExists(username)) {
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String generateDefaultEmail(String chainName) {
        return chainName.replaceAll("\\s+", "").toLowerCase() + "@gmail.com";
    }
    private void getCinemasForMovie(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    JSONObject jsonResponse = new JSONObject();

    try {
        String movieIdStr = request.getParameter("movieId");
        if (movieIdStr == null || movieIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("ID phim không hợp lệ");
        }
        int movieId = Integer.parseInt(movieIdStr);

        List<Cinema> cinemas = cinemaManagementDAO.getCinemasShowingMovie(movieId);
        
        JSONArray cinemasArray = new JSONArray();
        for (Cinema cinema : cinemas) {
            JSONObject cinemaObj = new JSONObject();
            cinemaObj.put("id", cinema.getCinemaID());
            cinemaObj.put("name", cinema.getName());
            cinemaObj.put("address", cinema.getAddress());
            cinemaObj.put("province", cinema.getProvince());
            cinemaObj.put("district", cinema.getDistrict());
            cinemaObj.put("commune", cinema.getCommune());
            cinemasArray.put(cinemaObj);
        }

        jsonResponse.put("success", true);
        jsonResponse.put("cinemas", cinemasArray);
    } catch (Exception e) {
        jsonResponse.put("success", false);
        jsonResponse.put("message", "Lỗi: " + e.getMessage());
        e.printStackTrace();
    }

    out.print(jsonResponse.toString());
    out.flush();
}
}
