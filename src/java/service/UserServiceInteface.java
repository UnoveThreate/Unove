/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import model.User;

/**
 *
 * @author VINHNQ
 */
public interface UserServiceInteface {

    List<User> getAll();

    User findOne(int id); // hàm lấy 01 đối tượng User theo ID

    User findOne(String username); // hàm lấy 01 đối tượng User theo username

    void insert(User user); // hàm này thêm dữ liệu mới cho User

    void update(User user); // hàm này cập nhật 1 đối tượng User

    void updatestatus(User user); // hàm này dùng active tài khoản

    void delete(int id); // hàm này xóa 1 đối tượng User

    boolean register(String fullname, String username, String email, String password, String code); // hàm đăng ký mới cho User

    User login(String username, String password); // hàm đăng nhập

    boolean checkExistEmail(String email); // hàm kiểm tra sự tồn tại của email

    boolean checkExistUsername(String username);

    String hashPassword(String password);
}