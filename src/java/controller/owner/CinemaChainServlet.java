/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.CinemaChainDAO;
import DAO.CinemaDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.MultipartConfig;
import model.CinemaChain;
import model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cinema;
import util.FileUploader;
import util.Role;
import util.RouterJSP;
import util.Router;
import util.RouterURL;

/**
 *
 * @author nguyendacphong
 */
@WebServlet("/owner")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 30, // 30 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class CinemaChainServlet extends HttpServlet {

    private CinemaChainDAO cinemaChainDAO;
    private CinemaDAO cinemaDAO;
    RouterJSP router = new RouterJSP();
    private FileUploader fileUploader;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            cinemaChainDAO = new CinemaChainDAO((ServletContext) getServletContext());
            fileUploader = new FileUploader();
            cinemaDAO = new CinemaDAO(getServletContext()); // Khởi tạo cinemaDAO
        } catch (Exception ex) {
            Logger.getLogger(CinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            if (cinemaChain == null) {
                request.getRequestDispatcher(router.OWNER_CMC).forward(request, response);
            } else {
                List<Cinema> cinemas = cinemaDAO.getCinemasByCinemaChainID(cinemaChain.getCinemaChainID());
                request.setAttribute("cinemas", cinemas); // Thiết lập danh sách cinemas vào request
                request.setAttribute("cinemaChain", cinemaChain);
                request.getRequestDispatcher(router.OWNER_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = (String) session.getAttribute("role");

        if (userID == null || !Role.isRoleValid(role, role)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        try {
            // Kiểm tra nếu Owner đã có CinemaChain
            CinemaChain existingCinemaChain = cinemaChainDAO.getCinemaChainByUserID(userID);
            if (existingCinemaChain != null) {
                // Nếu đã có CinemaChain, điều hướng tới trang quản lý CinemaChain
                request.setAttribute("cinemaChain", existingCinemaChain);
                request.getRequestDispatcher(router.OWNER_PAGE).forward(request, response);
            } else {
                // Nếu chưa có, xử lý việc tạo mới
                String name = request.getParameter("name");
                String information = request.getParameter("information");
                // Xử lý thêm việc upload ảnh lên cloudinay trước rồi insert vào database
                //
                String avatarURL = "";
                // Handle avatar upload
                Part avatarPart = request.getPart("avatar");

                if (avatarPart != null && avatarPart.getSize() > 0) {
                    // Create a temporary file for the uploaded avatar
                    File avatarFile = File.createTempFile("avatar_", "_" + avatarPart.getSubmittedFileName());
                    avatarPart.write(avatarFile.getAbsolutePath());

                    // Upload to Cloudinary
                    avatarURL = fileUploader.uploadAndReturnUrl(
                            avatarFile, "avatar_" + name, "cinema/avatar");

                }

                if (avatarURL.isEmpty()) {
                    response.sendRedirect(RouterURL.ERROR_PAGE);
                    return;
                }

                // Continue with other logic (e.g., save the avatar URL to the database)
                CinemaChain cinemaChain = new CinemaChain();
                cinemaChain.setUserId(userID);
                cinemaChain.setName(name);
                cinemaChain.setInformation(information);
                cinemaChain.setAvatarURL(avatarURL);

                cinemaChainDAO.createCinemaChain(cinemaChain);

                response.sendRedirect(RouterURL.OWNER_PAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }

}
