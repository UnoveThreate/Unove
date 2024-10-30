/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner.seat;

import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import DAO.cinemaChainOwnerDAO.CinemaDAO;
import DAO.cinemaChainOwnerDAO.RoomDAO;
import DAO.cinemaChainOwnerDAO.SeatDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;

import model.Cinema;
import model.owner.Room;
import model.Seat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/manageSeat")
public class ManageSeatServlet extends HttpServlet {

    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;
    private RoomDAO roomDAO;
    private SeatDAO seatDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            cinemaChainDAO = new CinemaChainDAO(context);
            cinemaDAO = new CinemaDAO(context);
            roomDAO = new RoomDAO(context);
            seatDAO = new SeatDAO(context);
        } catch (Exception e) {
            throw new ServletException("Error initializing DAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Check if user is logged in and has the owner role
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }
        request.setCharacterEncoding("UTF-8");  // Cấu hình mã hóa đầu vào
        response.setCharacterEncoding("UTF-8"); // Cấu hình mã hóa đầu ra
        loadCinemas(request);
        request.getRequestDispatcher(RouterJSP.MANAGE_SEAT_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        // Check if user is logged in and has the owner role
        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }
        loadCinemas(request);  // Luôn lấy danh sách cinema

        String cinemaIDParam = request.getParameter("cinemaID");
        String roomIDParam = request.getParameter("roomID");

        if (cinemaIDParam != null && !cinemaIDParam.isEmpty()) {
            int cinemaID = Integer.parseInt(cinemaIDParam);
            try {
                List<Room> rooms = roomDAO.getRoomsByCinemaID(cinemaID);
                request.setAttribute("rooms", rooms);
                request.setAttribute("selectedCinemaID", cinemaID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (roomIDParam != null && !roomIDParam.isEmpty()) {
            int roomID = Integer.parseInt(roomIDParam);
            try {
                List<Seat> seats = seatDAO.getSeatsByRoomID(roomID);
                request.setAttribute("seats", seats);
                request.setAttribute("selectedRoomID", roomID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher(RouterJSP.MANAGE_SEAT_PAGE).forward(request, response);
    }

    private void loadCinemas(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Kiểm tra xem userID có trong session không
        Integer userID = (Integer) session.getAttribute("userID");
        if (userID == null) {
            System.out.println("userID không có trong session");
            return;
        }

        try {
            int cinemaChainID = cinemaChainDAO.getCinemaChainByUserID(userID).getCinemaChainID();
            List<Cinema> cinemas = cinemaDAO.getCinemasByCinemaChainID(cinemaChainID);

            if (cinemas == null || cinemas.isEmpty()) {
                System.out.println("Không có cinema nào trong cinemaChainID: " + cinemaChainID);
            } else {
                System.out.println("Danh sách cinema đã lấy thành công.");
            }

            request.setAttribute("cinemas", cinemas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
