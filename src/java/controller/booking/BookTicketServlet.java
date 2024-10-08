/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.booking;

import DAO.payment.PaymentDAO;
import jakarta.servlet.ServletContext;
import model.BookingSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import model.CinemaChain;
import model.Movie;
import model.MovieSlot;
import model.Order;
import model.Seat;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author DELL
 */
@WebServlet(name = "BookTicketServlet", urlPatterns = {"/bookTicket"})
public class BookTicketServlet extends HttpServlet {

    PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        try {

            paymentDAO = new PaymentDAO(getServletContext());

        } catch (Exception ex) {
            Logger.getLogger(BookTicketServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            // Lấy thông tin từ session
            HttpSession session = request.getSession();
            BookingSession bookingSession = (BookingSession) session.getAttribute("bookingSession");

            // Kiểm tra xem bookingSession có tồn tại không
           
                // Lấy thông tin từ bookingSession

                int cinemaID = 1;
                int userID = 2;
                int movieSlotID = 1;

                // Đặt thuộc tính vào request để sử dụng trong JSP
                Cinema cinema = paymentDAO.getCinemaById(cinemaID);
                CinemaChain cinemaChain = paymentDAO.getCinemaChainByUserID(userID);
                MovieSlot movieSlot = paymentDAO.getMovieSlotById(movieSlotID);

                System.out.println(movieSlot);

                // Đặt thuộc tính vào request để sử dụng trong JSP
                request.setAttribute("cinema", cinema);
                request.setAttribute("cinemaChain", cinemaChain);
                request.setAttribute("movieSlot", movieSlot);

                // Chuyển hướng đến orderDetail.jsp
                request.getRequestDispatcher(RouterJSP.ORDER_DETAIL).forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(BookTicketServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("ErrorPage.jsp"); // Redirect đến trang lỗi
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
