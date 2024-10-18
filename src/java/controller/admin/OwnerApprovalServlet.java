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
        // Fetch pending requests
        List<OwnerRequest> pendingRequests = ownerRequestDAO.getPendingRequests();
        request.setAttribute("pendingRequests", pendingRequests);
        request.getRequestDispatcher(RouterJSP.OWNER_APPROVAL_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        String status = request.getParameter("status");
        String reason = request.getParameter("reason");

        if (ownerRequestDAO.updateRequestStatus(requestID, status, reason)) {
            
            if ("approved".equals(status)) {
                // Get UserID from the request to update role
                int userID = ownerRequestDAO.getUserIDByRequestID(requestID);
                userRoleUpdateDAO.updateUserRoleToOwner(userID);
            }
            
            response.sendRedirect(RouterURL.ADMIN_PAGE);
            
        } else {
            request.setAttribute("error", "Failed to update request status.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
        }
    }
}