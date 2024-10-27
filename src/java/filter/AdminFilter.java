//package filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import model.User;
//
//@WebFilter("/admin/*")
//public class AdminFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//        HttpSession session = request.getSession(false);
//
//        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
//        boolean isAdmin = (isLoggedIn && "ADMIN".equals(((User)session.getAttribute("user")).getRole()));
//
//        if (isAdmin) {
//            chain.doFilter(request, response);
//        } else {
//            response.sendRedirect(request.getContextPath() + "/login");
//        }
//    }
//}