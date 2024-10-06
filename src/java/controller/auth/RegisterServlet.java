/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.UserServiceInteface;
import util.RouterJSP;
import model.User;
import service.EmailService;
import service.UserServiceImpl;
import util.Util;
import util.Validation;

/**
 *
 * @author VINHNQ
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    UserServiceInteface userService;
    RouterJSP route = new RouterJSP();
    Validation validate = new Validation();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Initialize the UserService instance
            this.userService = new UserServiceImpl(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(VerifyCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

        String url = request.getRequestURL().toString();

        getRegister(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {        
                postRegister(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void getRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(route.REGISTER).forward(req, resp);
    }

    protected void postRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set character encoding and content type
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");

        // Regular expression pattern for password validation
        String alertMsg = "";

        // Password validation
        if (!validate.isPasswordPattern(password)) {
            alertMsg = "Mật khẩu phải chứa ít nhất một số và một kí tự chữ cái, và có ít nhất 8 kí tự.";
            forwardToRegisterWithError(request, response, alertMsg);
            return;
        }

        // Confirm password validation
        if (!password.equals(confirmPassword)) {
            alertMsg = "Mật khẩu và xác nhận mật khẩu không khớp!";
            forwardToRegisterWithError(request, response, alertMsg);
            return;
        }

        // Check if email exists
        if (userService.checkExistEmail(email)) {
            alertMsg = "Email đã tồn tại!";
            forwardToRegisterWithError(request, response, alertMsg);
            return;
        }

        // Check if username exists
        if (userService.checkExistUsername(username)) {
            alertMsg = "Tài khoản đã tồn tại!";
            forwardToRegisterWithError(request, response, alertMsg);
            return;
        }

        // Send verification email
        EmailService sm = new EmailService();
        String code = Util.getRanDom();

        User user = new User(fullName, username, email, code);
        System.out.println("email" + email + " ,code:" + code);
        boolean isSuccessSendMail = sm.sendEmail(email, code);

        // Handle email sending failure
        if (!isSuccessSendMail) {
            alertMsg = "Lỗi khi gửi mail!";
            forwardToRegisterWithError(request, response, alertMsg);
            return;
        }

        // Store user in session and register user
        HttpSession session = request.getSession();
        session.setAttribute("account", user);
        boolean isSuccess = userService.register(fullName, username, email, password, code);

        // Redirect or display error message based on registration success
        if (isSuccess) {
            response.sendRedirect(request.getContextPath() + "/verifycode");
        } else {
            alertMsg = "Lỗi hệ thống!";
            forwardToRegisterWithError(request, response, alertMsg);
        }
    }

// Helper method to forward to register page with error message
    private void forwardToRegisterWithError(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws ServletException, IOException {
        request.setAttribute("error", errorMsg);
        request.getRequestDispatcher(route.REGISTER).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}