package DAOSchedule;

import model.CinemaChain;
import database.MySQLConnect;
import jakarta.servlet.ServletContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaChainScheduleDAO extends MySQLConnect {

    public CinemaChainScheduleDAO(ServletContext context) throws Exception {
        super();
        connect((ServletContext) context);
    }

    // Lấy tất cả chuỗi rạp chiếu phim
    public List<CinemaChain> getAllCinemaChains() {
        List<CinemaChain> cinemaChains = new ArrayList<>();
        String sql = "SELECT * FROM CinemaChain";

        try (
                PreparedStatement pstmt = this.connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CinemaChain cinemaChain = new CinemaChain();
                cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinemaChain.setUserID(rs.getInt("UserID"));
                cinemaChain.setName(rs.getString("Name"));
                cinemaChain.setAvatarURL(rs.getString("AvatarURL"));
                cinemaChain.setInformation(rs.getString("Information"));
                cinemaChains.add(cinemaChain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChains;
    }

    // Thêm chuỗi rạp chiếu phim mới
    public boolean insertCinemaChain(CinemaChain cinemaChain) {
        String sql = "INSERT INTO CinemaChain (UserID, Name, AvatarURL, Information) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement pstmt = this.connection.prepareStatement(sql)) {

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

    // Lấy thông tin chuỗi rạp chiếu phim theo ID
    public CinemaChain getCinemaChainById(int cinemaChainID) {
        CinemaChain cinemaChain = null;
        String sql = "SELECT * FROM CinemaChain WHERE CinemaChainID = ?";
        try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaChainID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cinemaChain = new CinemaChain();
                    cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
                    cinemaChain.setUserID(rs.getInt("UserID"));
                    cinemaChain.setName(rs.getString("Name"));
                    cinemaChain.setAvatarURL(rs.getString("AvatarURL"));
                    cinemaChain.setInformation(rs.getString("Information"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChain;
    }
}
