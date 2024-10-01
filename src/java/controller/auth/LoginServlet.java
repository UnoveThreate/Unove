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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.RouterJSP;
import util.RouterURL;
import java.sql.ResultSet;
import java.util.Enumeration;

/**
 *
 * @author ACER
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
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
        request.getRequestDispatcher(route.LOGIN).forward(request, response);
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

        String username_email = request.getParameter("username-email");
        String password = request.getParameter("password");
        if (username_email == null || password == null) {
            request.setAttribute("ok", "bạn chưa nhâp tên đăng nhập hoặc password");
            request.getRequestDispatcher(route.LOGIN).forward(request, response);
            return;
        }
        String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);

        Boolean ok = null;
        User user;
        String role = "";
        ResultSet rs;

        try {
            rs = userDAO.checkLogin(username_email, hash);
            ok = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpSession session = request.getSession();
        if (ok) {

            try {
                user = userDAO.getUser(username_email);

                if (user == null) {
                    response.sendRedirect(RouterURL.LOGIN);
                    return;
                }

                System.out.println("user" + user.toString());

                role = user.getRole();

                session.setAttribute("userID", user.getUserID());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("role", role);

                System.out.println("role after login: " + session.getAttribute("role"));

            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (role) {
                case "USER" -> {
                    //TEMP CODE FOR GETTING CHAINS & Username

                    // Retrieve the originally requested URL
                    String redirectTo = (String) session.getAttribute("redirectTo");
                    System.out.println("redirect to: " + redirectTo);
                    
                    System.out.print("USSSSSSERRRR");

                    if (redirectTo == null) {
                        response.sendRedirect(RouterURL.LANDING_PAGE);
                    } else {
                        // Reconstruct the URL with stored parameters
                        StringBuilder redirectUrlWithParams = new StringBuilder(redirectTo);
                        boolean firstParam = true;
                        Enumeration<String> attributeNames = session.getAttributeNames();

                        while (attributeNames.hasMoreElements()) {
                            String attributeName = attributeNames.nextElement();
                            if (attributeName.startsWith("param_")) {
                                String paramName = attributeName.substring(6);
                                String paramValue = (String) session.getAttribute(attributeName);

                                if (firstParam) {
                                    redirectUrlWithParams.append("?");
                                    firstParam = false;
                                } else {
                                    redirectUrlWithParams.append("&");
                                }

                                redirectUrlWithParams.append(paramName).append("=").append(paramValue);
                                session.removeAttribute(attributeName);
                            }
                        }

                        session.removeAttribute("redirectTo");
                        response.sendRedirect(redirectUrlWithParams.toString());
                    }

                }
                case "OWNER" ->
                   
                {  
                   System.out.print("OWNER LOGINED");
                   response.sendRedirect(RouterURL.OWNER_PAGE);
                
                }
                  
                case "ADMIN" ->
                    request.getRequestDispatcher("/admin").forward(request, response);
            }

        } else {
            request.setAttribute("ok", ok);
            request.getRequestDispatcher(RouterJSP.LOGIN).forward(request, response);
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
