/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.common;

/**
 *
 * @author Per
 */
public enum RoleEnum {
    
    USER,
    OWNER,
    ADMIN;

    /**
     * Checks if a role is valid by comparing it with known roles.
     *
     * @param roleInput the role to validate
     * @return true if the role exists in the enum; false otherwise
     */
    public static boolean isRoleValid(String roleInput) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.name().equalsIgnoreCase(roleInput)) {
                return true;
            }
        }
        return false;
    }

}
