/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.CinemaChainDAO;
import jakarta.servlet.ServletContext;
import model.CinemaChain;
import model.User;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterJSP;
import util.Router;
import util.RouterURL;
/**
 *
 * @author nguyendacphong
 */
@WebServlet("/owner")
public class CinemaChainServlet extends HttpServlet {

    private CinemaChainDAO cinemaChainDAO;
    RouterJSP router = new RouterJSP();
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            cinemaChainDAO = new CinemaChainDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.print(user);

        if (user == null || !"OWNER".equals(user.getRole())) {
             System.out.print("aaaaaa");
            response.sendRedirect("/login");
            return;
        }
        try {
            CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserID(user.getUserID());
            System.out.print("bbbbbb");
            if (cinemaChain == null) {
                // Nếu user chưa có CinemaChain, yêu cầu tạo mới
                System.out.print("ccccccc");
                request.getRequestDispatcher(router.OWNER_CMC).forward(request, response);
            } else {
                // Nếu đã có, chuyển hướng tới trang quản lý
                System.out.print("ddddddd");
                request.setAttribute("cinemaChain", cinemaChain);
                request.getRequestDispatcher(router.OWNER_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"OWNER".equals(user.getRole())) {
            response.sendRedirect("/login");
            return;
        }

        try {
            // Kiểm tra nếu Owner đã có CinemaChain
            CinemaChain existingCinemaChain = cinemaChainDAO.getCinemaChainByUserID(user.getUserID());
            if (existingCinemaChain != null) {
                // Nếu đã có CinemaChain, điều hướng tới trang quản lý CinemaChain
                request.setAttribute("cinemaChain", existingCinemaChain);
                request.getRequestDispatcher(router.OWNER_PAGE).forward(request, response);
            } else {
                // Nếu chưa có, xử lý việc tạo mới
                String name = request.getParameter("name");
                String information = request.getParameter("information");

                CinemaChain cinemaChain = new CinemaChain(user.getUserID(), name, information);
                cinemaChainDAO.createCinemaChain(cinemaChain);
                response.sendRedirect("/owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}
