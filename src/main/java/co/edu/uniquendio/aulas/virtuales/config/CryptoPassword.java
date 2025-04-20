package co.edu.uniquendio.aulas.virtuales.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoPassword {

    public static String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }
}
