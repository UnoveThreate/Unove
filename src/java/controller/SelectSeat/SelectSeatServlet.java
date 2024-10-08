package controller;

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
import model.*;
import util.RouterJSP;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("doGet method started");
        try {
            String movieSlotIDParam = request.getParameter("movieSlotID");
            MovieSlot movieSlot = null;

            if (movieSlotIDParam == null || movieSlotIDParam.isEmpty()) {
                LOGGER.info("movieSlotID not provided, fetching latest movie slot");
                movieSlot = movieSlotDAO.getLatestMovieSlot();
                if (movieSlot == null) {
                    throw new ServletException("Không có suất chiếu nào hiện tại.");
                }
            } else {
                int movieSlotID = Integer.parseInt(movieSlotIDParam);
                LOGGER.info("Fetching MovieSlot for ID: " + movieSlotID);
                movieSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
                if (movieSlot == null) {
                    throw new ServletException("Suất chiếu không tồn tại.");
                }
            }

            LOGGER.info("Retrieved MovieSlot: " + movieSlot);

            List<Seat> seats = seatDAO.getSeatsByRoomId(movieSlot.getRoomID());
            LOGGER.info("Retrieved " + seats.size() + " seats for room ID: " + movieSlot.getRoomID());

            request.setAttribute("seats", seats);
            request.setAttribute("movieSlot", movieSlot);

            HttpSession session = request.getSession();
            BookingSession bookingSession = new BookingSession();
            bookingSession.setMovieSlotID(movieSlot.getMovieSlotID());
            session.setAttribute("bookingSession", bookingSession);
            LOGGER.info("BookingSession created and set in session");

            LOGGER.info("Forwarding to SELECT_SEAT JSP");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("doPost method started");
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            LOGGER.warning("Session is null");
            handleError(request, response, "Phiên làm việc đã hết hạn. Vui lòng thử lại.");
            return;
        }

        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

        if (bookingSession == null) {
            LOGGER.warning("BookingSession is null");
            handleError(request, response, "Thông tin đặt vé không hợp lệ. Vui lòng thử lại.");
            return;
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
            bookingSession.setTotalPrice(totalPrice);

            // Tạo đơn hàng mới
            Order order = new Order();
            order.setUserID(((User) session.getAttribute("user")).getUserID());
            order.setMovieSlotID(movieSlotID);
            order.setTimeCreated((Timestamp) new Date());
            order.setStatus("Đang xử lý");
            int orderID = orderDAO.createOrder(order);

            // Tạo vé cho từng ghế đã chọn
            for (Seat seat : selectedSeats) {
                Ticket ticket = new Ticket();
                ticket.setOrderID(orderID);
                ticket.setSeatID(seat.getSeatID());
                ticket.setStatus("Đã đặt");
                ticketDAO.createTicket(ticket);
            }

            // Cập nhật BookingSession trong session
            bookingSession.setStatus("Đã đặt vé");
            session.setAttribute("bookingSession", bookingSession);

          
            request.setAttribute("order", order);
            request.setAttribute("movieSlot", movieSlot);
            request.setAttribute("selectedSeats", selectedSeats);
            request.getRequestDispatcher("/bookingConfirmation.jsp").forward(request, response);

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