/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.owner;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.cinemaChainOwnerDAO.CinemaChainDAO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CinemaChain;
import util.FileUploader;
import util.Role;
import util.RouterJSP;
import util.RouterURL;

/**
 *
 * @author nguyendacphong
 */
@WebServlet("/owner/updateCinemaChain")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5 MB
        maxFileSize = 1024 * 1024 * 30, // 30 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UpdateCinemaChainServlet extends HttpServlet {

    private CinemaChainDAO cinemaChainDAO;
    private FileUploader fileUploader;
    RouterJSP router = new RouterJSP();

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            cinemaChainDAO = new CinemaChainDAO(getServletContext());
            fileUploader = new FileUploader();
        } catch (Exception ex) {
            Logger.getLogger(UpdateCinemaChainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = (HttpSession) request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        try {
            CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserID(userID);
            if (cinemaChain == null) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }
            request.setAttribute("cinemaChain", cinemaChain);
            request.getRequestDispatcher(router.OWNER_UPDATE_CINEMACHAIN_PAGE).forward(request, response);
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

        if (userID == null || !Role.isRoleValid(role, Role.OWNER)) {
            response.sendRedirect(RouterURL.LOGIN);
            return;
        }

        try {
            // Lấy thông tin hiện tại của CinemaChain
            CinemaChain cinemaChain = cinemaChainDAO.getCinemaChainByUserID(userID);
            if (cinemaChain == null) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }

            // Cập nhật tên và thông tin của CinemaChain
            String name = request.getParameter("name");
            String information = request.getParameter("information");

            // Cập nhật avatar nếu có thay đổi
            String avatarURL = cinemaChain.getAvatarURL();
            Part avatarPart = request.getPart("avatar");

            if (avatarPart != null && avatarPart.getSize() > 0) {
                // Tạo file tạm thời để upload avatar
                File avatarFile = File.createTempFile("avatar_", "_" + avatarPart.getSubmittedFileName());
                avatarPart.write(avatarFile.getAbsolutePath());

                // Upload lên Cloudinary
                avatarURL = fileUploader.uploadAndReturnUrl(
                        avatarFile, "avatar_" + name, "cinema/avatar");
            }

            // Kiểm tra nếu việc upload avatar có thành công hay không
            if (avatarURL.isEmpty()) {
                response.sendRedirect(RouterURL.ERROR_PAGE);
                return;
            }

            // Lưu thông tin cập nhật
            cinemaChain.setName(name);
            cinemaChain.setInformation(information);
            cinemaChain.setAvatarURL(avatarURL);

            cinemaChainDAO.updateCinemaChain(cinemaChain);

            response.sendRedirect(RouterURL.MANAGE_CINEMA);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(RouterURL.ERROR_PAGE);
        }
    }
}
