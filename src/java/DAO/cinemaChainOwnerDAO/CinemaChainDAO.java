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
        return new CinemaChain(rs.getInt("CinemaChainID"), rs.getString("Name"), rs.getString("Information"), rs.getInt("UserID"));
    }
    return null;
}


    public boolean createCinemaChain(CinemaChain cinemaChain) throws SQLException {
        String sql = "INSERT INTO CinemaChain (UserID, Name, Information, AvatarURL) VALUES (?, ?, ?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, cinemaChain.getUserId());
        stmt.setString(2, cinemaChain.getName());
        stmt.setString(3, cinemaChain.getInformation());
        stmt.setString(4, cinemaChain.getInformation());
        return stmt.executeUpdate() > 0;
    }

}
