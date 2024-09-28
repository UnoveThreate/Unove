/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.EmailService;
import util.RouterJSP;
import util.Util;

/**
 *
 * @author ACER
 */
@WebServlet("/forgetpassword")
public class ForgetPasswordServlet extends HttpServlet {
    RouterJSP route = new RouterJSP();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgetPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgetPasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();       
        System.out.println("\n\n");
        
        String backToLogin = request.getParameter("back-to-login");
        
        if(backToLogin != null) {
            request.getRequestDispatcher(route.LOGIN).forward(request, response);
            return;
        }
        
        String email = request.getParameter("email");
        String OTP = request.getParameter("OTP");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");
        
        UserDAO ud;
        
        System.out.println("newPassword = " + newPassword + ", confirmPassword = " + confirmPassword);
        
        if(newPassword != null && confirmPassword != null) {
            boolean isValidPassword, confirmPasswordOk, changePasswordOk;
            isValidPassword = confirmPasswordOk = changePasswordOk = false;
            isValidPassword = true; // bo sung
            confirmPasswordOk = newPassword.equals(confirmPassword);
            if(isValidPassword && confirmPasswordOk) {
                try {
                    ud = new UserDAO((ServletContext) request.getServletContext());
                    ud.updateUserPasswordByEmail(email, newPassword);
                    changePasswordOk = true;
                } catch (Exception ex) {
                    Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.setAttribute("email", email);
            request.setAttribute("isValidPassword", isValidPassword);
            request.setAttribute("confirmPasswordOk", confirmPasswordOk);
            request.setAttribute("changePasswordOk", changePasswordOk);
            
            System.out.println("isValidPassword = " + isValidPassword + ", confirmPasswordOk = " + confirmPasswordOk + ", changePasswordOk = " + changePasswordOk);
                
            request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
            return;
        }

        if (OTP != null) {
            boolean verifyOTPOk = false;
            try {
                ud = new UserDAO((ServletContext) request.getServletContext());
                if(OTP.equals(ud.getUserCode(email))) verifyOTPOk = true;
                request.setAttribute("email", email);
                request.setAttribute("verifyOTPOk", verifyOTPOk);
                System.out.println("VerifyOTPOK = " + verifyOTPOk);
                
                request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }


        boolean isExistedEmail = false;

        try {
            ud = new UserDAO((ServletContext) request.getServletContext());
//            ud.closeConnection();
            if (ud.checkExistEmail(email)) isExistedEmail = true;
            request.setAttribute("isExistedEmail", isExistedEmail);

            System.out.println("isExistedEmail = " + isExistedEmail);
            if (!isExistedEmail) {
                request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
                return;
            }
            EmailService sendEmail = new EmailService();
            String code = Util.getRanDom();
            
            boolean updateCodeUserOk = ud.updateUserCode(email, code);
            boolean sendEmailOk = false;
            if(updateCodeUserOk) {
                sendEmailOk = sendEmail.sendEmail(email, code);
                if(sendEmailOk) {
                    ud.updateUserCode(email, code);
                    request.setAttribute("email", email);
                }
            }
            
            request.setAttribute("sendEmailOk", sendEmailOk);

            System.out.println("sendEmailOk = " + sendEmailOk);

            request.getRequestDispatcher(route.FORGET_PASSWORD).forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ForgetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
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