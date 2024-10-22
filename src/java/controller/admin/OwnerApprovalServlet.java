package controller.admin;

import DAO.owner.request.OwnerRequestDAO;
import DAO.UserRoleUpdateDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OwnerRequest;
import util.RouterJSP;
import util.RouterURL;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Role;

/**
 * Servlet for handling owner approval requests by the admin.
 */
@WebServlet("/admin/ownerApproval")
public class OwnerApprovalServlet extends HttpServlet {

    private OwnerRequestDAO ownerRequestDAO;
    private UserRoleUpdateDAO userRoleUpdateDAO;

    @Override
    public void init() throws ServletException {
        try {
            ownerRequestDAO = new OwnerRequestDAO(getServletContext());
            userRoleUpdateDAO = new UserRoleUpdateDAO(getServletContext());
        } catch (Exception e) {
            Logger.getLogger(OwnerApprovalServlet.class.getName()).log(Level.SEVERE, "Error initializing DAOs", e);
            throw new ServletException(e); // Properly bubble up the exception
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentRole = (String) session.getAttribute("role");

        // Ensure that only admins can access this page
        if (currentRole == null || !Role.isRoleValid(currentRole, "admin")) {
            response.sendRedirect(request.getContextPath() + RouterURL.LOGIN);
            return; // Prevent further execution
        }

        // Fetch pending owner requests
        List<OwnerRequest> pendingRequests = ownerRequestDAO.getPendingRequests();
        request.setAttribute("pendingRequests", pendingRequests);

        // If no requests are found, set an attribute to display an appropriate message in the view
        if (pendingRequests == null || pendingRequests.isEmpty()) {
            request.setAttribute("noRequests", true);
        }

        // Forward the request to the owner approval JSP page
        request.getRequestDispatcher(RouterJSP.OWNER_APPROVAL_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentRole = (String) session.getAttribute("role");

        // Ensure that only admins can perform this action
        if (currentRole == null || !"admin".equals(currentRole)) {
            response.sendRedirect(request.getContextPath() + RouterURL.LOGIN);
            return; // Prevent further execution
        }

        try {
            // Parse the request ID and retrieve the status and reason from the request
            int requestID = Integer.parseInt(request.getParameter("requestID"));
            String status = request.getParameter("status");
            String reason = request.getParameter("reason");

            // Validate the status
            if (!"approved".equals(status) && !"rejected".equals(status)) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }

            // Update the request status in the database
            if (ownerRequestDAO.updateRequestStatus(requestID, status, reason)) {
                if ("approved".equals(status)) {
                    // If approved, update the user's role to owner
                    int userID = ownerRequestDAO.getUserIDByRequestID(requestID);
                    userRoleUpdateDAO.updateUserRoleToOwner(userID);
                }
                // Redirect to the admin page after successful update
                response.sendRedirect(RouterURL.ADMIN_PAGE);

            } else {
                // Handle failure in updating the request status
                request.setAttribute("error", "Failed to update request status.");
                request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);

            }
        } catch (NumberFormatException e) {
            // Handle invalid request ID format
            request.setAttribute("error", "Invalid request ID format.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);

        } catch (ServletException | IOException | IllegalArgumentException e) {
            // Handle general exceptions
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);

        }
    }
}
