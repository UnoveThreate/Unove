///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controller.auth;
//
//
//import DAO.UserDAO;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import model.User;
//import model.UserGoogleDto;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.fluent.Request;
//import org.apache.http.client.fluent.Form;
//import util.GoogleOAuthConstants;
//import util.RouterJSP;
//import util.RouterURL;
//
///**
// *
// * @author duyqu
// */
//@WebServlet(name = "LoginGoogleServlet", urlPatterns = {"/LoginGoogleServlet"})
//public class LoginGoogleServlet extends HttpServlet {
//
//    RouterJSP route = new RouterJSP();
//    UserDAO userDAO;
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    public void init() throws ServletException {
//        try {
//            super.init();
//            this.userDAO = new UserDAO(getServletContext());
//        } catch (Exception ex) {
//            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
////        System.out.println("LoginGoogle");
//        String code = request.getParameter("code");
//        String accessToken = getToken(code);
//        UserGoogleDto userGoogle = getUserInfo(accessToken);
//        System.out.println(userGoogle);
//        System.out.println(userGoogle.getEmail());
//        String username_email = userGoogle.getEmail();
//        String role = "";
//        HttpSession session = request.getSession();
//        User user;
//        try {
//            user = userDAO.getUser(username_email);
//
//            if (user == null) {
//                request.setAttribute("ok", "Tài khoản google chưa đăng ký");
//                request.getRequestDispatcher(route.LOGIN).forward(request, response);
//                return;
//            }
//
//            System.out.println("user" + user.toString());
//
//            role = user.getRole();
//
//            session.setAttribute("userID", user.getUserID());
//            session.setAttribute("username", user.getUsername());
//            session.setAttribute("email", user.getEmail());
//
//            session.setAttribute("role", role);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        switch (role) {
//            case "USER" -> {
//                //TEMP CODE FOR GETTING CHAINS & Username
//
//                ArrayList<String> cinemaNames = null;
//                try {
//                    CinemaChainDAO cc = new CinemaChainDAO(request.getServletContext());
//                    cinemaNames = cc.getCinemaChainList();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                session.setAttribute("chains", cinemaNames);
//                String redirectTo = (String) session.getAttribute("redirectTo");
//                System.out.println("redirect to: " + redirectTo);
//
//                if (redirectTo == null) {
//                    response.sendRedirect(RouterURL.HOMEPAGE);
//                } else {
//                    // Reconstruct the URL with stored parameters
//                    StringBuilder redirectUrlWithParams = new StringBuilder(redirectTo);
//                    boolean firstParam = true;
//                    Enumeration<String> attributeNames = session.getAttributeNames();
//
//                    while (attributeNames.hasMoreElements()) {
//                        String attributeName = attributeNames.nextElement();
//                        if (attributeName.startsWith("param_")) {
//                            String paramName = attributeName.substring(6);
//                            String paramValue = (String) session.getAttribute(attributeName);
//
//                            if (firstParam) {
//                                redirectUrlWithParams.append("?");
//                                firstParam = false;
//                            } else {
//                                redirectUrlWithParams.append("&");
//                            }
//
//                            redirectUrlWithParams.append(paramName).append("=").append(paramValue);
//                            session.removeAttribute(attributeName);
//                        }
//                    }
//
//                    session.removeAttribute("redirectTo");
//                    response.sendRedirect(redirectUrlWithParams.toString());
//                }
//               
//            }
//            case "STAFF" ->
//                request.getRequestDispatcher(route.STAFF).forward(request, response);
//            case "ADMIN" ->
//                request.getRequestDispatcher("/admin").forward(request, response);
//        }
//    }
//
//    public static String getToken(String code) throws ClientProtocolException, IOException {
//        // call api to get token
//        String response = Request.Post(GoogleOAuthConstants.GOOGLE_LINK_GET_TOKEN)//create post request to google link
//                .bodyForm(Form.form().add("client_id", GoogleOAuthConstants.GOOGLE_CLIENT_ID)
//                        .add("client_secret", GoogleOAuthConstants.GOOGLE_CLIENT_SECRET)
//                        .add("redirect_uri", GoogleOAuthConstants.GOOGLE_REDIRECT_URI).add("code", code)
//                        .add("grant_type", GoogleOAuthConstants.GOOGLE_GRANT_TYPE).build())
//                .execute().returnContent().asString();
//
//        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
//        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
//        return accessToken;
//    }
//
//    public static UserGoogleDto getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
//        String link = GoogleOAuthConstants.GOOGLE_LINK_GET_USER_INFO + accessToken;
//        String response = Request.Get(link).execute().returnContent().asString();
//
//        UserGoogleDto googlePojo = new Gson().fromJson(response, UserGoogleDto.class);
//
//        return googlePojo;
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}