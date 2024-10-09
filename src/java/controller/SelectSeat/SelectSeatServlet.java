package controller.SelectSeat;

import DAO.payment.PaymentDAO;
import DAOSchedule.MovieScheduleSlotDAO;
import DAOSchedule.SeatDAO;
import DAOSchedule.OrderDAO;
import DAOSchedule.TicketDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import util.RouterJSP;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BookingSession;
import model.Cinema;
import model.Movie;
import model.MovieSlot;
import model.Seat;
import util.RouterURL;

@WebServlet("/selectSeat")
public class SelectSeatServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SelectSeatServlet.class.getName());
    private SeatDAO seatDAO;
    private MovieScheduleSlotDAO movieSlotDAO;
    private OrderDAO orderDAO;
    private TicketDAO ticketDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            this.seatDAO = new SeatDAO(context);
            this.movieSlotDAO = new MovieScheduleSlotDAO(context);
            this.orderDAO = new OrderDAO(context);
            this.ticketDAO = new TicketDAO(context);
            this.paymentDAO = new PaymentDAO(context);
            LOGGER.info("SelectSeatServlet initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing SelectSeatServlet", e);
            throw new ServletException("Không thể khởi tạo DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("doGet method started");
        try {
            String movieSlotIDParam = request.getParameter("movieSlotID");
            String movieIDParam = request.getParameter("movieID");
            String cinemaIDParam = request.getParameter("cinemaID");

            LOGGER.info("Received parameters - movieSlotID: " + movieSlotIDParam
                    + ", movieID: " + movieIDParam + ", cinemaID: " + cinemaIDParam);

            if (movieSlotIDParam != null && movieIDParam != null && cinemaIDParam != null) {
                int movieSlotID = Integer.parseInt(movieSlotIDParam);
                int movieID = Integer.parseInt(movieIDParam);
                int cinemaID = Integer.parseInt(cinemaIDParam);

                MovieSlot selectedSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
                Movie movie = paymentDAO.getMovieByCinemaIDAndMovieID(movieID, cinemaID);
                Cinema cinema = paymentDAO.getCinemaById(cinemaID);

                request.setAttribute("selectedSlot", selectedSlot);
                request.setAttribute("movie", movie);
                request.setAttribute("cinema", cinema);

                LOGGER.info("Retrieved MovieSlot: " + selectedSlot);
                LOGGER.info("Retrieved Movie: " + movie);
                LOGGER.info("Retrieved Cinema: " + cinema);

                List<Seat> seats = seatDAO.getSeatsByRoomId(selectedSlot.getRoomID());
                request.setAttribute("seats", seats);
                request.setAttribute("movieSlotID", movieSlotID);
                request.setAttribute("movieID", movieID);
                request.setAttribute("cinemaID", cinemaID);

                request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
            } else {
                LOGGER.warning("Missing required parameters");
                request.setAttribute("errorMessage", "Thông tin suất chiếu không hợp lệ.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            }
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid parameter format", e);
            handleError(request, response, "Dữ liệu không hợp lệ.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doGet", e);
            handleError(request, response, "Đã xảy ra lỗi khi tải trang chọn ghế: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("doPost method started");

        HttpSession session = request.getSession();

        if (session == null) {
            LOGGER.warning("Session is null");
            handleError(request, response, "Phiên làm việc đã hết hạn. Vui lòng thử lại.");
            return;
        }

        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

        if (bookingSession == null) {
            bookingSession = new BookingSession();
        }

        String movieSlotIDParam = request.getParameter("movieSlotID");
        String selectedSeatIDsParam = request.getParameter("selectedSeatID");
        String movieIDParam = request.getParameter("movieID"); // Get selected movie ID
        String cinemaIDParam = request.getParameter("cinemaID");
        LOGGER.info("Received POST - movieSlotID: " + movieSlotIDParam + ", selectedSeatIDs: " + selectedSeatIDsParam + ", movieID: " + movieIDParam + ", cinemaID: " + cinemaIDParam);

        if (movieSlotIDParam == null || movieSlotIDParam.isEmpty()
                || cinemaIDParam == null || cinemaIDParam.isEmpty()
                || movieIDParam == null || movieIDParam.isEmpty()) {
            LOGGER.warning("One or more required parameters are null or empty");
            handleError(request, response, "Thiếu thông tin cần thiết. Vui lòng thử lại.");
            return;
        }

        try {
            int cinemaID = Integer.parseInt(cinemaIDParam);
            int movieSlotID = Integer.parseInt(movieSlotIDParam);
            int movieID = Integer.parseInt(movieIDParam);

            bookingSession.setCinemaID(cinemaID);
            bookingSession.setMovieSlotID(movieSlotID);
            bookingSession.setMovieID(movieID);

            Cinema cinema = paymentDAO.getCinemaById(cinemaID);
            MovieSlot movieSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
            Movie movie = paymentDAO.getMovieByCinemaIDAndMovieID(cinemaID, movieID);

            if (selectedSeatIDsParam == null || selectedSeatIDsParam.isEmpty()) {
                throw new ServletException("Vui lòng chọn ít nhất một ghế.");
            }

            String[] selectedSeatIDs = selectedSeatIDsParam.split(",");
            List<Seat> selectedSeats = getSelectedSeats(selectedSeatIDs);

            if (selectedSeats.isEmpty()) {
                throw new ServletException("Không có ghế hợp lệ được chọn.");
            }

            // Kiểm tra lại tính khả dụng của ghế
            for (Seat seat : selectedSeats) {
                if (!seatDAO.checkSeatAvailability(seat.getSeatID())) {
                    throw new ServletException("Ghế " + seat.getName() + " đã được đặt. Vui lòng chọn ghế khác.");
                }
                bookingSession.addSelectedSeatID(seat.getSeatID());
            }

            double totalPrice = calculateTotalPrice(selectedSeats, movieSlot);
            bookingSession.setTotalPrice(totalPrice);

            // Cập nhật BookingSession trong session
            bookingSession.setStatus("Đã đặt vé");

            session.setAttribute("bookingSession", bookingSession);

            request.setAttribute("movie", movie);
            request.setAttribute("cinema", cinema);
            request.setAttribute("movieSlot", movieSlot);
            request.setAttribute("selectedSeats", selectedSeats);
            response.sendRedirect(RouterURL.ORDER_DETAIL);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid movieSlotID", e);
            handleError(request, response, "Dữ liệu suất chiếu không hợp lệ.");
        } catch (ServletException e) {
            LOGGER.log(Level.WARNING, "ServletException", e);
            handleError(request, response, e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doPost", e);
            handleError(request, response, "Đã xảy ra lỗi khi xử lý đặt vé: " + e.getMessage());
        }
    }

    private List<Seat> getSelectedSeats(String[] selectedSeatIDs) throws Exception {
        List<Seat> selectedSeats = new ArrayList<>();
        for (String seatID : selectedSeatIDs) {
            try {
                int seatIDInt = Integer.parseInt(seatID);
                Seat seat = seatDAO.getSeatById(seatIDInt);
                if (seat != null) {
                    selectedSeats.add(seat);
                } else {
                    LOGGER.warning("Seat not found for ID: " + seatIDInt);
                }
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid seat ID: " + seatID);
            }
        }
        return selectedSeats;
    }

    private double calculateTotalPrice(List<Seat> selectedSeats, MovieSlot movieSlot) {
        double basePrice = movieSlot.getPrice();
        double discount = movieSlot.getDiscount();
        return selectedSeats.size() * basePrice * (1 - discount);
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        LOGGER.warning("Handling error: " + errorMessage);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}
