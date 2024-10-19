/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import DAO.owner.request.OwnerRequestDAO;
import DAO.UserRoleUpdateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.RouterURL;
import util.RouterJSP;
import java.io.IOException;
import java.util.List;
import model.OwnerRequest;
import model.User;

/**
 *
 * @author Kaan
 */
@WebServlet("/ownerApproval")
public class OwnerApprovalServlet extends HttpServlet {

    private OwnerRequestDAO ownerRequestDAO;
    private UserRoleUpdateDAO userRoleUpdateDAO;

    @Override
    public void init() throws ServletException {
        super.init(); // Call to parent init method

        try {
            ownerRequestDAO = new OwnerRequestDAO(getServletContext());
            userRoleUpdateDAO = new UserRoleUpdateDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Check if user is admin
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + RouterURL.LOGIN);
            return;
        }

        // Fetch pending requests
        List<OwnerRequest> pendingRequests = ownerRequestDAO.getPendingRequests();
        request.setAttribute("pendingRequests", pendingRequests);

        // Set attribute if no requests found
        if (pendingRequests == null || pendingRequests.isEmpty()) {
            request.setAttribute("noRequests", true);
        }

        request.getRequestDispatcher(RouterJSP.OWNER_APPROVAL_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        // Check if user is admin
        if (currentUser == null || !"admin".equals(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + RouterURL.LOGIN);
            return;
        }

        try {
            int requestID = Integer.parseInt(request.getParameter("requestID"));
            String status = request.getParameter("status");
            String reason = request.getParameter("reason");

            // Validate status
            if (!"approved".equals(status) && !"rejected".equals(status)) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }

            if (ownerRequestDAO.updateRequestStatus(requestID, status, reason)) {
                if ("approved".equals(status)) {
                    // Get UserID from request to update role
                    int userID = ownerRequestDAO.getUserIDByRequestID(requestID);
                    userRoleUpdateDAO.updateUserRoleToOwner(userID);
                }
                response.sendRedirect(RouterURL.ADMIN_PAGE);
            } else {
                request.setAttribute("error", "Failed to update request status.");
                request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid request ID format.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
        }
    }
}