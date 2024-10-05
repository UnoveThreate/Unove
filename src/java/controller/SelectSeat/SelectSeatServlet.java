package controller.SelectSeat;

import DAOHuy.MovieSlotDAO;
import DAOHuy.SeatDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MovieSlot;
import model.Seat;

import java.io.IOException;
import java.util.List;
import util.RouterJSP;

@WebServlet("/selectSeat")
public class SelectSeatServlet extends HttpServlet {
    private SeatDAO seatDAO;
    private MovieSlotDAO movieSlotDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            this.seatDAO = new SeatDAO(context); 
            this.movieSlotDAO = new MovieSlotDAO(context); 
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAO", e);
        }
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    int movieSlotID;
    try {
        movieSlotID = Integer.parseInt(request.getParameter("movieSlotID"));
    } catch (NumberFormatException e) {
        request.setAttribute("errorMessage", "Suất chiếu không hợp lệ.");
        request.getRequestDispatcher("/error.jsp").forward(request, response);
        return;
    }

    MovieSlot movieSlot = movieSlotDAO.getMovieSlotById(movieSlotID);
    if (movieSlot != null) {
        int roomId = movieSlot.getRoomID();
        List<Seat> seats = seatDAO.getSeatsByRoomId(roomId); // Lấy danh sách ghế
        request.setAttribute("seats", seats); // Truyền danh sách ghế đến JSP
        request.setAttribute("movieSlotID", movieSlotID);
        request.getRequestDispatcher(RouterJSP.SELECT_SEAT).forward(request, response);
    } else {
        request.setAttribute("errorMessage", "Suất chiếu không hợp lệ.");
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
    int movieSlotID;
    try {
        movieSlotID = Integer.parseInt(request.getParameter("movieSlotID"));
    } catch (NumberFormatException e) {
        request.setAttribute("errorMessage", "Suất chiếu không hợp lệ.");
        request.getRequestDispatcher("/error.jsp").forward(request, response);
        return;
    }

    String selectedSeatIDs = request.getParameter("selectedSeatID");

    // đặt ghế ở đây
    if (selectedSeatIDs != null && !selectedSeatIDs.isEmpty()) {
        String[] seatIDs = selectedSeatIDs.split(",");
        for (String seatID : seatIDs) {
            // chưa code logic
        }
    }

   
    response.sendRedirect("success.jsp");
}
}