/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.owner.confirm;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.Order;

/**
 *
 * @author DELL
 */
public class ConfirmDAO extends MySQLConnect {

    public ConfirmDAO(ServletContext context) throws Exception {
        super();
        connect(context); // Establish the connection from MySQLConnect
    }

    public boolean confirmOrder(int orderID, String code) {
        String sql = "UPDATE `order` SET status = 'Confirmed' WHERE orderID = ? AND code = ? ";

        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            ps.setInt(1, orderID);
            ps.setString(2, code);

            // Execute the update
            int rowsAffected = ps.executeUpdate();

            // Check if the order was updated
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
            // Return false if an error occurs
            return false;
        }

    }

    public boolean checkConfirmOrder(int orderID, int userID, String code) {
        boolean isValid = false;
        String sql = "SELECT * FROM `order` WHERE OrderID = ? AND UserID = ? AND code = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, userID);
            preparedStatement.setString(3, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Valid order found
                isValid = true;
                // Update status to 'Confirmed'
                confirmOrder(orderID, code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

//    public boolean checkConfirmTicket(int orderID) {
//        boolean isValid = false;
//        String sql = "SELECT * FROM ticket WHERE OrderID = ? and status = 'success'";
//
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, orderID);
//            ResultSet resultSet = ps.executeQuery();
//
//            if (resultSet.next()) {
//                // Valid order found
//                isValid = true;
//                // Update status to 'Confirmed'
//                confirmOrderTicket(orderID);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false; // Returns false if no matching ticket is found or an error occurs
//    }
//
//    public boolean confirmOrderTicket(int orderID) {
//        String sql = "UPDATE Ticket SET status = 'Confirmed' WHERE orderID = ? AND status = 'success'";
//
//        try (PreparedStatement ps = this.connection.prepareStatement(sql)) {
//
//            // Set the parameters for the prepared statement
//            ps.setInt(1, orderID);
//
//            // Execute the update
//            int rowsAffected = ps.executeUpdate();
//
//            // Check if the order was updated
//            return rowsAffected > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace(); // Log the error
//            // Return false if an error occurs
//            return false;
//        }
//
//    }
    public boolean isValidQRCodeOrder(int orderID, int userID, String code) {

        String validateOrderSQL = "SELECT OrderID FROM `order` o WHERE OrderID = ? AND UserID = ? AND Code = ? AND o.Status = ?";

        try (PreparedStatement st = connection.prepareStatement(validateOrderSQL)) {

            // Set parameters for validation query
            st.setInt(1, orderID);
            st.setInt(2, userID);
            st.setString(3, code);
            st.setString(4, "success");

            try (ResultSet rs = st.executeQuery();) {
                return rs.next(); // Returns true if a record is found, false otherwise
            }
            // Set parameters for update query

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception or rethrow as needed
        }
        // Return false if the validation or update failed
        return false;
    }

}
