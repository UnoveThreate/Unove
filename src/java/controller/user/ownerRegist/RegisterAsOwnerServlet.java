/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.ownerRegist;

import DAO.UserDAO;
import DAO.owner.request.OwnerRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.FileUploader;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author Kaan
 */
@WebServlet("/registerAsOwner")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class RegisterAsOwnerServlet extends HttpServlet {

    private OwnerRequestDAO ownerRequestDAO;
    private UserDAO userDAO;
    private FileUploader fileUploader;

    @Override
    public void init() throws ServletException {
        try {
            ownerRequestDAO = new OwnerRequestDAO(getServletContext());
            userDAO = new UserDAO(getServletContext());
            fileUploader = new FileUploader();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAO or FileUploader", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (userID == null || role == null) {
            response.sendRedirect(RouterURL.LOGIN); // Redirect to login if user is not logged in
            return;
        }

        try {
            // Retrieve user details using userID
            User user = userDAO.getUserById(userID);
            if (user != null) {
                request.setAttribute("fullName", user.getFullName());
                request.setAttribute("email", user.getEmail());
            } else {
                request.setAttribute("error", "User details not found.");
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterAsOwnerServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Error retrieving user details.");
        }

        request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        try {
            // Check if the user has a pending or approved request
            String existingRequestStatus = ownerRequestDAO.getRequestStatus(userID);
            if ("pending".equals(existingRequestStatus)) {
                request.setAttribute("error", "Your request is currently under review; you cannot reapply.");
                request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
                return;
            } else if ("approved".equals(existingRequestStatus)) {
                request.setAttribute("error", "You are already an owner.");
                request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
                return;
            }

            // Validate Tax ID format
            String taxID = request.getParameter("taxNumber");
            if (!isValidTaxID(taxID)) {
                request.setAttribute("error", "Invalid tax ID format.");
                request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
                return;
            }

            // Handle file upload
            Part filePart = request.getPart("businessLicenseFile");
            String businessLicenseFile = uploadFile(filePart);

            // Add owner request to the database
            boolean success = ownerRequestDAO.addOwnerRequest(userID, taxID, businessLicenseFile);

            if (!success) {
                request.setAttribute("error", "Failed to submit request. Please try again.");
            } else {
                request.setAttribute("successMessage", "Your request has been submitted successfully.");
            }

            request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);

        } catch (Exception e) {
            Logger.getLogger(RegisterAsOwnerServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("error", "An error occurred while processing your request.");
            request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
        }
    }

    private boolean isValidTaxID(String taxID) {
        return taxID.matches("\\d{10}|\\d{13}");
    }

    private String uploadFile(Part filePart) throws IOException {
        String imageURL = "";
        if (filePart != null && filePart.getSize() > 0) {
            File tempFile = File.createTempFile("business_license_", "_" + filePart.getSubmittedFileName());
            filePart.write(tempFile.getAbsolutePath());

            imageURL = fileUploader.uploadAndReturnUrl(tempFile, "business_license_" + System.currentTimeMillis(), "business_license");

            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
        return imageURL;
    }
}
