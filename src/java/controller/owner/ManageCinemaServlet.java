/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.cinemaChainOwnerDAO.CinemaDAO; // Đảm bảo import đúng DAO của bạn
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cinema; // Import model Cinema

import java.io.IOException;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

@WebServlet("/owner/manageCinema")
public class ManageCinemaServlet extends HttpServlet {

    private CinemaDAO cinemaDAO;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        try {
            cinemaDAO = new CinemaDAO(context);
        } catch (Exception e) {
            throw new ServletException("Failed to initialize CinemaDAO", e);
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

        // Get cinemaID from the request
        String cinemaIDStr = request.getParameter("cinemaID");
        if (cinemaIDStr == null || cinemaIDStr.isEmpty()) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not provided
            return;
        }

        try {
            Integer cinemaID = Integer.parseInt(cinemaIDStr); // Parse cinemaID to Integer
            Cinema cinema = cinemaDAO.getCinemaById(cinemaID); // Get cinema details by ID
            
            if (cinema == null) {
                response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinema not found
                return;
            }

            request.setAttribute("cinema", cinema); // Set cinema data to request
            request.getRequestDispatcher(RouterJSP.OWNER_MANAGE_CINEMA_PAGE).forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(RouterURL.MANAGE_CINEMA); // Redirect if cinemaID is not a valid integer
        } catch (Exception e) {
            throw new ServletException("Error retrieving cinema information for ID: " + cinemaIDStr, e);
        }
    }
}
