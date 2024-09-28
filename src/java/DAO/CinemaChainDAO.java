package dao;

import model.CinemaChain;
import database.MySQLConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaChainDAO extends MySQLConnect {

    public CinemaChainDAO() {
        super(); 
    }

    public List<CinemaChain> getAllCinemaChains() {
        List<CinemaChain> cinemaChains = new ArrayList<>();
        String sql = "SELECT * FROM CinemaChain";

        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CinemaChain cinemaChain = new CinemaChain();
                cinemaChain.setCinemaChainID(rs.getInt("CinemaChainID"));
                cinemaChain.setName(rs.getString("Name"));
                cinemaChain.setInformation(rs.getString("Information"));
                cinemaChains.add(cinemaChain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinemaChains;
    }

    public boolean insertCinemaChain(CinemaChain cinemaChain) {
        String sql = "INSERT INTO CinemaChain (Name, Information) VALUES (?, ?)";
        try (Connection conn = this.connection; 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cinemaChain.getName());
            pstmt.setString(2, cinemaChain.getInformation());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
}