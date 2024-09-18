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

    private final String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
    
    private final String EMAIL_PATTERN
            = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public  boolean isPasswordPattern(String password) {
        return (Boolean )password.matches(passwordPattern);
    }

    public boolean isEmailPattern(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = this.pattern.matcher(email);
        return matcher.matches();
    }

  
}