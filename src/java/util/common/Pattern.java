/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.common;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Per
 */
public class Pattern {

    public static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "login", "logout", "register", "/login/google",
            "notifications", "forgetpassword", "styles", "assets"
    );

    public static final List<String> EXCLUDED_EXTENSIONS = Arrays.asList(
            ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".svg",
            ".woff", ".woff2", ".ttf", ".json"
    );

    public static final String PREFIX_SERVER = "Unove";
}
