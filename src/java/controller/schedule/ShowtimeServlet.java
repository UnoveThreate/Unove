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
import model.Seat;
import util.RouterJSP;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet("/showtimes")
public class ShowtimeServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ShowtimeServlet.class.getName());

    private CinemaChainScheduleDAO cinemaChainDAO;
    private CinemaScheduleDAO cinemaDAO;
    private MovieScheduleDAO movieDAO;
    private MovieScheduleSlotDAO movieSlotDAO;
    private SeatDAO seatDAO;

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
            LOGGER.log(Level.SEVERE, "Failed to initialize DAO", ex);
            throw new ServletException("Failed to initialize DAO", ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            List<CinemaChain> cinemaChains = cinemaChainDAO.getAllCinemaChains();
            request.setAttribute("cinemaChains", cinemaChains);

            if (cinemaChains.isEmpty()) {
                request.setAttribute("errorMessage", "Không có chuỗi rạp nào.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
                return;
            }

            Integer cinemaChainID = processCinemaChainSelection(request, session, cinemaChains);
            List<Cinema> cinemas = cinemaDAO.getCinemasByChain(cinemaChainID);
            request.setAttribute("cinemas", cinemas);

            if (cinemas.isEmpty()) {
                request.setAttribute("errorMessage", "Không có rạp nào cho chuỗi rạp đã chọn.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
                return;
            }

            Integer cinemaID = processCinemaSelection(request, session, cinemas);
            Optional<Cinema> selectedCinema = Optional.ofNullable(cinemaDAO.getCinemaById(cinemaID));
            selectedCinema.ifPresent(cinema -> request.setAttribute("selectedCinema", cinema));

            LocalDate today = LocalDate.now();
            List<LocalDate> next7Days = IntStream.range(0, 7)
                    .mapToObj(today::plusDays)
                    .collect(Collectors.toList());
            request.setAttribute("availableDates", next7Days);

            LocalDate selectedDate = processDateSelection(request, session, next7Days);

            // Lấy danh sách các ngày có suất chiếu
            List<LocalDate> datesWithShowtimes = movieSlotDAO.getAvailableDates(cinemaID);
            request.setAttribute("datesWithShowtimes", datesWithShowtimes);

            List<Movie> movies = movieDAO.getMoviesByCinemaAndDate(cinemaID, selectedDate);
            request.setAttribute("movies", movies);

            Map<Integer, List<String>> movieGenres = processMovieGenres(movies);
            request.setAttribute("movieGenres", movieGenres);

            Map<Movie, List<MovieSlot>> movieSlotsByMovie = processMovieSlots(cinemaID, selectedDate, movies);

            request.setAttribute("selectedCinemaChainID", cinemaChainID);
            request.setAttribute("selectedCinemaID", cinemaID);
            request.setAttribute("selectedDate", selectedDate);
            request.setAttribute("movieSlotsByMovie", movieSlotsByMovie);

            LOGGER.log(Level.INFO, "Processing request for cinemaChainID: {0}, cinemaID: {1}, date: {2}",
                    new Object[] { cinemaChainID, cinemaID, selectedDate });

            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing request", e);
            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi xử lý yêu cầu.");
            request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
        }
    }

    private Integer processCinemaChainSelection(HttpServletRequest request, HttpSession session,
            List<CinemaChain> cinemaChains) {
        Integer cinemaChainID = (Integer) session.getAttribute("selectedCinemaChainID");
        String cinemaChainIDParam = request.getParameter("cinemaChainID");
        if (cinemaChainIDParam != null && !cinemaChainIDParam.isEmpty()) {
            try {
                cinemaChainID = Integer.parseInt(cinemaChainIDParam);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid cinemaChainID parameter", e);
                cinemaChainID = cinemaChains.get(0).getCinemaChainID();
            }
        } else if (cinemaChainID == null) {
            cinemaChainID = cinemaChains.get(0).getCinemaChainID();
        }
        session.setAttribute("selectedCinemaChainID", cinemaChainID);
        return cinemaChainID;
    }

    private Integer processCinemaSelection(HttpServletRequest request, HttpSession session, List<Cinema> cinemas) {
        Integer cinemaID = (Integer) session.getAttribute("selectedCinemaID");
        String cinemaIDParam = request.getParameter("cinemaID");
        if (cinemaIDParam != null && !cinemaIDParam.isEmpty()) {
            try {
                cinemaID = Integer.parseInt(cinemaIDParam);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid cinemaID parameter", e);
                cinemaID = cinemas.get(0).getCinemaID();
            }
        } else if (cinemaID == null) {
            cinemaID = cinemas.get(0).getCinemaID();
        }
        session.setAttribute("selectedCinemaID", cinemaID);
        return cinemaID;
    }

    private LocalDate processDateSelection(HttpServletRequest request, HttpSession session,
            List<LocalDate> availableDates) {
        LocalDate selectedDate = (LocalDate) session.getAttribute("selectedDate");
        String dateParam = request.getParameter("date");
        if (dateParam != null && !dateParam.isEmpty()) {
            selectedDate = LocalDate.parse(dateParam);
        } else if (selectedDate == null || !availableDates.contains(selectedDate)) {
            selectedDate = availableDates.get(0);
        }
        session.setAttribute("selectedDate", selectedDate);
        return selectedDate;
    }

    private Map<Integer, List<String>> processMovieGenres(List<Movie> movies) {
        Map<Integer, List<String>> movieGenres = new HashMap<>();
        for (Movie movie : movies) {
            List<String> genres = movieDAO.getMovieGenres(movie.getMovieID());
            movieGenres.put(movie.getMovieID(), genres);
        }
        return movieGenres;
    }

    private Map<Movie, List<MovieSlot>> processMovieSlots(Integer cinemaID, LocalDate selectedDate,
            List<Movie> movies) {
        LocalDateTime now = LocalDateTime.now();
        Map<Integer, Movie> movieMap = movies.stream()
                .collect(Collectors.toMap(Movie::getMovieID, movie -> movie));

        return movieSlotDAO.getMovieSlotsByCinemaAndDate(cinemaID, selectedDate).stream()
                .filter(slot -> slot.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        .isAfter(now))
                .sorted(Comparator.comparing(MovieSlot::getStartTime))
                .collect(Collectors.groupingBy(
                        slot -> movieMap.get(slot.getMovieID()),
                        Collectors.toList()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("selectSlot".equals(action)) {
            String movieSlotIDParam = request.getParameter("movieSlotID");
            if (movieSlotIDParam != null) {
                try {
                    int movieSlotID = Integer.parseInt(movieSlotIDParam);

                    MovieSlot selectedSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
                    request.setAttribute("selectedSlot", selectedSlot);

                    List<Seat> seats = seatDAO.getSeatsByRoomId(selectedSlot.getRoomID());
                    request.setAttribute("seats", seats);

                    request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "Invalid movieSlotID parameter", e);
                    request.setAttribute("errorMessage", "Thông tin suất chiếu không hợp lệ.");
                    request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Thông tin suất chiếu không hợp lệ.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            }
        }
    }
}