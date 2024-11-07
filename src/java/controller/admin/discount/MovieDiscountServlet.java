/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.discount;

import DAO.admin.movieDiscount.MovieDiscountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.discount.MovieDiscount;
import util.RouterJSP;
import util.RouterURL;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import util.Role;

/**
 *
 * @author Kaan
 */
@WebServlet("/admin/discount/movieDiscount")
public class MovieDiscountServlet extends HttpServlet {

    private MovieDiscountDAO movieDiscountDAO;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            movieDiscountDAO = new MovieDiscountDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAO", e);
        }
    }

    private boolean isAdmin(Integer userID, String role, HttpServletResponse response) throws IOException {
        if (userID == null || !Role.isRoleValid(role, Role.ADMIN)) {
            response.sendRedirect(RouterURL.LOGIN);
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (!isAdmin(userID, role, response)) {
            return;
        }

        try {
            // Fetch all discounts and update status for expired ones
            List<MovieDiscount> discounts = movieDiscountDAO.getAllDiscounts();
            for (MovieDiscount discount : discounts) {
                if (discount.isExpired()) {
                    discount.setStatus("inactive");
                    movieDiscountDAO.updateDiscountStatus(discount); // update status in database
                }
            }
            request.setAttribute("discounts", discounts);
            request.getRequestDispatcher(RouterJSP.MOVIE_DISCOUNT_PAGE).forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Failed to retrieve discounts", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        // Admin role validation
        if (!isAdmin(userID, role, response)) {
            return;
        }

        try {
            String discountCode = request.getParameter("discountCode");
            BigDecimal discountPercentage = new BigDecimal(request.getParameter("discountPercentage"));
            Date startDate = Date.valueOf(request.getParameter("startDate"));
            Date endDate = Date.valueOf(request.getParameter("endDate"));
            String status = request.getParameter("status");

            if (!movieDiscountDAO.isDiscountCodeUnique(discountCode)) {
                request.setAttribute("error", "Discount code already exists. Please choose another one.");
                request.getRequestDispatcher(RouterJSP.MOVIE_DISCOUNT_PAGE).forward(request, response);
                return;
            }
// Continue to add the discount if the code is unique

            // Discount code validation
            if (!discountCode.matches("^[A-Z0-9]{10,20}$")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid discount code format. It must be alphanumeric and between 10 and 20 characters.");
                return;
            }

            // Discount percentage validation
            if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(new BigDecimal("100")) > 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Discount percentage must be between 0 and 100.");
                return;
            }

            // Create a new MovieDiscount object
            MovieDiscount newDiscount = new MovieDiscount(discountCode, discountPercentage, startDate, endDate, status);

            // Insert discount into database and check success
            if (movieDiscountDAO.createDiscount(newDiscount)) {
                response.sendRedirect(RouterURL.MOVIE_DISCOUNT_PAGE);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to create discount. Please check the movie ID or other parameters.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid movie ID or discount percentage format.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
        }
    }

}
