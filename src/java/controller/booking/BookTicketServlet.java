/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.booking;

import model.BookingSession;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.RouterJSP;

/**
 *
 * @author DELL
 */
@WebServlet(name = "BookTicketServlet", urlPatterns = {"/bookTicket"})
public class BookTicketServlet extends HttpServlet {

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
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
            return;
        }

        // Set booking details as attributes for JSP
        request.setAttribute("movie", bookingSession.getMovieID());
        request.setAttribute("cinema", bookingSession.getCinemaID());
        request.setAttribute("movieSlot", bookingSession.getMovieSlotID());
        request.setAttribute("selectedSeats", bookingSession.getSelectedSeatIDs());
        request.setAttribute("totalPrice", bookingSession.getTotalPrice());

        // Forward to orderDetail.jsp page
        request.getRequestDispatcher(RouterJSP.ORDER_DETAIL).forward(request, response);
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
