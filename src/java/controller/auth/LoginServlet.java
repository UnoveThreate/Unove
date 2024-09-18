package controller.auth;

import DAO.UserDAO;
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
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    RouterJSP route = new RouterJSP();
    UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            this.userDAO = new UserDAO(getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(route.LOGIN).forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String username_email = request.getParameter("username-email");
    String password = request.getParameter("password");

    if (username_email == null || password == null) {
        request.setAttribute("error", "Bạn chưa nhập tên đăng nhập hoặc mật khẩu");
        request.getRequestDispatcher(route.LOGIN).forward(request, response);
        return;
    }

    boolean ok = false;
    User user = null;

    try {
        // Check login
        ok = userDAO.checkLogin(username_email, password);
        
        if (ok) {
            try {
                user = userDAO.getUserById(username_email); // Update this if needed to get user info

                if (user == null) {
                    response.sendRedirect(RouterURL.LOGIN);
                    return;
                }

                HttpSession session = request.getSession();
                session.setAttribute("userID", user.getUserID());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("email", user.getEmail());

                // Redirect to a default page after successful login
                response.sendRedirect(RouterURL.HOMEPAGE);

            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác");
            request.getRequestDispatcher(route.LOGIN).forward(request, response);
        }
    } catch (SQLException ex) {
        Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        request.setAttribute("error", "Có lỗi xảy ra trong quá trình đăng nhập");
        request.getRequestDispatcher(route.LOGIN).forward(request, response);
    }
}


    @Override
    public String getServletInfo() {
        return "LoginServlet handles user login functionality.";
    }
}
