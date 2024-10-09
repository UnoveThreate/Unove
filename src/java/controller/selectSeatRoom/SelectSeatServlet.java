package controller.selectSeatRoom;

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

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            this.seatDAO = new SeatDAO(context);
            this.movieSlotDAO = new MovieScheduleSlotDAO(context);
            this.orderDAO = new OrderDAO(context);
            this.ticketDAO = new TicketDAO(context);
            LOGGER.info("SelectSeatServlet initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing SelectSeatServlet", e);
            throw new ServletException("Không thể khởi tạo DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("doGet method started");
        try {
            String movieSlotIDParam = request.getParameter("movieSlotID");
            if (movieSlotIDParam != null) {
                int movieSlotID = Integer.parseInt(movieSlotIDParam);

                MovieSlot selectedSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
                request.setAttribute("selectedSlot", selectedSlot);

                LOGGER.info("Retrieved MovieSlot: " + selectedSlot);

                List<Seat> seats = seatDAO.getSeatsByRoomId(selectedSlot.getRoomID());
                request.setAttribute("seats", seats);
                request.setAttribute("movieSlotID", movieSlotID);

                request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Thông tin suất chiếu không hợp lệ.");
                request.getRequestDispatcher(RouterJSP.SCHEDULE_MOVIE).forward(request, response);
            }

            request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid movieSlotID", e);
            handleError(request, response, "Suất chiếu không hợp lệ.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in doGet", e);
            handleError(request, response, "Đã xảy ra lỗi khi tải trang chọn ghế: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        LOGGER.info("Received POST - movieSlotID: " + movieSlotIDParam + ", selectedSeatIDs: " + selectedSeatIDsParam);

        if (movieSlotIDParam == null || movieSlotIDParam.isEmpty()) {
            LOGGER.warning("movieSlotID is null or empty");
            handleError(request, response, "Thiếu thông tin suất chiếu. Vui lòng thử lại.");
            return;
        }

        try {
            int movieSlotID = Integer.parseInt(movieSlotIDParam);
            bookingSession.setMovieSlotID(movieSlotID);

            MovieSlot movieSlot = movieSlotDAO.getMovieSlotById(movieSlotID);

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

            // Cập nhật BookingSession trong session
            bookingSession.setTotalPrice(totalPrice);
            bookingSession.setMovieSlotID(movieSlotID);
            bookingSession.setStatus("Đã đặt vé");
            bookingSession.setMovieSlot(movieSlot);
            bookingSession.setListSeats(selectedSeats);

            session.setAttribute("bookingSession", bookingSession);

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

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        LOGGER.warning("Handling error: " + errorMessage);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}