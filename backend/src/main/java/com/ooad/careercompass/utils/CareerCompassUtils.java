package com.ooad.careercompass.utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    public String convertToMD5(String password) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//
//            digest.update(password.getBytes());
//
//            byte[] hashBytes = digest.digest();
//
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hashBytes) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static <T> T gsonMapper(Object sourceObject, Class<T> targetClass) {
        String json = gson.toJson(sourceObject);
        Type targetType = TypeToken.getParameterized(targetClass, sourceObject.getClass()).getType();
        return gson.fromJson(json, targetType);
    }

    public static <T> Set<T> gsonMapperSet(Set<?> sourceSet, Class<T> targetClass) {
        Type setType = TypeToken.getParameterized(Set.class, targetClass).getType();
        String json = gson.toJson(sourceSet);
        return gson.fromJson(json, setType);
    }

    public static <T, U> List<U> gsonMapperList(List<T> sourceList, Class<U> targetClass) {
        return sourceList.stream()
                .map(sourceObject -> gsonMapper(sourceObject, targetClass))
                .collect(Collectors.toList());
    }
    public String generateUniqueHash() {
                long timestamp = System.currentTimeMillis();
                UUID uuid = UUID.randomUUID();
                return timestamp + uuid.toString();
        }
}
