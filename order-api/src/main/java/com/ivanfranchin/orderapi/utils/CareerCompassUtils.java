package com.ivanfranchin.orderapi.utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ivanfranchin.orderapi.model.User;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

public class CareerCompassUtils {
    private static CareerCompassUtils careerCompassUtils;
    public final String TWILIO_ACCOUNT_SID = "AC149c076bc2b866a0c48789efbe10e394";
    public final String TWILIO_AUTH_TOKEN = "e31135e1397345256efbbde63b72dcc3";
    public final String TWILIO_PHONE_NUMBER = "+15005550006";
    public final String TWILIO_AUTH_SERVICE_SID="VAc536ba7dbf710ba52de7ba66040f363c";
    public final String MAILJET_API_KEY="23d9f53040a4b4c4eebb28a8e5c22afd";
    public final String MAILJET_SECRET_KEY="a753c76a9a9efb048ebc1b1ba768a63d";
    private static BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

//    public static <T> T gsonMapper(Object sourceObject, Class<T> targetClass) {
//        return gson.fromJson(gson.toJson(sourceObject), targetClass);
//    }
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
//    public static <T> Set<T> gsonMapperSet(Set<?> sourceSet, Class<T> targetClass) {
//        // Create a TypeToken for the Set of target class
//        Type setType = TypeToken.getParameterized(Set.class, targetClass).getType();
//
//        // Convert the source set to JSON
//        String json = gson.toJson(sourceSet);
//
//        // Map the JSON to the Set of target class
//        Set<T> targetSet = gson.fromJson(json, setType);
//
//        return targetSet;
//    }
    public static <T> T gsonMapper(Object sourceObject, Class<T> targetClass) {
        // Convert the source object to JSON
        String json = gson.toJson(sourceObject);

        // Create a TypeToken for the target class
        Type targetType = TypeToken.getParameterized(targetClass, sourceObject.getClass()).getType();

        // Map the JSON to the target class
        T targetObject = gson.fromJson(json, targetType);

        return targetObject;
    }

    public static <T> Set<T> gsonMapperSet(Set<?> sourceSet, Class<T> targetClass) {
        // Create a TypeToken for the Set of target class
        Type setType = TypeToken.getParameterized(Set.class, targetClass).getType();

        // Convert the source set to JSON
        String json = gson.toJson(sourceSet);

        // Map the JSON to the Set of target class
        Set<T> targetSet = gson.fromJson(json, setType);

        return targetSet;
    }

    public static <T, U> List<U> gsonMapperList(List<T> sourceList, Class<U> targetClass) {
        return sourceList.stream()
                .map(sourceObject -> gsonMapper(sourceObject, targetClass))
                .collect(Collectors.toList());
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
