/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.owner.request;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import model.OwnerRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kaan
 */
public class OwnerRequestDAO extends MySQLConnect {

    public OwnerRequestDAO(ServletContext context) throws Exception {
        super(); // Call the constructor of MySQLConnect
        connect(context); // Establish the connection
    }

    // Add a new owner request
    public boolean addOwnerRequest(int userID, String reason) {
        String sql = "INSERT INTO OwnerRequest (UserID, Reason, Status) VALUES (?, ?, 'pending')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, reason);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get pending owner requests for admin
    public List<OwnerRequest> getPendingRequests() {
        List<OwnerRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM OwnerRequest WHERE Status = 'pending'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                OwnerRequest request = new OwnerRequest(
                        rs.getInt("RequestID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("RequestDate"),
                        rs.getString("Status"),
                        rs.getString("Reason")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    // Update request status (approve/reject)
    public boolean updateRequestStatus(int requestID, String status, String reason) {
        String sql = "UPDATE OwnerRequest SET Status = ?, Reason = ? WHERE RequestID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setString(2, reason);
            pstmt.setInt(3, requestID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get UserID by RequestID
    public int getUserIDByRequestID(int requestID) {
        String sql = "SELECT UserID FROM OwnerRequest WHERE RequestID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // or throw an exception if needed
    }
}
