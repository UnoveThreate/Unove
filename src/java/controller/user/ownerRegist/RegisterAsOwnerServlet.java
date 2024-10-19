/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.ownerRegist;

import DAO.owner.request.OwnerRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.RouterURL;
import java.io.IOException;

/**
 *
 * @author Kaan
 */
@WebServlet("/registerAsOwner")
public class RegisterAsOwnerServlet extends HttpServlet {
    private OwnerRequestDAO ownerRequestDAO;

    @Override
    public void init() throws ServletException {
        try {
            ownerRequestDAO = new OwnerRequestDAO(getServletContext());
        } catch (Exception e) {
            throw new ServletException("Failed to initialize OwnerRequestDAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID")); // Get userID from session or request
        String reason = request.getParameter("reason");

        if (ownerRequestDAO.addOwnerRequest(userID, reason)) {
            response.sendRedirect(RouterURL.DISPLAY_PROFILE); // Redirect to user's profile page after successful registration
        } else {
            request.setAttribute("error", "Failed to register as an owner.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response); // Forward to an error page
        }
    }
}
