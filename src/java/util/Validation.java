/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author PC
 */
public class Validation {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
    private static final Pattern PASSWORD_COMPILED_PATTERN = Pattern.compile(PASSWORD_PATTERN);

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);

    public static boolean isPasswordPattern(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = PASSWORD_COMPILED_PATTERN.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailPattern(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_COMPILED_PATTERN.matcher(email);
        return matcher.matches();
    }

  
}