package controller.auth;

import DAO.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.EmailService;
import util.RouterJSP;
import util.Util;

/**
 * Servlet for handling forget password functionality.
 */
@WebServlet("/forgetpassword")
public class ForgetPasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RouterJSP route = new RouterJSP();
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            userDAO = new UserDAO(getServletContext());
        } catch (Exception e) {
            Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException("UserDAO initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String backToLogin = request.getParameter("back-to-login");
        if (backToLogin != null) {
            response.sendRedirect(route.LOGIN);
            return;
        }

        String email = request.getParameter("email");
        String OTP = request.getParameter("OTP");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");

        try {
            if (newPassword != null && confirmPassword != null) {
                handlePasswordChange(request, response, email, newPassword, confirmPassword);
            } else if (OTP != null) {
                handleOTPVerification(request, response, email, OTP);
            } else {
                handleEmailCheck(request, response, email);
            }
        } catch (Exception e) {
            Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("error", "An error occurred. Please try again later.");
            request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
        }
    }

    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response, 
                                      String email, String newPassword, String confirmPassword) 
            throws ServletException, IOException {
        boolean isValidPassword = newPassword != null && confirmPassword != null;
        boolean confirmPasswordOk = newPassword.equals(confirmPassword);
        boolean changePasswordOk = false;

        if (isValidPassword && confirmPasswordOk) {
            try {
                boolean updated = userDAO.updateUserPasswordByEmail(email, newPassword);
                changePasswordOk = updated;
            } catch (Exception e) {
                Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        request.setAttribute("email", email);
        request.setAttribute("isValidPassword", isValidPassword);
        request.setAttribute("confirmPasswordOk", confirmPasswordOk);
        request.setAttribute("changePasswordOk", changePasswordOk);
        request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
    }

    private void handleOTPVerification(HttpServletRequest request, HttpServletResponse response, 
                                        String email, String OTP) 
            throws ServletException, IOException {
        boolean verifyOTPOk = false;
        try {
            String userCode = userDAO.getUserCode(email);
            verifyOTPOk = OTP.equals(userCode);
        } catch (Exception e) {
            Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        request.setAttribute("email", email);
        request.setAttribute("verifyOTPOk", verifyOTPOk);
        request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
    }

    private void handleEmailCheck(HttpServletRequest request, HttpServletResponse response, 
                                   String email) 
            throws ServletException, IOException {
        boolean isExistedEmail = false;
        try {
            isExistedEmail = userDAO.checkExistEmail(email);
            if (isExistedEmail) {
                String code = Util.getRanDom();
                boolean updateCodeOk = userDAO.updateUserCode(email, code);

                boolean sendEmailOk = false;
                if (updateCodeOk) {
                    EmailService sendEmail = new EmailService();
                    sendEmailOk = sendEmail.sendEmail(email, code);
                }

                request.setAttribute("sendEmailOk", sendEmailOk);
            }
        } catch (Exception e) {
            Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        request.setAttribute("isExistedEmail", isExistedEmail);
        request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling forget password functionality.";
    }
}
