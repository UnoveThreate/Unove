package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.RouterJSP;

/**
 *
 * @author FPTSHOP
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    RouterJSP route = new RouterJSP();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // tao hoac lay session co san : 
        HttpSession session = request.getSession();

        session.invalidate();

        // quay ve trang chu : 
        request.getRequestDispatcher(route.USER).forward(request, response);
//        request.getRequestDispatcher("index.html").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}