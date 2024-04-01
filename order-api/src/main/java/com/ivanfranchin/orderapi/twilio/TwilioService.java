package com.ivanfranchin.orderapi.twilio;

import okhttp3.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

public class TwilioService {
        public static void main(String[] args) throws IOException {
            String url = "https://verify.twilio.com/v2/Services/VAc536ba7dbf710ba52de7ba66040f363c/Verifications";
            String checkUrl = "https://verify.twilio.com/v2/Services/VAc536ba7dbf710ba52de7ba66040f363c/VerificationCheck";
            String username = "AC149c076bc2b866a0c48789efbe10e394";
            String secret = "e31135e1397345256efbbde63b72dcc3";

            OkHttpClient client = new OkHttpClient();

            // Send verification request
            RequestBody requestBody = new FormBody.Builder()
                    .add("To", "+13034347446")
                    .add("Channel", "sms")
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + secret).getBytes()))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    System.out.println(response.body().toString());

                    String responseBody = response.body().string();
                    System.out.println("Verification SID: " + extractValue(responseBody, "\"sid\""));
                    System.out.println("Message Body: " + extractValue(responseBody, "\"body\""));
                } else {
                    System.out.println("Verification request failed: " + response.code());
                }
            }

            // Read OTP from user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter the OTP: ");
            String otp = scanner.nextLine();

            // Check verification code
            RequestBody checkRequestBody = new FormBody.Builder()
                    .add("To", "+13034347446")
                    .add("Code", otp)
                    .build();

            Request checkRequest = new Request.Builder()
                    .url(checkUrl)
                    .post(checkRequestBody)
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + secret).getBytes()))
                    .build();

            try (Response response = client.newCall(checkRequest).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    System.out.println("Verification Check SID: " + extractValue(responseBody, "\"sid\""));
                    System.out.println("Verification Result: " + extractValue(responseBody, "\"status\""));
                } else {
                    System.out.println("Verification check failed: " + response.code());
                }
            }
        }

        private static String extractValue(String json, String key) {
            int startIndex = json.indexOf(key) + key.length() + 2;
            int endIndex = json.indexOf("\"", startIndex);
            return json.substring(startIndex, endIndex);
        }
}





