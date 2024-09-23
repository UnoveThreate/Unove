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

/**
 * Data Access Object for User management
 */
public class UserDAOImpl extends MySQLConnect {

    public UserDAOImpl(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM `User`";
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                User c = new User(
                    rs.getString("username"),
                    rs.getString("fullName"),
                    rs.getString("password"),
                    rs.getString("email")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public User getUserByID(String userName) {
        String sql = "SELECT * FROM `User` WHERE `username` = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, userName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateStatus(User user) {
        String sql = "UPDATE `User` SET `status` = ?, `code` = ? WHERE `email` = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, user.getStatus());
            st.setString(2, user.getCode());
            st.setString(3, user.getEmail());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkExistUsername(String username) {
        boolean exists = false;
        String sql = "SELECT 1 FROM `User` WHERE `username` = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean checkExistEmail(String email) {
        boolean exists = false;
        String sql = "SELECT 1 FROM `User` WHERE `email` = ? AND `status` = 1";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    exists = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void insertRegister(User user) {
        String sql = "INSERT INTO `User` (`Fullname`, `Username`, `Email`, `Password`, `Code`, `Status`, `Role`) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            e.printStackTrace();
        }
    }
}
