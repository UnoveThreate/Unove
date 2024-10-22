/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.owner.request.OwnerRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import model.User;
import util.RouterJSP;
import util.RouterURL;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the JSP form page
        request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle form input and file upload
        int userID = Integer.parseInt(request.getParameter("userID"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String cinemaName = request.getParameter("cinemaName");
        String cinemaAddress = request.getParameter("cinemaAddress");
        String businessLicenseNumber = request.getParameter("businessLicenseNumber");
        String reason = request.getParameter("reason");

        // Handle file upload (assuming it's a PDF)
        Part filePart = request.getPart("businessLicenseFile");
        String fileName = filePart.getSubmittedFileName();
        String businessLicenseFile = "uploads/" + fileName; // Save the file path

        // Store the file in the 'uploads' directory
        filePart.write(getServletContext().getRealPath("/") + businessLicenseFile);

        // Add owner request with additional details
        if (ownerRequestDAO.addOwnerRequest(userID, fullName, email, cinemaName, cinemaAddress, businessLicenseNumber, businessLicenseFile)) {
            // Redirect to a success page
            response.sendRedirect(RouterURL.LANDING_PAGE);
        } else {
            // Handle failure case
            request.setAttribute("error", "Failed to submit request.");
            request.getRequestDispatcher(RouterJSP.ERROR_PAGE).forward(request, response);
        }
    }
}
