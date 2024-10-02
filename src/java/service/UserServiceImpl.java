/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import DAO.UserDAOImpl;
import java.util.List;
import model.User;
import jakarta.servlet.ServletContext;

/**
 *
 * @author VINHNQ
 */
public class UserServiceImpl implements UserServiceInteface {

    UserDAOImpl userDAO;

    public UserServiceImpl(ServletContext context) throws Exception {
        userDAO = new UserDAOImpl(context);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User findOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User findOne(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean register(String fullName, String username, String email, String password, String code) {
        String hb = hashPassword(password);
        userDAO.insertRegister(new User(fullName, username, email,hb , code, 0, "USER"));
        
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDAO.checkExistEmail(email);
    }
    @Override
    public User login(String username, String password) {
        User user = this.findOne(username); // Find the user by username
        if (user != null && password.equals(user.getPassword())) {
            return user; // Return the user if login is successful
        }
        return null; // Return null if login fails
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDAO.checkExistUsername(username);
    }

    @Override
    public void updatestatus(User user) {
        userDAO.updateStatus(user);
    }

    @Override
    public String hashPassword(String password) {
        String hash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return hash;
    }

}