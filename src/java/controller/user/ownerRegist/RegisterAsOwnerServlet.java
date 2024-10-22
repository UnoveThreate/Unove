/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user.ownerRegist;

import DAO.owner.request.OwnerRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import util.RouterURL;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author Kaan
 */
@WebServlet("/registerAsOwner")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
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

    // Handle GET requests (for displaying the registration form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the registration form JSP page
        request.getRequestDispatcher("/page/user/OwnerRegist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        // Check if userID is null (user not logged in)
        if (userID == null) {
            // Redirect to login page or show an error
            response.sendRedirect(RouterURL.ERROR_PAGE); // Replace with your login page URL
            return;
        }

        // Retrieve the fields from the form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String cinemaName = request.getParameter("cinemaName");
        String cinemaAddress = request.getParameter("cinemaAddress");
        String businessLicenseNumber = request.getParameter("businessLicenseNumber");

        // Handle file upload for business license
        Part filePart = request.getPart("businessLicenseFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Save the file on the server
        filePart.write(uploadPath + File.separator + fileName);

        // Insert the owner request data into the database
        boolean success = ownerRequestDAO.addOwnerRequest(userID, fullName, email, cinemaName, cinemaAddress, businessLicenseNumber, fileName);

        if (success) {
            // Forward to the success page with a message
            request.setAttribute("message", "Your request is being reviewed.");
            request.getRequestDispatcher("/page/user/ownerRequestSuccess.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to submit owner request.");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }

}
