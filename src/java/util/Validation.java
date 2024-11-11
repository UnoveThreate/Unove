/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author F1
 */
public class Validation {

    // Regular expression pattern for password validation
    private static final String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";

    // Regular expression pattern for email validation
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    // Compiling the email pattern once to reuse it in email validation
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Checks if a given password matches the required pattern. The password
     * must contain at least one letter, one number, and be at least 8
     * characters long.
     *
     * @param password the password to validate
     * @return true if the password matches the pattern; false otherwise
     */
    public static boolean isPasswordPattern(String password) {
        return (Boolean) password.matches(passwordPattern);
    }

    /**
     * Validates the format of a given email. Uses a regular expression to check
     * if the email is in a valid format.
     *
     * @param email the email to validate
     * @return true if the email format matches the pattern; false otherwise
     */
    public static boolean isEmailPattern(String email) {
        if (email == null) { // Return false if the email is null
            return false;
        }
        Matcher matcher = pattern.matcher(email); // Apply the pattern to the email
        return matcher.matches(); // Return true if the email matches the pattern
    }

    /**
     * Checks if the specified role input matches the USER role. Uses the
     * isRoleValid method from the Role class to verify the role.
     *
     * @param roleInput the role to validate
     * @return true if the roleInput is valid and corresponds to the USER role;
     * false otherwise
     */
    public static boolean isValidRoleUser(String roleInput) {
        return Role.isRoleValid(roleInput, Role.USER);
    }

    /**
     * Checks if the specified role input matches the OWNER role. Uses the
     * isRoleValid method from the Role class to verify the role.
     *
     * @param roleInput the role to validate
     * @return true if the roleInput is valid and corresponds to the OWNER role;
     * false otherwise
     */
    public static boolean isValidRoleOwner(String roleInput) {
        return Role.isRoleValid(roleInput, Role.OWNER);
    }

    /**
     * Checks if the specified role input matches the ADMIN role. Uses the
     * isRoleValid method from the Role class to verify the role.
     *
     * @param roleInput the role to validate
     * @return true if the roleInput is valid and corresponds to the ADMIN role;
     * false otherwise
     */
    public static boolean isValidRoleAdmin(String roleInput) {
        return Role.isRoleValid(roleInput, Role.ADMIN);
    }

}
