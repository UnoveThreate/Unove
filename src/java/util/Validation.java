/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.common.FilterPattern;
import util.common.Key;

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

    /**
     * Redirects to the login page if the user does not meet the required role.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param roleRequired the role required to access the page
     * @return true if the user meets the required role, false otherwise
     * @throws IOException if an I/O error occurs during redirection
     * @throws Exception if an error occurs while retrieving the user's role
     */
    public static boolean requireLogin(HttpServletRequest request, HttpServletResponse response, String roleRequired) throws IOException, Exception {

        if (roleRequired == null) {
            return true;
        }

        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        String role = FilterPattern.getRole(request);

        if (userID == null || role == null) {
            FilterPattern.redirectToLogin(response);
            return false;
        }

        // Validate the required role type
        if (!isValidRoleType(roleRequired)) {
            response.sendRedirect(RouterURL.ERROR_PAGE);
            return false;
        }

        // Check role-specific access and redirect if the user does not meet the required role
        return isUserAuthorizedForRole(roleRequired, role, request, response);
    }

    /**
     * Checks if the provided roleRequired is a valid role type.
     *
     * @param roleRequired the role to validate
     * @return true if roleRequired is a valid role, false otherwise
     */
    public static boolean isValidRoleType(String roleRequired) {
        return roleRequired.equals(Role.USER) || roleRequired.equals(Role.OWNER) || roleRequired.equals(Role.ADMIN);
    }

    /**
     * Checks if the user is authorized for the specified role and handles
     * redirection if not.
     *
     * @param roleRequired the required role for access
     * @param userRole the user's current role
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @return true if the user is authorized, false if redirection occurred
     * @throws IOException if an I/O error occurs during redirection
     */
    public static boolean isUserAuthorizedForRole(String roleRequired, String userRole, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean isAuthorized = switch (roleRequired) {
            case Role.USER ->
                Validation.isValidRoleUser(userRole);
            case Role.OWNER ->
                Validation.isValidRoleOwner(userRole);
            case Role.ADMIN ->
                Validation.isValidRoleAdmin(userRole);
            default ->
                false;
        };

        if (!isAuthorized) {
            request.getSession().setAttribute(Key.ROLE_REQUIRE, roleRequired);
            FilterPattern.redirectToLogin(response);
        }

        return isAuthorized;
    }

}
