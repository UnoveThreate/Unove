/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.cinemaChainOwnerDAO;

import database.MySQLConnect;
import jakarta.servlet.ServletContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class CountryDAO extends MySQLConnect {

    public CountryDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    public List<String> getAllCountries() throws SQLException {
        List<String> countries = new ArrayList<>();
        String sql = "SELECT DISTINCT Country FROM movie"; // Hoặc bảng chứa thông tin quốc gia

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                countries.add(resultSet.getString("Country"));
            }
        }

        return countries;
    }
}
