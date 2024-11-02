/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Config {

    private static final Logger logger = Logger.getLogger(Config.class.getName());
    //DOMAIN  URL 
    public static final String DOMAIN;

    // Static block to initialize the DOMAIN based on the environment.
    static {

        //default to "development".
        String env = System.getProperty("env", "development");
        logger.info("Current environment: " + env);

        // Check if the environment is set to "production".
        if ("production".equals(env)) {
            // DOMAIN to the production URL.
            DOMAIN = "https://unove.store";
        } else {
            // (defaulting to development),
            //  DOMAIN to the local development URL.
            DOMAIN = "http://localhost:8080";
        }

        logger.info("Configured DOMAIN: " + DOMAIN);
    }
}
