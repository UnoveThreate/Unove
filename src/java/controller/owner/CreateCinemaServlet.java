/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import DAO.CinemaChainDAO;
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
import model.CinemaChain;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author Per
 */
@WebServlet(name = "CreateCinemaServlet", urlPatterns = {"/owner/create/cinema"})
public class CreateCinemaServlet extends HttpServlet {

    private RouterJSP router = new RouterJSP();
    private CinemaChainDAO cinemaChainDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            cinemaChainDAO = new CinemaChainDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(CreateCinemaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateCinemaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCinemaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        HttpSession session = (HttpSession) request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            // note for sendRedirect 
            response.sendRedirect(RouterURL.LOGIN);

            return;
        }
        try {
            CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserID(userID);
            System.out.print("bbbbbb");
            if (cinemaChain == null) {
                // Nếu user chưa có CinemaChain, yêu cầu tạo mới
                System.out.print("ccccccc");
                request.getRequestDispatcher(router.OWNER_CMC).forward(request, response);
            } else {
                // Nếu đã có, chuyển hướng tới trang quản lý
                System.out.print("ddddddd");
                request.setAttribute("cinemaChain", cinemaChain);
                request.getRequestDispatcher(RouterJSP.OWNER_CREATE_CINEMA_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
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
        //handle logic create cinema here

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
