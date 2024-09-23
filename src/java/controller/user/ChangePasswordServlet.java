/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import DAO.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author FPTSHOP
 */
@WebServlet("/change")
public class ChangePasswordServlet extends HttpServlet {

    UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            userDAO = new UserDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RouterJSP router = new RouterJSP();

        // lay du lieu tu Request : 
        String currentPass = request.getParameter("current-password");
        String newPass = request.getParameter("new-password");

        // lay userid tu session : 
//        String id = "3";
        HttpSession session = request.getSession();

        // lay userID tu session : 
        String username = (String) session.getAttribute("username");

        User user = null;
        try {

            user = userDAO.getUserByUsername(username);
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // check pass co dung khong , neu dung thi update password :
         String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(currentPass);
        if (user.getPassword().equalsIgnoreCase(hash)) {
            try {
                userDAO.updateUserPasswordByID(username, newPass);
            } catch (SQLException ex) {
                Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // chuyen qua cho thang display  ra thong tin user : (chuyen sang cho Servlet truoc : )
        request.getRequestDispatcher("/user/information").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
