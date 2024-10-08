/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.cinemaChainOwnerDAO;

import database.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletContext;
import model.CinemaChain;

public class CinemaChainDAO extends MySQLConnect {

    public CinemaChainDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public CinemaChain getCinemaChainByUserID(int userID) throws SQLException {
        String sql = "SELECT * FROM CinemaChain WHERE UserID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new CinemaChain(rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Information"), rs.getInt("UserID"), rs.getString("AvatarURL"));
        }
        return null;
    }

    public boolean createCinemaChain(CinemaChain cinemaChain) throws SQLException {
        String sql = "INSERT INTO CinemaChain (UserID, Name, Information, AvatarURL) VALUES (?, ?, ?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cinemaChain.getUserID());
        stmt.setString(2, cinemaChain.getName());
        stmt.setString(3, cinemaChain.getInformation());
        stmt.setString(4, cinemaChain.getAvatarURL());
        return stmt.executeUpdate() > 0;
    }

    public boolean updateCinemaChain(CinemaChain cinemaChain) throws SQLException {
        String sql = "UPDATE CinemaChain SET Name = ?, Information = ?, AvatarURL = ? WHERE CinemaChainID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cinemaChain.getName());
        stmt.setString(2, cinemaChain.getInformation());

        // Kiểm tra xem có avatar mới không, nếu có thì cập nhật, nếu không thì giữ nguyên
        if (cinemaChain.getAvatarURL() != null && !cinemaChain.getAvatarURL().isEmpty()) {
            stmt.setString(3, cinemaChain.getAvatarURL());
        } else {
            // Nếu không có avatar mới, giữ nguyên avatar cũ từ database
            stmt.setString(3, getCurrentAvatarURL(cinemaChain.getCinemaChainID()));
        }

        stmt.setInt(4, cinemaChain.getCinemaChainID());
        return stmt.executeUpdate() > 0;
    }

// Hàm phụ để lấy avatar hiện tại từ database
    private String getCurrentAvatarURL(int cinemaChainID) throws SQLException {
        String sql = "SELECT AvatarURL FROM CinemaChain WHERE CinemaChainID = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cinemaChainID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("AvatarURL");
        }
        return null; // Trả về null nếu không tìm thấy avatar
    }

}
