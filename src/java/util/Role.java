/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Per
 */
public class Role {

    public final static String USER = "USER";
    public final static String ADMIN = "ADMIN";
    public final static String OWNER = "OWNER";

    public static boolean isRoleValid(String roleInput, String roleValid) {
        if (roleInput == null || roleInput.isEmpty()) {
            return false;
        }
        return roleInput.equals(roleValid);
    }

}

