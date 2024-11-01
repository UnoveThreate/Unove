package controller.owner.movie;

import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import DAO.cinemaChainOwnerDAO.CinemaDAO;
import DAO.cinemaChainOwnerDAO.MovieDAO;
import DAO.cinemaChainOwnerDAO.RoomDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.owner.Movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Cinema;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/movie")
public class MovieServlet extends HttpServlet {

    private MovieDAO movieDAO;
    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;
    private RoomDAO roomDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            movieDAO = new MovieDAO(context);
            cinemaChainDAO = new CinemaChainDAO(context);
            cinemaDAO = new CinemaDAO(context);
            roomDAO = new RoomDAO(context);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize MovieDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Check if user is logged in and has the owner role
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }
        request.setCharacterEncoding("UTF-8");  // Cấu hình mã hóa đầu vào
        response.setCharacterEncoding("UTF-8"); // Cấu hình mã hóa đầu ra
        loadCinemas(request);
        request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_LIST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Kiểm tra quyền truy cập
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        // Lấy cinemaID từ request
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            // Nếu cinemaID chưa có, load lại danh sách rạp và hiển thị trang mà không cần chuyển hướng
            loadCinemas(request);
            request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_LIST_PAGE).forward(request, response);
            return;
        }

        try {
            Integer cinemaID = Integer.parseInt(cinemaIDStr);
            List<Movie> movies = movieDAO.getAllMovies(cinemaID);
            String cinemaName = movieDAO.getCinemaNameByID(cinemaID);

            request.setAttribute("movies", movies);
            request.setAttribute("cinemaID", cinemaID);
            request.setAttribute("cinemaName", cinemaName);
            request.setAttribute("selectedCinemaID", cinemaID); // Cập nhật cinemaID đã chọn

            loadCinemas(request); // Load danh sách rạp
            request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_LIST_PAGE).forward(request, response);

        } catch (NumberFormatException e) {
            loadCinemas(request);
            request.getRequestDispatcher(RouterJSP.OWNER_MOVIE_LIST_PAGE).forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving movies for cinema ID: " + cinemaIDStr, e);
        }
    }

    private void loadCinemas(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Kiểm tra xem userID có trong session không
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID == null) {
            System.out.println("userID không có trong session");
            return;
        }

        try {
            int cinemaChainID = cinemaChainDAO.getCinemaChainByUserID(userID).getCinemaChainID();
            List<Cinema> cinemas = cinemaDAO.getCinemasByCinemaChainID(cinemaChainID);

            if (cinemas == null || cinemas.isEmpty()) {
                System.out.println("Không có cinema nào trong cinemaChainID: " + cinemaChainID);
            } else {
                System.out.println("Danh sách cinema đã lấy thành công.");
            }

            request.setAttribute("cinemas", cinemas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
