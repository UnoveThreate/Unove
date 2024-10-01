package controller.owner;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.RouterURL;
import util.RouterJSP;

@WebServlet("/owner")
public class OwnerDashBoard extends HttpServlet {

    RouterJSP router = new RouterJSP();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            new CinemaChainDAO((ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(OwnerDashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        // Kiểm tra vai trò của người dùng
        if (role != null && role.equals("OWNER")) {
            // Nếu là OWNER, chuyển hướng tới trang Owner Dashboard
           request.getRequestDispatcher(router.OWNER_PAGE).forward(request, response);
        } else {
            // Nếu không phải OWNER, chuyển hướng tới trang đăng nhập hoặc trang lỗi
            response.sendRedirect(RouterURL.LOGIN);
        }
    }

   
}
