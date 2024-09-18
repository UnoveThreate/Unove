package DAO;

import DB.MySQLConnect;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class UserDAO extends MySQLConnect {

    public UserDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public ResultSet getResultSet(String sqlQuery) throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement per = connection.prepareStatement(sqlQuery)) {
            rs = per.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
        }
        return rs;
    }

   public boolean checkLogin(String username_email, String password) throws SQLException {
    String sqlQuery = "SELECT * FROM [User] WHERE (Username = ? OR Email = ?) AND Password = ? AND Status = 1";
    try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
        pstmt.setString(1, username_email);
        pstmt.setString(2, username_email);
        pstmt.setString(3, password);
        try (ResultSet rs = pstmt.executeQuery()) {
            return rs.next();
        }
    } catch (SQLException e) {
        System.err.println("Error checking login: " + e.getMessage());
        throw e;
    }
}



    public boolean checkExistEmail(String email) {
        String sql = "SELECT 1 FROM [User] WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
        }
        return false;
    }

    public boolean updateUserCode(String email, String code) throws SQLException {
        String sql = "UPDATE [User] SET Code = ? WHERE Email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, code);
            pstmt.setString(2, email);
            int result = pstmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            System.err.println("Error updating user code: " + e.getMessage());
            throw e;
        }
    }

    public String getUserCode(String email) throws SQLException {
        String sql = "SELECT Code FROM [User] WHERE Email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Code");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user code: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public boolean updateUserPassword(String id, String password) throws SQLException {
    String sql = "UPDATE [User] SET Password = ? WHERE UserID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, password); // Không băm mật khẩu
        pstmt.setString(2, id);
        int result = pstmt.executeUpdate();
        return result == 1;
    } catch (SQLException e) {
        System.err.println("Error updating user password: " + e.getMessage());
        throw e;
    }
}


    public User getUserById(String id) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM [User] WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by ID: " + e.getMessage());
            throw e;
        }
        return user;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setAvatarLink(rs.getString("AvatarLink"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setEmail(rs.getString("Email"));
        user.setFullName(rs.getString("Fullname"));
        user.setBirthday(rs.getDate("Birthday"));
        user.setAddress(rs.getString("Address"));
        user.setProvince(rs.getString("Province"));
        user.setDistrict(rs.getString("District"));
        user.setCommune(rs.getString("Commune"));
        return user;
    }

    public boolean updateUser(User user) throws SQLException {
    String sql = "UPDATE [User] SET AvatarLink=?, Username=?, Password=?, Email=?, Fullname=?, Birthday=?, Address=?, Province=?, District=?, Commune=? WHERE UserID=?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, user.getAvatarLink());
        pstmt.setString(2, user.getUsername());
        pstmt.setString(3, user.getPassword()); // Không băm mật khẩu
        pstmt.setString(4, user.getEmail());
        pstmt.setString(5, user.getFullName());

        if (user.getBirthday() != null) {
            pstmt.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
        } else {
            pstmt.setNull(6, java.sql.Types.DATE);
        }

        pstmt.setString(7, user.getAddress());
        pstmt.setString(8, user.getProvince());
        pstmt.setString(9, user.getDistrict());
        pstmt.setString(10, user.getCommune());
        pstmt.setInt(11, user.getUserID());

        int updated = pstmt.executeUpdate();
        return updated > 0;
    } catch (SQLException e) {
        System.err.println("Error updating user: " + e.getMessage());
        throw e;
    }
}
public boolean updateUserPasswordByEmail(String email, String newPassword) throws SQLException {
    String sql = "UPDATE [User] SET Password = ? WHERE Email = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, newPassword);
        pstmt.setString(2, email);
        int rowsUpdated = pstmt.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        System.err.println("Error updating user password: " + e.getMessage());
        throw e;
    }
}

    public String getUserRole(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
