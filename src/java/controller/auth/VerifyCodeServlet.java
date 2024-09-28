package controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import service.UserServiceImpl;
import service.UserServiceInteface;
import util.RouterJSP;
import util.RouterURL;

@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/verifycode"})
public class VerifyCodeServlet extends HttpServlet {

    RouterJSP routeJSP = new RouterJSP();
    RouterURL routeURL = new RouterURL();

    UserServiceInteface userService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Initialize the UserService instance
            this.userService = new UserServiceImpl(getServletContext());;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Boolean timeExpired = (Boolean) session.getAttribute("timeExpired");
        User user = (User) session.getAttribute("account");
        if (timeExpired == null || !timeExpired) {
            session.setAttribute("startTime", Instant.now());
            session.setAttribute("attempts", 0);
            session.setAttribute("timeExpired", false);
        }
        if (user == null) {
            response.sendRedirect("/Unove/register");
            return;
        }
        request.getRequestDispatcher(new RouterJSP().VERIFY).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            handleRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(VerifyCodeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");

        if (user == null) {
            handleUserNotFound(request, response);
            return;
        }

        String code = request.getParameter("authcode");
        Instant startTime = getStartTime(session);
        int attempts = getAttempts(session);

        if (isTimeout(startTime)) {
            handleTimeout(request, response, user, session);
            return;
        }

        if (isCodeValid(code, user)) {
            handleSuccess(request, response, user, session);
        } else {
            handleInvalidCode(request, response, user, session, attempts);
        }
    }

    private void handleUserNotFound(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("error", "Tài khoản đăng ký không thành công. Vui lòng đăng ký lại.");
        //request.getRequestDispatcher(routeJSP.REGISTER).forward(request, response);
        response.sendRedirect(routeURL.REGISTER);
    }

    private Instant getStartTime(HttpSession session) {
        Instant startTime = (Instant) session.getAttribute("startTime");
        if (startTime == null) {
            startTime = Instant.now();
            session.setAttribute("startTime", startTime);
        }
        return startTime;
    }

    private int getAttempts(HttpSession session) {
        Integer attempts = (Integer) session.getAttribute("attempts");
        if (attempts == null) {
            attempts = 0;
            session.setAttribute("attempts", attempts);
        }
        return attempts;
    }

    private boolean isTimeout(Instant startTime) {
        Instant endTime = startTime.plusSeconds(60);
        return Instant.now().isAfter(endTime);
    }

    private void handleTimeout(HttpServletRequest request, HttpServletResponse response, User user, HttpSession session)
            throws ServletException, IOException {
        request.setAttribute("error", "Thời gian nhập mã kích hoạt đã hết.");
        user.setStatus(0);
        userService.updatestatus(user);

        session.setAttribute("timeExpired", true);
        session.invalidate();

        request.getRequestDispatcher(routeJSP.REGISTER).forward(request, response);
    }

    private boolean isCodeValid(String code, User user) {
        return code.equals(user.getCode());
    }

    private void handleSuccess(HttpServletRequest request, HttpServletResponse response, User user, HttpSession session)
            throws ServletException, IOException {

        user.setStatus(1);
        userService.updatestatus(user);
        session.invalidate();

        request.setAttribute("message", "Kích hoạt tài khoản thành công!");
        request.getRequestDispatcher(routeJSP.LOGIN).forward(request, response);
    }

    private void handleInvalidCode(HttpServletRequest request, HttpServletResponse response, User user, HttpSession session, int attempts)
            throws ServletException, IOException {
        int maxTimeTry = 3;

        if (attempts < maxTimeTry - 1) {
            attempts++;
            session.setAttribute("attempts", attempts);
        } else {
            lockAccount(user, request, response);
            return;
        }

        int remain = maxTimeTry - attempts;
        if (remain > 0) {
            request.setAttribute("error", "Sai mã kích hoạt, vui lòng kiểm tra lại(còn " + remain + " lần)");
            request.getRequestDispatcher(routeJSP.VERIFY).forward(request, response);
        } else {

            request.setAttribute("error", "Nhập quá 3 lần, vui lòng đăng ký lại");
            request.getRequestDispatcher(routeJSP.VERIFY).forward(request, response);
            response.sendRedirect(routeURL.REGISTER);

        }

    }   

    private void lockAccount(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        user.setStatus(0);
        userService.updatestatus(user);
        request.setAttribute("error", "Bạn đã nhập sai quá 3 lần. Tài khoản của bạn đã bị khóa.");
        request.getRequestDispatcher(routeJSP.REGISTER).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}