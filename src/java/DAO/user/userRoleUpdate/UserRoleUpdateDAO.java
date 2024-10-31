/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.user.userRoleUpdate;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Kaan
 */
public class UserRoleUpdateDAO extends MySQLConnect {

    public UserRoleUpdateDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean updateUserRoleToOwner(int userID) {
        String sql = "UPDATE User SET Role = 'owner' WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
