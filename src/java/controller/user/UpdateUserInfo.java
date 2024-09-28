package controller.user;

import javax.servlet.ServletContext;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import DAO.UserDAO;
import javax.servlet.annotation.WebServlet;
import util.RouterURL;
/**
 *
 * @author FPTSHOP
 */
@WebServlet("/update")
public class UpdateUserInfo extends HttpServlet {
    UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init(); 
        try {
            userDAO= new UserDAO((jakarta.servlet.ServletContext) getServletContext());
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String fullname = request.getParameter("fullname");
        String birthdayStr = request.getParameter("birthday");
        String address = request.getParameter("address");
        String province = request.getParameter("provinceName");
        String district = request.getParameter("districtName");
        String commune = request.getParameter("communeName");
        String avatarUrl = request.getParameter("avatarUrl");

        User user = new User();
        
        user.setUserID(Integer.parseInt(userId)); // 
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullName(fullname);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = dateFormat.parse(birthdayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setBirthday(birthday);

        user.setAddress(address);
        user.setProvince(province);
        user.setDistrict(district);
        user.setCommune(commune);
        user.setAvatarLink(avatarUrl);

        // tao Context Servlet : 
        ServletContext context = getServletContext();

        // update user : 
        try {
            userDAO.updateUser(user);
        } catch (Exception ex) {
            Logger.getLogger(UpdateUserInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        // chuyen qua cho thang display thong tin user : 
        response.sendRedirect(RouterURL.DISPLAY_PROFILE);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
