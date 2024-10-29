/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.owner.request.OwnerRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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
@MultipartConfig
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
        request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");       
        String taxNumber = request.getParameter("taxNumber");

        // Handle file upload
        Part filePart = request.getPart("businessLicenseFile");
        String fileName = filePart.getSubmittedFileName();
        String businessLicenseFile = "uploads/" + fileName;
        filePart.write(getServletContext().getRealPath("/") + businessLicenseFile);

        // Add owner request to the database
        boolean isAdded = ownerRequestDAO.addOwnerRequest(currentUser, taxNumber, businessLicenseFile);

        if (isAdded) {
            // Display a success message in a green box with a return button
            request.setAttribute("message", "<div style='background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px;'>"
                + "Registration successful! <form action='" + RouterURL.DISPLAY_PROFILE + "' method='get'>"
                + "<button type='submit'>Click here to return to your profile</button></form></div>");
        } else {
            request.setAttribute("error", "Failed to submit request.");
        }

        request.getRequestDispatcher(RouterJSP.OWNER_REGIST_PAGE).forward(request, response);
    }
}
