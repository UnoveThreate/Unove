/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.booking;

import DAO.payment.PaymentDAO;
import model.BookingSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import model.Cinema;
import model.Movie;
import util.RouterJSP;

/**
 *
 * @author DELL
 */
@WebServlet(name = "BookTicketServlet", urlPatterns = {"/bookTicket"})
public class BookTicketServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(BookTicketServlet.class.getName());
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();

            this.paymentDAO = new PaymentDAO(context);
            LOGGER.info("SelectSeatServlet initialized successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing SelectSeatServlet", e);
            throw new ServletException("Không thể khởi tạo DAO", e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BookTicketServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookTicketServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

        if (bookingSession == null) {
            request.getRequestDispatcher(RouterJSP.LOGIN).forward(request, response);
            return;
        }

        // Retrieve movieSlotID from session
        Integer movieSlotID = (Integer) bookingSession.getMovieSlotID();

        // Use movieSlotID to obtain movieID and cinemaID
        if (movieSlotID != null) {
            Movie movie = paymentDAO.getMovieByMovieSlotID(movieSlotID);
            Cinema cinema = paymentDAO.getCinemaByMovieSlot(movieSlotID);

            

            System.out.println(cinema);
            System.out.println(movie);


            request.setAttribute("movie", movie);
            request.setAttribute("cinema", cinema);
            request.setAttribute("movieSlot", bookingSession.getMovieSlot());
            request.setAttribute("selectedSeats", bookingSession.getListSeats());
            request.setAttribute("totalPrice", bookingSession.getTotalPrice());

            // Forward to orderDetail.jsp page
            request.getRequestDispatcher(RouterJSP.ORDER_DETAIL).forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Không tìm thấy thông tin suất chiếu.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
