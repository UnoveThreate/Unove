/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.cinemaChainOwnerDAO;

import database.MySQLConnect;
import model.Seat;

import jakarta.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO extends MySQLConnect {

    public SeatDAO(ServletContext context) throws Exception {
        super();
        connect(context);
    }

    // Phương thức lấy danh sách ghế dựa trên roomID
    public List<Seat> getSeatsByRoomID(int roomID) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String query = "SELECT SeatID, RoomID, Name, CoordinateX, CoordinateY FROM Seat WHERE RoomID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roomID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatID(rs.getInt("SeatID"));
                    seat.setRoomID(rs.getInt("RoomID"));
                    seat.setName(rs.getString("Name"));
                    seat.setCoordinateX(rs.getInt("CoordinateX"));
                    seat.setCoordinateY(rs.getInt("CoordinateY"));
                    seats.add(seat);
                }
            }
        }
        return seats;
    }

}
