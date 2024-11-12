package DAO.admindashboard;

import model.User;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserManagementDAO extends MySQLConnect {

    public UserManagementDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE Username = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int addUser(User user) {
        String sql = "INSERT INTO User (AvatarLink, Role, Username, Password, Email, Fullname, Birthday, Address, Province, District, Commune) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getAvatarLink());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getFullName());
            pstmt.setDate(7, user.getBirthday() != null ? new java.sql.Date(user.getBirthday().getTime()) : null);
            pstmt.setString(8, user.getAddress());
            pstmt.setString(9, user.getProvince());
            pstmt.setString(10, user.getDistrict());
            pstmt.setString(11, user.getCommune());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Tạo người dùng thất bại, không có dòng nào được thêm vào.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Tạo người dùng thất bại, không lấy được ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean deleteUser(int userID) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE UserID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserID(resultSet.getInt("UserID"));
                    user.setUsername(resultSet.getString("Username"));
                    user.setEmail(resultSet.getString("Email"));
                    user.setRole(resultSet.getString("Role"));
                    user.setIsBanned(resultSet.getBoolean("IsBanned"));
                    user.setFullName(resultSet.getString("Fullname"));
                    user.setBirthday(resultSet.getDate("Birthday"));
                    user.setAddress(resultSet.getString("Address"));
                    user.setProvince(resultSet.getString("Province"));
                    user.setDistrict(resultSet.getString("District"));
                    user.setCommune(resultSet.getString("Commune"));
                    user.setStatus(resultSet.getInt("Status"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE user SET Username = ?, Email = ?, Role = ?, Fullname = ?, Birthday = ?, Address = ?, Province = ?, District = ?, Commune = ?, Status = ? WHERE UserID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getFullName());
            statement.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getProvince());
            statement.setString(8, user.getDistrict());
            statement.setString(9, user.getCommune());
            statement.setInt(10, user.getStatus());
            statement.setInt(11, user.getUserID());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User ORDER BY UserID";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = extractUserFromResultSet(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    public boolean banUser(int userId) {
        String sql = "UPDATE user SET Status = 0 WHERE UserID = ? AND Role IN ('USER', 'OWNER')";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean unbanUser(int userId) {
        String sql = "UPDATE user SET Status = 1 WHERE UserID = ? AND Role IN ('USER', 'OWNER')";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setAvatarLink(rs.getString("AvatarLink"));
        user.setRole(rs.getString("Role"));
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setEmail(rs.getString("Email"));
        user.setFullName(rs.getString("Fullname"));
        user.setBirthday(rs.getDate("Birthday"));
        user.setAddress(rs.getString("Address"));
        user.setProvince(rs.getString("Province"));
        user.setDistrict(rs.getString("District"));
        user.setCommune(rs.getString("Commune"));
        user.setStatus(rs.getInt("Status"));
        return user;
    }
}
