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

/**
 *
 * @author Kaan
 */
@WebServlet("/admin/ownerApproval")
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
        String roleUser = (String) session.getAttribute("role");

        // Fetch pending requests with additional user information
        List<OwnerRequest> pendingRequests = ownerRequestDAO.getPendingRequests();

        // Set pending requests as an attribute
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

        try {
            int requestID = Integer.parseInt(request.getParameter("requestID"));
            String status = request.getParameter("status");
            String reason = request.getParameter("reason");

            // Validate status
            if (!"approved".equals(status) && !"rejected".equals(status)) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }

            // Update request status in the database
            if (ownerRequestDAO.updateRequestStatus(requestID, status, reason)) {
                if ("approved".equals(status)) {
                    int userID = ownerRequestDAO.getUserIDByRequestID(requestID);
                    userRoleUpdateDAO.updateUserRoleToOwner(userID);
                }

                // For AJAX requests, return success response
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Handle error
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to update request status.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request ID format.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }
}