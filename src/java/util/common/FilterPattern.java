/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.common;

import DAO.UserDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import util.RouterURL;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Validation;

/**
 *
 * @author Per
 */
public class FilterPattern {

    private static final Logger LOGGER = Logger.getLogger(FilterPattern.class.getName());
    private static final String LOGIN_URI = RouterURL.LOGIN;

    private static final Map<String, RoleEnum> URL_ROLE_MAP = new HashMap<>();

    static {
        URL_ROLE_MAP.put("/user", RoleEnum.USER);
        URL_ROLE_MAP.put("/admin", RoleEnum.ADMIN);
        URL_ROLE_MAP.put("/owner", RoleEnum.OWNER);
    }

    /**
     * Validates the user's role based on the URL they are accessing. Redirects
     * to the login page if the user does not have the required role to access
     * page without login
     *
     *
     * @param httpRequest the HTTP request containing session and URL
     * information
     * @param httpResponse the HTTP response used for redirecting if validation
     * fails
     * @return true if redirection occurred due to invalid role; false
     * otherwise,
     * @throws IOException if an error occurs during the redirection process
     */
    public static boolean validationRoleForRedirect(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        try {

            String urlStore = storeUrlAndParams(httpRequest);
            LOGGER.info("history url:  " + urlStore);

            // if url doesn't require for roles -> accept redirect without login 
            boolean isDenyAccessBeforeLogin = isRoleMismatch(httpRequest);

            if (!isDenyAccessBeforeLogin) {
                return false;
            }

            return redirectToLogin(httpResponse);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during role validation", e);
            return false;
        }

    }

    /**
     * Checks if the user's role matches the required role for the requested
     * URL. Invalidates the session if the role does not match the required role
     * for the URL.
     *
     * @param httpRequest the HttpServletRequest containing the URL and session
     * @return true if there is a role mismatch; false otherwise
     * @throws Exception if an error occurs during role retrieval
     */
    private static boolean isRoleMismatch(HttpServletRequest httpRequest) throws Exception {
        // Get the requested URL path and user's role
        String url = httpRequest.getServletPath();
        String role = getRole(httpRequest);

        // Iterate over URL role mappings to check for a match
        for (Map.Entry<String, RoleEnum> entry : URL_ROLE_MAP.entrySet()) {
            if (!url.contains(entry.getKey())) {
                continue;
            }
            // Redirect if no role is found for protected URLs
            if (role == null) {
                return true;
            }
            // Invalidate session and redirect if role mismatch occurs
            if (!role.equals(entry.getValue().name())) {
                httpRequest.getSession().invalidate();
                return true;
            }
        }
        // No mismatch if the required role matches the user's role
        return false;
    }

    /**
     * Retrieves the role of the user from the session or database.
     *
     * @param httpRequest the HTTP request containing session information
     * @return the user's role as a String, or null if no role is found
     * @throws Exception if there is an error retrieving the role from the
     * database
     */
    public static String getRole(HttpServletRequest httpRequest) throws Exception {

        HttpSession session = httpRequest.getSession();

        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");

        if (username == null && email == null && role == null) {
            return null;
        }

        String userAccount = (username != null) ? username : email;

        if (role != null) {
            return role;
        }

        return new UserDAO((ServletContext) httpRequest.getServletContext()).getUserRole(userAccount);
    }

    /**
     * Stores the URL and all request parameters in the session if the URL is
     * not excluded.
     *
     * @param httpRequest the HTTP request containing the URL and parameter
     * @return the stored URL if valid, otherwise null
     *
     */
    public static String storeUrlAndParams(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(true);
        String urlStore = httpRequest.getRequestURI();

        boolean isValidURLStore = isValidUrlToStore(urlStore);

        if (!isValidURLStore) {
            return null; // URL is not valid for storage
        }

        // Store the URL in the session with the attribute name "redirectTo"
        session.setAttribute("redirectTo", urlStore);
        // Store each request parameter in the session with "param_" prefix
        Enumeration<String> parameterNames = httpRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = httpRequest.getParameter(paramName);
            session.setAttribute("param_" + paramName, paramValue);
        }

        return urlStore;
    }

    /**
     * Validates if the URL is eligible for storage in session.
     *
     * @param url the URL to validate
     * @return true if the URL is valid for storage; false otherwise
     */
    private static boolean isValidUrlToStore(String url) {
        return url.contains(Pattern.PREFIX_SERVER) && !isExcluded(url);
    }

    /**
     * Checks if the URL should be excluded from authentication or role
     * validation.
     *
     * @param url the URL to check
     * @return true if the URL is excluded; false otherwise
     */
    private static boolean isExcluded(String url) {
        // Check for excluded paths
        boolean isExcludedPath = Pattern.EXCLUDED_PATHS.stream().anyMatch(url::contains);

        // Check for excluded file extensions
        boolean isExcludedExtension = Pattern.EXCLUDED_EXTENSIONS.stream().anyMatch(url::endsWith);

        return isExcludedPath || isExcludedExtension;
    }

    /**
     * Redirects the user to the login page.
     *
     * @param httpResponse the HTTP response used for redirection
     * @return true after performing the redirection
     * @throws IOException if an error occurs during the redirection process
     */
    public static boolean redirectToLogin(HttpServletResponse httpResponse) throws IOException {
        httpResponse.sendRedirect(LOGIN_URI);
        return true;
    }

    /**
     * Checks if a redirection is needed by verifying the presence of a
     * "redirectTo" attribute in the session. If the "redirectTo" attribute is
     * null, it implies that no redirection has been previously set.
     *
     * @param request the HttpServletRequest containing the session
     * @return true if there is no stored redirection path (i.e., redirection is
     * valid); false if a path is stored
     */
    public static boolean isRedirectRequired(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String redirectTo = (String) session.getAttribute("redirectTo");

        return redirectTo != null;
    }

    /**
     * Reconstructs the URL with stored parameters from the session and
     * redirects to that URL. Session attributes prefixed with "param_" are
     * appended as query parameters to the URL. After appending, these
     * attributes are removed from the session.
     *
     * @param request HttpServletRequest
     * @param response the HttpServletResponse used to send the redirect
     * @throws IOException if an error occurs during redirection
     */
    public static void reconstructAndRedirectWithStoredParams(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

        HttpSession session = request.getSession();
        String redirectTo = (String) session.getAttribute("redirectTo");

        if (!isRedirectRequired(request)) {
            response.sendRedirect(RouterURL.LANDING_PAGE);
            return;
        }

        StringBuilder redirectUrlWithParams = new StringBuilder(redirectTo);
        boolean firstParam = true;

        // Retrieve all session attributes
        Enumeration<String> attributeNames = session.getAttributeNames();

        // Iterate through attributes and add any prefixed with "param_" as URL parameters
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();

            // Check if the attribute name starts with "param_" to identify stored parameters
            if (attributeName.startsWith("param_")) {
                String paramName = attributeName.substring(6); // Remove "param_" prefix
                String paramValue = (String) session.getAttribute(attributeName);

                // Append '?' for the first parameter, '&' for subsequent parameters
                if (firstParam) {
                    redirectUrlWithParams.append("?");
                    firstParam = false;
                } else {
                    redirectUrlWithParams.append("&");
                }

                // Append parameter name and value to the URL
                redirectUrlWithParams.append(paramName).append("=").append(paramValue);

                // Remove the parameter from the session after appending
                session.removeAttribute(attributeName);
            }
        }

        session.removeAttribute("redirectTo");

        response.sendRedirect(redirectUrlWithParams.toString());
    }

}
