package controller.schedule;

import DAOSchedule.CinemaChainScheduleDAO;
import DAOSchedule.CinemaScheduleDAO;
import DAOSchedule.MovieScheduleDAO;
import DAOSchedule.MovieScheduleSlotDAO;
import DAOSchedule.SeatDAO;
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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Seat;
import util.RouterJSP;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {

    private CinemaChainScheduleDAO cinemaChainDAO;
    private CinemaScheduleDAO cinemaDAO;
    private MovieScheduleDAO movieDAO;
    private MovieScheduleSlotDAO movieSlotDAO;
    private SeatDAO seatDAO;
    private Map<Integer, List<String>> movieGenres;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            this.cinemaChainDAO = new CinemaChainScheduleDAO(context);
            this.cinemaDAO = new CinemaScheduleDAO(context);
            this.movieDAO = new MovieScheduleDAO(context);
            this.movieSlotDAO = new MovieScheduleSlotDAO(context);
            this.seatDAO = new SeatDAO(context);
        } catch (Exception ex) {
            Logger.getLogger(ShowtimeServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException("Failed to initialize DAO", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Lấy danh sách chuỗi rạp
        List<CinemaChain> cinemaChains = cinemaChainDAO.getAllCinemaChains();
        request.setAttribute("cinemaChains", cinemaChains);

        if (cinemaChains.isEmpty()) {
            request.setAttribute("errorMessage", "Không có chuỗi rạp nào.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            return;
        }

        Integer cinemaChainID = (Integer) session.getAttribute("selectedCinemaChainID");
        String cinemaChainIDParam = request.getParameter("cinemaChainID");
        if (cinemaChainID == null || cinemaChainIDParam == null
                || !cinemaChainID.equals(Integer.parseInt(cinemaChainIDParam))) {
            cinemaChainID = (cinemaChainIDParam != null && !cinemaChainIDParam.isEmpty())
                    ? Integer.parseInt(cinemaChainIDParam)
                    : cinemaChains.get(0).getCinemaChainID();
            session.setAttribute("selectedCinemaChainID", cinemaChainID);
            session.removeAttribute("selectedCinemaID");
        }

        List<Cinema> cinemas = cinemaDAO.getCinemasByChain(cinemaChainID);
        request.setAttribute("cinemas", cinemas);

        if (cinemas.isEmpty()) {
            request.setAttribute("errorMessage", "Không có rạp nào cho chuỗi rạp đã chọn.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            return;
        }

        Integer cinemaID = (Integer) session.getAttribute("selectedCinemaID");
        String cinemaIDParam = request.getParameter("cinemaID");
        if (cinemaID == null || cinemaIDParam == null || !cinemaID.equals(Integer.parseInt(cinemaIDParam))) {
            cinemaID = (cinemaIDParam != null && !cinemaIDParam.isEmpty())
                    ? Integer.parseInt(cinemaIDParam)
                    : cinemas.get(0).getCinemaID();
            session.setAttribute("selectedCinemaID", cinemaID);
        }

        Cinema selectedCinema = cinemaDAO.getCinemaById(cinemaID);
        request.setAttribute("selectedCinema", selectedCinema);

        List<LocalDate> availableDates = movieSlotDAO.getAvailableDates(cinemaID);
        request.setAttribute("availableDates", availableDates);

        if (availableDates.isEmpty()) {
            request.setAttribute("errorMessage", "Không có ngày nào có suất chiếu.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            return;
        }

        LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
        String dateParam = request.getParameter("date");
        if (selectedDate == null || dateParam != null) {
            selectedDate = (dateParam != null && !dateParam.isEmpty())
                    ? LocalDate.parse(dateParam)
                    : availableDates.get(0);
            session.setAttribute("selectedDate", selectedDate);
        }

        List<Movie> movies = movieDAO.getMoviesByCinemaAndDate(cinemaID, selectedDate);
        request.setAttribute("movies", movies);

        // Lấy thể loại cho mỗi bộ phim
        Map<Integer, List<String>> movieGenres = new HashMap<>();
        for (Movie movie : movies) {
            List<String> genres = movieDAO.getMovieGenres(movie.getMovieID());
            movieGenres.put(movie.getMovieID(), genres);
        }
        request.setAttribute("movieGenres", movieGenres);

        List<MovieSlot> movieSlots = movieSlotDAO.getMovieSlotsByCinemaAndDate(cinemaID, selectedDate);

        Map<Movie, List<MovieSlot>> movieSlotsByMovie = movieSlots.stream()
                .collect(Collectors.groupingBy(movieSlot -> movies.stream()
                        .filter(movie -> movie.getMovieID() == movieSlot.getMovieID())
                        .findFirst()
                        .orElse(null)));

        request.setAttribute("selectedCinemaChainID", cinemaChainID);
        request.setAttribute("selectedCinemaID", cinemaID);
        request.setAttribute("selectedDate", selectedDate);
        request.setAttribute("movieSlotsByMovie", movieSlotsByMovie);

        request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("selectSlot".equals(action)) {
            String movieSlotIDParam = request.getParameter("movieSlotID");
            if (movieSlotIDParam != null) {
                int movieSlotID = Integer.parseInt(movieSlotIDParam);

                MovieSlot selectedSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
                request.setAttribute("selectedSlot", selectedSlot);

                List<Seat> seats = seatDAO.getSeatsByRoomId(selectedSlot.getRoomID());
                request.setAttribute("seats", seats);

                request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Thông tin suất chiếu không hợp lệ.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            }
        }
    }
}
