/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.DatabaseConnectionManager;
import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Per
 */
public abstract class BaseDAO {

    protected Connection getConnection() throws SQLException {
        return DatabaseConnectionManager.getInstance(null).getConnection();
    }

    protected <T> T executeQuery(String sql, ResultSetHandler<T> handler) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {
            return handler.handle(resultSet);
        }
    }

    protected void executeUpdate(String sql, PreparedStatementSetter setter) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setter.setValues(preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    // Functional interfaces for handling ResultSet and setting PreparedStatement values
    @FunctionalInterface
    public interface ResultSetHandler<T> {
        T handle(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    public interface PreparedStatementSetter {
        void setValues(PreparedStatement preparedStatement) throws SQLException;
    }

}
