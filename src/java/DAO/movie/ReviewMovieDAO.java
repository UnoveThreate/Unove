/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.movie;

import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class ReviewMovieDAO extends UserDAO {
    
    public ReviewMovieDAO(ServletContext context) throws Exception {
        super(context);
    }
    
    public boolean canReview(int userID, int movieID) throws SQLException {
        String sqlQuery = "select distinct MovieSlot.MovieID from [Order]\n" +
                            "join Ticket on [Order].OrderID = Ticket.OrderID\n" +
                            "join MovieSlot on Ticket.MovieSlotID = MovieSlot.MovieSlotID\n" +
                            "where [Order].UserID = ? and MovieSlot.MovieID = ? and Ticket.Status = 'CHECKED-IN'";
        PreparedStatement ps = connection.prepareStatement(sqlQuery); 
        ps.setInt(1, userID);
        ps.setInt(2, movieID);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    public int insertReview(int userID, int movieID, int rating, String timeCreated, String content) throws SQLException {
        String sql = "insert into Review (UserID, movieID, Rating, TimeCreated, Content) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ps.setInt(2, movieID);
        ps.setInt(3, rating);
        ps.setString(4, timeCreated);
        ps.setString(5, content);
        return ps.executeUpdate();
    }
}
