/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.MySQLConnect;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;
import model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 *
 * @author Admin
 */
public class UserDAO extends MySQLConnect {
    
    public UserDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public ResultSet getResultSet(String sqlQuery) throws SQLException {
        ResultSet rs = null;
        try {
            PreparedStatement per = connection.prepareStatement(sqlQuery);
            rs = per.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet checkLogin(String username_email, String password) throws SQLException {
    String sqlQuery =  "SELECT 1 FROM `User` WHERE (Username = ? OR Email = ?) AND Password = ? AND Status = 1";
    ResultSet rs = null;
    
    try {
        PreparedStatement st = connection.prepareStatement(sqlQuery);
        st.setString(1, username_email);
        st.setString(2, username_email);

        // Assuming you hash the password before comparing it with the stored hashed password
//        String hashedPassword = hashPassword(password); // Replace with actual hashing function
        st.setString(3, password);

        rs = st.executeQuery();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return rs;
}


// Sample password hashing function (replace this with your actual implementation)
private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}

public boolean updateUserCode(String email, String code) throws SQLException {
    String sql = "UPDATE `User` SET `Code` = ? WHERE `Email` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, code);
        pstmt.setString(2, email);
        int kq = pstmt.executeUpdate();
        return kq == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


public String getUserCode(String email) throws SQLException {
    String sql = "SELECT `Code` FROM `User` WHERE `Email` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, email);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("Code");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    //DuyND-Get Role By email or username
    public String getUserRole(String username_email) throws SQLException {
    String role = "";
    String sqlQuery = "SELECT `Role` FROM `User` WHERE (`Username` = ? OR `Email` = ?) AND `Status` = 1";

    try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
        pstmt.setString(1, username_email);
        pstmt.setString(2, username_email);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                role = rs.getString("Role");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle exceptions appropriately in real scenarios
    }
    return role;
}

    public User getUser(String username_email) throws SQLException {
    String sqlQuery = "SELECT * FROM `User` WHERE (`Username` = ? OR `Email` = ?) AND `Status` = 1";
    try (PreparedStatement pstmt = connection.prepareStatement(sqlQuery)) {
        pstmt.setString(1, username_email);
        pstmt.setString(2, username_email);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setRole(rs.getString("Role"));
                user.setAvatarLink(rs.getString("AvatarLink"));
                user.setEmail(rs.getString("Email"));
                user.setFullName(rs.getString("Fullname"));
                user.setBirthday(rs.getDate("Birthday"));
                user.setAddress(rs.getString("Address"));
                user.setProvince(rs.getString("Province"));
                user.setDistrict(rs.getString("District"));
                user.setCommune(rs.getString("Commune"));
                return user;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
public boolean updateUserPasswordByEmail(String email, String password) throws SQLException {
    String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    String sql = "UPDATE `User` SET `Password` = ? WHERE `Email` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, hash);
        pstmt.setString(2, email);
        int kq = pstmt.executeUpdate();
        return kq == 1;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean updateUserPasswordByID(String id, String password) throws SQLException {
    String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    String sql = "UPDATE `User` SET `Password` = ? WHERE `Username` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, hash);
        pstmt.setString(2, id);
        int kq = pstmt.executeUpdate();
        return kq == 1;
    }
}

public User getUserById(String id) throws SQLException {
    User user = null;
    String sql = "SELECT * FROM `User` WHERE `UserID` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            user = extractUserFromResultSet(rs);
        }
    } catch (SQLException e) {
        System.err.println("SQL error: " + e.getMessage());
    }
    return user;
}

public User getUserById(int id) throws SQLException {
    User user = null;
    String sql = "SELECT * FROM `User` WHERE `UserID` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            user = extractUserFromResultSet(rs);
        }
    } catch (SQLException e) {
        System.err.println("SQL error: " + e.getMessage());
    }
    return user;
}

public User getUserByUsername(String username) throws SQLException {
    User user = null;
    String sql = "SELECT * FROM `User` WHERE `Username` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            user = extractUserFromResultSet(rs);
        }
    } catch (SQLException e) {
        System.err.println("SQL error: " + e.getMessage());
    }
    return user;
}

public boolean updateAvatarByUserID(int userID, String avatarLink) throws SQLException {
    String sql = "UPDATE `User` SET `AvatarLink` = ? WHERE `UserID` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, avatarLink);
        pstmt.setInt(2, userID);
        int updated = pstmt.executeUpdate();
        return updated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Rethrow SQLException for handling in the calling method
    }
}

private User extractUserFromResultSet(ResultSet rs) throws SQLException {
    User user = new User();
    user.setUserID(rs.getInt("UserID"));
    user.setAvatarLink(rs.getString("AvatarLink"));
    user.setUsername(rs.getString("Username"));
    user.setRole(rs.getString("Role"));
    user.setEmail(rs.getString("Email"));
    user.setFullName(rs.getString("Fullname"));
    user.setBirthday(rs.getDate("Birthday"));
    user.setAddress(rs.getString("Address"));
    user.setProvince(rs.getString("Province"));
    user.setDistrict(rs.getString("District"));
    user.setCommune(rs.getString("Commune"));
    user.setPassword(rs.getString("Password"));
    return user;
}

public boolean updateUser(User user) throws SQLException {
    String sql = "UPDATE `User` SET `Fullname` = ?, `Birthday` = ?, `Address` = ?, `Province` = ?, `District` = ?, `Commune` = ? WHERE `UserID` = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, user.getFullName());
        if (user.getBirthday() != null) {
            pstmt.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
        } else {
            pstmt.setNull(2, java.sql.Types.DATE);
        }
        pstmt.setString(3, user.getAddress());
        pstmt.setString(4, user.getProvince());
        pstmt.setString(5, user.getDistrict());
        pstmt.setString(6, user.getCommune());
        pstmt.setInt(7, user.getUserID());
        int updated = pstmt.executeUpdate();
        return updated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Rethrow SQLException for handling in the calling method
    }
}

public ResultSet getData(String table, String atr, Map<String, String> conditions) throws SQLException {
    String sqlQuery;
    if (conditions == null) {
        sqlQuery = "SELECT " + atr + " FROM `" + table + "`";
    } else {
        sqlQuery = "SELECT " + atr + " FROM `" + table + "` WHERE ";
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            sqlQuery += "`" + entry.getKey() + "` = ? AND ";
        }
        sqlQuery += "1 = 1";
    }
    PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
    int index = 1;
    if (conditions != null) {
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            pstmt.setString(index++, entry.getValue());
        }
    }
    return pstmt.executeQuery();
}

    // Query room

    // Save room 
    public int insertSeats(int roomID, String seatName, int coordinateX, int coordinateY) throws SQLException {
        String sql = "INSERT INTO Seat (RoomID, Name, CoordinateX, CoordinateY) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, roomID);
        ps.setString(2, seatName);
        ps.setInt(3, coordinateX);
        ps.setInt(4, coordinateY);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    public boolean checkExistEmail(String email) {
    boolean duplicate = false;
    String sql = "SELECT 1 FROM `User` WHERE email = ?"; // Dấu ngoặc ngược ` ` cho tên bảng

    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, email);
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                duplicate = true;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return duplicate;
}
    

}