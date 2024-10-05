package controller.schedule;

import DAOHuy.CinemaChainDAO;
import DAOHuy.CinemaDAO;
import DAOHuy.MovieDAO;
import DAOHuy.MovieSlotDAO;
import DAOHuy.SeatDAO;
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
import model.Seat;
import util.RouterJSP;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {
    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;
    private MovieDAO movieDAO;
    private MovieSlotDAO movieSlotDAO;
    private SeatDAO seatDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            this.cinemaChainDAO = new CinemaChainDAO(context);
            this.cinemaDAO = new CinemaDAO(context);
            this.movieDAO = new MovieDAO(context);
            this.movieSlotDAO = new MovieSlotDAO(context);
            this.seatDAO = new SeatDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(ShowtimeServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Failed to initialize DAO", ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy danh sách chuỗi rạp
        List<CinemaChain> cinemaChains = cinemaChainDAO.getAllCinemaChains();
        request.setAttribute("cinemaChains", cinemaChains);

        // Kiểm tra xem danh sách chuỗi rạp có rỗng không
        if (cinemaChains.isEmpty()) {
            request.setAttribute("errorMessage", "Không có chuỗi rạp nào.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            return;
        }

        // Xác định chuỗi rạp được chọn
        Integer cinemaChainID = (Integer) session.getAttribute("selectedCinemaChainID");
        String cinemaChainIDParam = request.getParameter("cinemaChainID");
        if (cinemaChainID == null || cinemaChainIDParam == null || !cinemaChainID.equals(Integer.parseInt(cinemaChainIDParam))) {
            cinemaChainID = (cinemaChainIDParam != null && !cinemaChainIDParam.isEmpty()) ?
                    Integer.parseInt(cinemaChainIDParam) : 
                    cinemaChains.get(0).getCinemaChainID();
            session.setAttribute("selectedCinemaChainID", cinemaChainID);
            session.removeAttribute("selectedCinemaID"); // Reset selectedCinemaID when chain changes
        }

        // Lấy danh sách rạp thuộc chuỗi rạp đã chọn
        List<Cinema> cinemas = cinemaDAO.getCinemasByChain(cinemaChainID);
        request.setAttribute("cinemas", cinemas);

        // Kiểm tra xem danh sách rạp có rỗng không
        if (cinemas.isEmpty()) {
            request.setAttribute("errorMessage", "Không có rạp nào cho chuỗi rạp đã chọn.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            return;
        }

        // Xác định rạp được chọn
        Integer cinemaID = (Integer) session.getAttribute("selectedCinemaID");
        String cinemaIDParam = request.getParameter("cinemaID");
        if (cinemaID == null || cinemaIDParam == null || !cinemaID.equals(Integer.parseInt(cinemaIDParam))) {
            cinemaID = (cinemaIDParam != null && !cinemaIDParam.isEmpty()) ?
                    Integer.parseInt(cinemaIDParam) : 
                    cinemas.get(0).getCinemaID();
            session.setAttribute("selectedCinemaID", cinemaID);
        }

        // Lấy thông tin chi tiết của rạp được chọn
        Cinema selectedCinema = cinemaDAO.getCinemaById(cinemaID);
        request.setAttribute("selectedCinema", selectedCinema);

        // Lấy danh sách phim đang chiếu tại rạp
        List<Movie> movies = movieDAO.getMoviesByCinema(cinemaID);
        request.setAttribute("movies", movies);

        // Lấy danh sách ngày có suất chiếu
        List<LocalDate> availableDates = movieSlotDAO.getAvailableDates(cinemaID);
        request.setAttribute("availableDates", availableDates);

        // Kiểm tra xem danh sách ngày có rỗng không
        if (availableDates.isEmpty()) {
            request.setAttribute("errorMessage", "Không có ngày nào có suất chiếu.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            return;
        }

        // Xác định ngày được chọn
        LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
        String dateParam = request.getParameter("date");
        if (selectedDate == null) {
            selectedDate = (dateParam != null && !dateParam.isEmpty()) ?
                    LocalDate.parse(dateParam) : 
                    availableDates.get(0);
            session.setAttribute("selectedDate", selectedDate);
        }

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
        request.setAttribute("movieSlotsByMovie", movieSlotsByMovie);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("selectSlot".equals(action)) {
            String movieSlotIDParam = request.getParameter("movieSlotID");
            if (movieSlotIDParam != null) {
                int movieSlotID = Integer.parseInt(movieSlotIDParam);
                
                // Lấy thông tin suất chiếu
                MovieSlot selectedSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
                request.setAttribute("selectedSlot", selectedSlot);
                
                // Lấy danh sách ghế cho suất chiếu
                List<Seat> seats = seatDAO.getSeatsByRoomId(selectedSlot.getRoomID());
                request.setAttribute("seats", seats);
                
                // Chuyển tiếp đến trang chọn ghế
                request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Thông tin suất chiếu không hợp lệ.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            }
        }
    }
}
