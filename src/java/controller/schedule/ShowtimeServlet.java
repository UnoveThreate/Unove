package controller.schedule;

import DAOSchedule.CinemaChainScheduleDAO;
import DAOSchedule.CinemaScheduleDAO;
import DAOSchedule.MovieDAO;
import DAOSchedule.MovieSlotDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CinemaChain;
import model.Cinema;
import model.Movie;
import model.MovieSlot;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import util.RouterJSP;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {

    private CinemaChainScheduleDAO cinemaChainDAO;
    private CinemaScheduleDAO cinemaDAO;
    private MovieDAO movieDAO;
    private MovieSlotDAO movieSlotDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
          
            this.cinemaChainDAO = new CinemaChainScheduleDAO(getServletContext());
            this.cinemaDAO = new CinemaScheduleDAO(getServletContext());
            this.movieDAO = new MovieDAO(getServletContext());
            this.movieSlotDAO = new MovieSlotDAO(getServletContext());
            
        } catch (Exception ex) {
            Logger.getLogger(ShowtimeServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Failed to initialize DAO", ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Lấy session

        // Lấy danh sách chuỗi rạp
        List<CinemaChain> cinemaChains = cinemaChainDAO.getAllCinemaChains();

        // Kiểm tra xem danh sách chuỗi rạp có rỗng không
        if (cinemaChains.isEmpty()) {
            request.setAttribute("errorMessage", "Không có chuỗi rạp nào.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
            return; // Dừng xử lý nếu không có chuỗi rạp
        }

        request.setAttribute("cinemaChains", cinemaChains);

        // Xác định chuỗi rạp được chọn
        Integer cinemaChainID = (Integer) session.getAttribute("selectedCinemaChainID");
        if (cinemaChainID == null) {
            cinemaChainID = Integer.parseInt(request.getParameter("cinemaChainID") != null
                    ? request.getParameter("cinemaChainID")
                    : String.valueOf(cinemaChains.get(0).getCinemaChainID()));
            session.setAttribute("selectedCinemaChainID", cinemaChainID); // Lưu vào session
        }

        // Lấy danh sách rạp thuộc chuỗi rạp đã chọn
        List<Cinema> cinemas = cinemaDAO.getCinemasByChain(cinemaChainID);
        request.setAttribute("cinemas", cinemas);

        // Kiểm tra xem danh sách rạp có rỗng không
        if (cinemas.isEmpty()) {
            request.setAttribute("errorMessage", "Không có rạp nào cho chuỗi rạp đã chọn.");
            request.getRequestDispatcher(RouterJSP.SHOWTIME_PAGE).forward(request, response);
            return; // Dừng xử lý nếu không có rạp
        }

        // Xác định rạp được chọn
        Integer cinemaID = (Integer) session.getAttribute("selectedCinemaID");
        if (cinemaID == null) {
            cinemaID = Integer.parseInt(request.getParameter("cinemaID") != null
                    ? request.getParameter("cinemaID")
                    : String.valueOf(cinemas.get(0).getCinemaID()));
            session.setAttribute("selectedCinemaID", cinemaID); // Lưu vào session
        }

        // Lấy thông tin chi tiết của rạp được chọn
        Cinema selectedCinema = cinemaDAO.getCinemaById(cinemaID);
        request.setAttribute("selectedCinema", selectedCinema);

        // Lấy danh sách ngày có suất chiếu
        List<LocalDate> availableDates = movieSlotDAO.getAvailableDates(cinemaID);
        request.setAttribute("availableDates", availableDates);

        // Kiểm tra xem danh sách ngày có rỗng không
        if (availableDates.isEmpty()) {
            request.setAttribute("errorMessage", "Không có ngày nào có suất chiếu.");
            request.getRequestDispatcher(RouterJSP.SHOWTIME_PAGE).forward(request, response);
            return; // Dừng xử lý nếu không có ngày
        }

        // Xác định ngày được chọn
        LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
        if (selectedDate == null) {
            selectedDate = LocalDate.parse(request.getParameter("date") != null
                    ? request.getParameter("date")
                    : availableDates.get(0).toString());
            session.setAttribute("selectedDate", selectedDate); // Lưu vào session
        }

        // Lấy danh sách phim đang chiếu tại rạp
        List<Movie> movies = movieDAO.getMoviesByCinema(cinemaID);

        // Lấy danh sách suất chiếu theo rạp và ngày
        List<MovieSlot> movieSlots = movieSlotDAO.getMovieSlotsByCinemaAndDate(cinemaID, selectedDate);

        // Nhóm các suất chiếu theo phim
        Map<Movie, List<MovieSlot>> movieSlotsByMovie = movieSlots.stream()
                .collect(Collectors.groupingBy(movieSlot -> movies.stream()
                .filter(movie -> movie.getMovieID() == movieSlot.getMovieID())
                .findFirst()
                .orElse(null)));

        // Thêm dữ liệu vào request để hiển thị
        request.setAttribute("selectedCinemaChainID", cinemaChainID);
        request.setAttribute("selectedCinemaID", cinemaID);
        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("movies", movies);
        request.setAttribute("movieSlotsByMovie", movieSlotsByMovie);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher(RouterJSP.SHOWTIME_PAGE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu có yêu cầu từ form (nếu có)
        // Xóa dữ liệu trong session nếu người dùng muốn chọn lại
//        HttpSession session = request.getSession();
//        session.removeAttribute("selectedCinemaChainID");
//        session.removeAttribute("selectedCinemaID");
//        session.removeAttribute("selectedDate");

        // Sau đó chuyển tiếp lại về doGet
        doGet(request, response);
    }
}
