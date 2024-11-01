package DAO.admindashboard;

import model.CinemaChain;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaChainManagementDAO extends MySQLConnect {

    public CinemaChainManagementDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }
    // lấy tất cả chuỗi rạp

    public List<CinemaChain> getAllCinemaChains() {
        List<CinemaChain> cinemaChains = new ArrayList<>();
        String sql = "SELECT * FROM cinemachain";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                cinemaChains.add(extractCinemaChainFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChains;
    }

    public CinemaChain getCinemaChainByID(int cinemaChainID) {
        String sql = "SELECT * FROM cinemachain WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractCinemaChainFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addCinemaChain(CinemaChain cinemaChain) {
        String sql = "INSERT INTO cinemachain (UserID, Name, AvatarURL, Information) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChain.getUserID());
            pstmt.setString(2, cinemaChain.getName());
            pstmt.setString(3, cinemaChain.getAvatarURL());
            pstmt.setString(4, cinemaChain.getInformation());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCinemaChain(CinemaChain cinemaChain) {
        String sql = "UPDATE cinemachain SET Name = ?, AvatarURL = ?, Information = ? WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setString(1, cinemaChain.getName());
            pstmt.setString(2, cinemaChain.getAvatarURL());
            pstmt.setString(3, cinemaChain.getInformation());
            pstmt.setInt(4, cinemaChain.getCinemaChainID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
// xóa chuỗi rạp

    public boolean deleteCinemaChain(int cinemaChainID) {
        String sql = "DELETE FROM cinemachain WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private CinemaChain extractCinemaChainFromResultSet(ResultSet rs) throws SQLException {
        CinemaChain cinemaChain = new CinemaChain();
        cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
        cinemaChain.setUserID(rs.getInt("UserID"));
        cinemaChain.setName(rs.getString("Name"));
        cinemaChain.setAvatarURL(rs.getString("AvatarURL"));
        cinemaChain.setInformation(rs.getString("Information"));
//        cinemaChain.setStatus(rs.getString("status"));
        return cinemaChain;
    }

    public int getCinemaCountForChain(int cinemaChainID) {
        String sql = "SELECT COUNT(*) FROM cinema WHERE cinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Integer> getAllUserIDs() {
        List<Integer> userIDs = new ArrayList<>();
        String sql = "SELECT UserID FROM user";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                userIDs.add(rs.getInt("UserID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userIDs;
    }
    public boolean updateCinemaChainStatus(int cinemaChainID, String status) {
    String sql = "UPDATE cinemachain SET status = ? WHERE CinemaChainID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, status);
        pstmt.setInt(2, cinemaChainID);
        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
//    public List<CinemaChain> getActiveCinemaChains() {
//    List<CinemaChain> chains = new ArrayList<>();
//    String sql = "SELECT * FROM cinemachain WHERE status = 'active'";
//    try (
//         PreparedStatement pstmt = connection.prepareStatement(sql);
//         ResultSet rs = pstmt.executeQuery()) {
//        while (rs.next()) {
//            CinemaChain chain = new CinemaChain();
//            chain.setCinemaChainID(rs.getInt("CinemaChainID"));
//            chain.setName(rs.getString("Name"));
//            chain.setAvatarURL(rs.getString("AvatarURL"));
//            chain.setInformation(rs.getString("Information"));
//            chain.setStatus(rs.getString("status"));
//            chains.add(chain);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return chains;
//}

public List<CinemaChain> getCinemaChainsForMovie(int movieId) {
    List<CinemaChain> chains = new ArrayList<>();
    String sql = "SELECT DISTINCT cc.CinemaChainID, cc.Name, cc.AvatarURL " +
                 "FROM CinemaChain cc " +
                 "JOIN Cinema c ON cc.CinemaChainID = c.CinemaChainID " +
                 "JOIN Room r ON c.CinemaID = r.CinemaID " +
                 "JOIN MovieSlot ms ON r.RoomID = ms.RoomID " +
                 "WHERE ms.MovieID = ? " +
                 "AND ms.StartTime > CURRENT_TIMESTAMP"; 
    try (
         PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, movieId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                CinemaChain chain = new CinemaChain();
                chain.setCinemaChainID(rs.getInt("CinemaChainID"));
                chain.setName(rs.getString("Name"));
                chain.setAvatarURL(rs.getString("AvatarURL"));
                chains.add(chain);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return chains;
}

}
