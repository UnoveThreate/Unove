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
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.FileUploader;

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
            fileUploader = new FileUploader(); // Initialize the FileUploader
        } catch (Exception e) {
            throw new ServletException("Failed to initialize DAO or FileUploader", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID"); // Use Integer to check for null

        // Check if userID is null
        if (userID == null) {
            // Redirect to login page or show an error
            request.setAttribute("error", "You must be logged in to access this page.");
            request.getRequestDispatcher("/page/user/Login.jsp").forward(request, response);
            return; // Stop further processing
        }

        // Fetch user details using UserDAO
        User user = null;
        try {
            user = userDAO.getUserById(userID);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAsOwnerServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "Error retrieving user details.");
            request.getRequestDispatcher("/page/user/OwnerRegist.jsp").forward(request, response);
            return; // Stop further processing
        }

        if (user != null) {
            request.setAttribute("fullName", user.getFullName());
            request.setAttribute("email", user.getEmail());
        } else {
            request.setAttribute("error", "User details not found.");
        }

        request.getRequestDispatcher("/page/user/OwnerRegist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userID = (int) session.getAttribute("userID");

        // Get Tax ID from form data
        String taxID = request.getParameter("businessLicenseNumber");

        // Validate Tax ID format
        if (!isValidTaxID(taxID)) {
            request.setAttribute("error", "Invalid tax ID format.");
            request.getRequestDispatcher("/page/user/OwnerRegist.jsp").forward(request, response);
            return;
        }

        // Handle file upload for Giấy Phép Kinh Doanh
        Part filePart = request.getPart("businessLicenseFile");
        String imageUrl = uploadFile(filePart); // Use FileUploader

        // Insert owner request data into the database via OwnerRequestDAO
        boolean success = ownerRequestDAO.addOwnerRequest(userID, taxID, imageUrl);

        // Forward based on success or failure of the request submission
        if (success) {
            request.setAttribute("message", "Your request has been submitted and is pending approval.");
        } else {
            request.setAttribute("error", "Failed to submit request. Please try again.");
        }
        request.getRequestDispatcher("/page/user/OwnerRegist.jsp").forward(request, response);
    }

    private boolean isValidTaxID(String taxID) {
        return taxID.matches("\\d{10}|\\d{13}");
    }

    private String uploadFile(Part filePart) throws IOException {
        String imageURL = "";
        if (filePart != null && filePart.getSize() > 0) {
            // Create a temporary file to store the uploaded image
            File tempFile = File.createTempFile("business_license_", "_" + filePart.getSubmittedFileName());
            filePart.write(tempFile.getAbsolutePath());

            // Use FileUploader to upload the image and get the URL
            imageURL = fileUploader.uploadAndReturnUrl(tempFile, "business_license_" + System.currentTimeMillis(), "business_license");

            // Clean up temporary file
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
        return imageURL;
    }
}
