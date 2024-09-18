package DAO;

import DB.MySQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;
import jakarta.servlet.ServletContext;
import java.util.Properties;

public class UserDAOImpl extends MySQLConnect {

    public UserDAOImpl(ServletContext context) throws Exception {
        super();
        connect((Properties) context);
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM User";  // Removed brackets
        
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
             
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("fullName"), rs.getString("password"), rs.getString("email"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
        }
        return list;
    }

    public User getUserByID(String userName) {
        String sql = "SELECT * FROM User WHERE username = ?";  // Removed brackets
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, userName);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("fullName"), rs.getString("password"), rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
        }
        return null;
    }

    public void updateStatus(User user) {
        String sql = "UPDATE User SET status = ?, code = ? WHERE email = ?";  // Removed brackets
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, user.getStatus());
            st.setString(2, user.getCode());
            st.setString(3, user.getEmail());
            
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating user status: " + e.getMessage());
        }
    }

    public boolean checkExistUsername(String username) {
        String sql = "SELECT 1 FROM User WHERE username = ?";  // Removed brackets
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking if username exists: " + e.getMessage());
        }
        return false;
    }

    public boolean checkExistEmail(String email) {
        String sql = "SELECT 1 FROM User WHERE email = ? AND status = 1";  // Removed brackets
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking if email exists: " + e.getMessage());
        }
        return false;
    }

    public void insertRegister(User user) {
        String sql = "INSERT INTO User (fullName, username, email, password, code, status, role) VALUES (?, ?, ?, ?, ?, ?, ?)";  // Removed brackets
        
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, user.getFullName());
            st.setString(2, user.getUsername());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setString(5, user.getCode());
            st.setInt(6, user.getStatus());
            st.setString(7, user.getRole());
            
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting new user: " + e.getMessage());
        }
    }

    public void updatestatus(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // This method has not been implemented
    }
}
