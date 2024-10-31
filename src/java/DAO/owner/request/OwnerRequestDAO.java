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
        super();
        connect(context);
    }

    // Add a new owner request
    public boolean addOwnerRequest(int userID, String taxNumber, String businessLicenseFile) {
        String sql = "INSERT INTO OwnerRequest (UserID, TaxNumber, BusinessLicenseFile, Status) VALUES (?, ?, ?, 'pending')";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setString(2, taxNumber);
            pstmt.setString(3, businessLicenseFile);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get pending owner requests for admin review
    public List<OwnerRequest> getPendingRequests() {
        List<OwnerRequest> requests = new ArrayList<>();
        String sql = "SELECT orq.*, u.Username, u.Email "
                + "FROM OwnerRequest orq "
                + "JOIN User u ON orq.UserID = u.UserID "
                + "WHERE orq.Status = 'pending'";

        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                OwnerRequest request = new OwnerRequest(
                        rs.getInt("RequestID"),
                        rs.getInt("UserID"),
                        rs.getTimestamp("RequestDate"),
                        rs.getString("Status"),
                        rs.getString("TaxNumber"),
                        rs.getString("BusinessLicenseFile")
                );
                request.setUsername(rs.getString("Username"));
                request.setEmail(rs.getString("Email"));

                requests.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    // Update request status (approve/reject) and add reason
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

    // Get UserID associated with a specific RequestID
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
        return -1; // Return -1 if no userID found or an error occurs
    }

    // Check if a user has a pending request
    public boolean hasPendingRequest(int userID) {
        String sql = "SELECT COUNT(*) FROM OwnerRequest WHERE UserID = ? AND Status = 'pending'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if a user is already an approved owner
    public boolean isUserApprovedOwner(int userID) {
        String sql = "SELECT COUNT(*) FROM OwnerRequest WHERE UserID = ? AND Status = 'approved'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the current status of a user's owner request
    public String getRequestStatus(int userID) {
        String sql = "SELECT Status FROM OwnerRequest WHERE UserID = ? ORDER BY RequestDate DESC LIMIT 1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no request or an error occurs
    }
}
