package com.yvolabs.projectmanagementsystemserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Yvonne N
 */

@Component
public class JwtConstant {

    public static String JWT_SECRET_KEY;

    public static final String JWT_HEADER = "Authorization";

    /**
     * @implNote Note we cannot use @Value on a STATIC field hence this set method, secrete key is set as an env var.
     * Also note that this key can be anything but should be kept secret.
     *
     * @param jwtSecretKey This value is from application.properties file
     */
    @Value("${auth.token.jwtSecret}")
    public void setJwtSecretKey(String jwtSecretKey) {
        JWT_SECRET_KEY = jwtSecretKey;
    }

}
