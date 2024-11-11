/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.RouterJSP;
import util.RouterURL;
import util.Role;
import util.Util;
import util.common.FilterPattern;
import util.common.Key;
import util.common.Message;

/**
 *
 * @author ACER
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.userDAO = new UserDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String currentRole = (String) session.getAttribute(Key.ROLE);
        String roleRequire = (String) session.getAttribute(Key.ROLE_REQUIRE);

        if (currentRole == null) {
            request.getRequestDispatcher(RouterJSP.LOGIN).forward(request, response);
            return;
        }

        if (roleRequire != null && !roleRequire.equalsIgnoreCase(currentRole)) {
            session.removeAttribute(Key.ROLE_REQUIRE);
            session.removeAttribute(Key.ROLE);
            request.getRequestDispatcher(RouterJSP.LOGIN).forward(request, response);
            return;
        }

        try {
            FilterPattern.reconstructAndRedirectWithStoredParams(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method for user login.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Retrieve login parameters
            String usernameEmail = request.getParameter("username-email");
            String password = request.getParameter("password");

            // Check if parameters are provided
            if (Util.isNullOrEmpty(usernameEmail) || Util.isNullOrEmpty(password)) {
                setLoginError(request, "Please enter both username/email and password.");
                forwardToLogin(request, response);
                return;
            }

            // Hash password
            String hashedPassword = Util.hashPassword(password);

            // Verify user credentials
            User user = authenticateUser(usernameEmail, hashedPassword);
            if (user == null) {
                setLoginError(request, Message.INVALID_USER_LOGIN_AUTH);
                forwardToLogin(request, response);
                return;
            }

            // Manage session and redirect if needed
            manageSessionAndRedirect(request, response, user);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets a login error message in the request attributes.
     *
     * @param request the HttpServletRequest
     * @param errorText the error message to display
     */
    private void setLoginError(HttpServletRequest request, String errorText) {
        request.setAttribute("ok", errorText);
    }

    /**
     * Forwards the request to the login page.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(RouterJSP.LOGIN).forward(request, response);
    }

    /**
     * Authenticates the user by checking the username/email and hashed
     * password.
     *
     * @param usernameEmail the username or email of the user
     * @param hashedPassword the hashed password
     * @return the User object if authentication is successful, null otherwise
     */
    private User authenticateUser(String usernameEmail, String hashedPassword) {
        try {
            boolean isUserValidLogin = userDAO.checkLogin(usernameEmail, hashedPassword);
            if (isUserValidLogin) {
                return userDAO.getUser(usernameEmail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "Database error during authentication", ex);
        }
        return null;
    }

    /**
     * Manages session attributes and redirects based on user role.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param user the authenticated User object
     * @throws IOException if an I/O error occurs during redirection
     */
    private void manageSessionAndRedirect(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException, Exception {

        HttpSession session = request.getSession();
        String currentRole = user.getRole();
        String previousRole = (String) session.getAttribute("role");

        setUserSessionAttributes(request.getSession(), user);

        boolean isRoleConsistent = previousRole == null || previousRole.equalsIgnoreCase(currentRole);

        //if consistent role user and store url history to redirect-> accept  redirect to history page, 
        boolean isValidRedirectHistoryURL = FilterPattern.isRedirectRequired(request) && isRoleConsistent;

        // Handle redirection if a valid redirect URL exists and roles are consistent
        if (isValidRedirectHistoryURL) {
            FilterPattern.reconstructAndRedirectWithStoredParams(request, response);
            return;
        }

        // Redirect to role-specific default page
        redirectToRolePage(response, currentRole);
    }

    /**
     * Sets session attributes for the authenticated user.
     *
     * @param session the HttpSession to store user information
     * @param user the authenticated User object
     */
    private void setUserSessionAttributes(HttpSession session, User user) {
        session.setAttribute("userID", user.getUserID());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
    }

    /**
     * Redirects the user to a page based on their role.
     *
     * @param response the HttpServletResponse for redirection
     * @param role the user's role
     * @throws IOException if an I/O error occurs during redirection
     */
    private void redirectToRolePage(HttpServletResponse response, String role) throws IOException {
        switch (role) {
            case Role.USER ->
                response.sendRedirect(RouterURL.LANDING_PAGE);
            case Role.OWNER ->
                response.sendRedirect(RouterURL.OWNER_DASHBOARD_PAGE);
            case Role.ADMIN ->
                response.sendRedirect(RouterURL.ADMIN_PAGE);
            default ->
                response.sendRedirect(RouterURL.LOGIN);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
