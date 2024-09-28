// File: TicketBookingServlet.java
package controller.ticket;

import dao.SeatDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import model.Seat;

public class TicketBookingServlet extends HttpServlet {
    private HashMap<String, Boolean> seats = new HashMap<>();
    private SeatDAO seatDAO = new SeatDAO();

    @Override
    public void init() {
        
        for (char row = 'A'; row <= 'K'; row++) {
            for (int col = 1; col <= 12; col++) {
                seats.put(row + String.valueOf(col), false); // false = chưa đặt chỗ
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("user") == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang login luon
            response.sendRedirect("/auth/Login.jsp");
        } else {
            // Nếu đã đăng nhập, xử lí danh sách ghế và chhuyeenr đến trang đặt vé
            List<Seat> seats = seatDAO.getAllSeats(); 
            request.setAttribute("seats", seats); 
            RequestDispatcher dispatcher = request.getRequestDispatcher("/booking/booking.jsp"); 
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String seat = request.getParameter("seat");
        if (seats.containsKey(seat) && !seats.get(seat)) {
            seats.put(seat, true); // Đặt chỗ
            saveSeatToDatabase(seat); 
            response.getWriter().write("Đặt vé thành công cho ghế " + seat);
        } else {
            response.getWriter().write("Ghế đã được đặt hoặc không hợp lệ.");
        }
    }

    private void saveSeatToDatabase(String seat) {
        String[] seatParts = seat.split("(?<=\\D)(?=\\d)"); // Tách tên ghế thành phần chữ và số
        String name = seatParts[0] + seatParts[1]; // Tên ghế
        int coordinateX = seatParts[0].charAt(0) - 'A'; // Tính tọa độ X
        int coordinateY = Integer.parseInt(seatParts[1]) - 1; // Tính tọa độ Y

        seatDAO.addSeat(1, name, coordinateX, coordinateY); 
    }
}