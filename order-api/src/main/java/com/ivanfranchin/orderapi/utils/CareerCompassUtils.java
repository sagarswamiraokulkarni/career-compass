package com.ivanfranchin.orderapi.utils;
import com.ivanfranchin.orderapi.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class CareerCompassUtils {
    private static CareerCompassUtils careerCompassUtils;

    private static BCryptPasswordEncoder bCryptPasswordEncoder;
    private CareerCompassUtils(){
        bCryptPasswordEncoder=new BCryptPasswordEncoder();
    }
    public static synchronized CareerCompassUtils getInstance(){
        if(careerCompassUtils==null) careerCompassUtils = new CareerCompassUtils();
        return careerCompassUtils;
    }

    public String encodeString(String unEncodedString){
        return bCryptPasswordEncoder.encode(unEncodedString);
    }
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public String convertToMD5(String password) {
        try {
            // Create an instance of MessageDigest with MD5 algorithm
            MessageDigest digest = MessageDigest.getInstance("MD5");

            // Update the digest with the password bytes
            digest.update(password.getBytes());

            // Compute the hash
            byte[] hashBytes = digest.digest();

            // Convert the hash bytes to a hexadecimal string representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateUniqueHash() {
//            try {
                // Get the current timestamp
                long timestamp = System.currentTimeMillis();

                // Generate a random UUID
                UUID uuid = UUID.randomUUID();

                // Combine the timestamp and UUID
                return timestamp + uuid.toString();
//
//                // Create an instance of MessageDigest with SHA-256 algorithm
//                MessageDigest digest = MessageDigest.getInstance("SHA-256");
//
//                // Update the digest with the input bytes
//                digest.update(input.getBytes());
//
//                // Compute the hash
//                byte[] hashBytes = digest.digest();
//
//                // Convert the hash bytes to a hexadecimal string representation
//                StringBuilder hexString = new StringBuilder();
//                for (byte b : hashBytes) {
//                    String hex = Integer.toHexString(0xff & b);
//                    if (hex.length() == 1) {
//                        hexString.append('0');
//                    }
//                    hexString.append(hex);
//                }
//
//                return hexString.toString();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//                return null;
//            }
        }
}
