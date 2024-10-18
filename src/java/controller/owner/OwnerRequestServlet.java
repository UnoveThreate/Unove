/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.owner.request.OwnerRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.RouterURL;
import java.io.IOException;
import util.RouterJSP;

/**
 *
 * @author Kaan
 */
@WebServlet("/ownerRequest")
public class OwnerRequestServlet extends HttpServlet {
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
        int userID = Integer.parseInt(request.getParameter("userID"));
        String reason = request.getParameter("reason");

        if (ownerRequestDAO.addOwnerRequest(userID, reason)) {
            // Redirect to a success page
            response.sendRedirect(RouterURL.LANDING_PAGE);
        } else {
            // Handle failure case
            request.setAttribute("error", "Failed to submit request.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
        }
    }
}
