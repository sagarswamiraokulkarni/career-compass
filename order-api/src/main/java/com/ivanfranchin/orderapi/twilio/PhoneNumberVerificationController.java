package com.ivanfranchin.orderapi.twilio;


import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.notification.EmailNotificationStrategy;
import com.ivanfranchin.orderapi.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/phoneNumber")
@Slf4j
public class PhoneNumberVerificationController {

    private final String TWILIO_ACCOUNT_SID = "AC149c076bc2b866a0c48789efbe10e394";
    private final String TWILIO_AUTH_TOKEN = "e31135e1397345256efbbde63b72dcc3";
    private final String TWILIO_PHONE_NUMBER = "+15005550006";
    private final String TWILIO_AUTH_SERVICE_SID="VAc536ba7dbf710ba52de7ba66040f363c";

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/generateEmailOTP/{email}")
    public ResponseEntity<String> generateEmailOTP(@PathVariable("email") String email){
        EmailNotificationStrategy ens=new EmailNotificationStrategy();
        Optional<User> user = userRepository.findByEmail(email);

        ens.sendNotification(user.get());


        log.info("Email OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

        return new ResponseEntity<>("Your OTP has been sent to your Email", HttpStatus.OK);
    }

    @GetMapping(value = "/generateOTP/{phoneNumber}")
    public ResponseEntity<String> generateOTP(@PathVariable("phoneNumber") String phoneNumber){

//        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Verification verification = Verification.creator(
//                        System.getenv("TWILIO_AUTH_SERVICE_SID"), // this is your verification sid
                        TWILIO_AUTH_SERVICE_SID,
                        phoneNumber, //this is your Twilio verified recipient phone number
                        "whatsapp") // this is your channel type
                .create();

        System.out.println(verification.getStatus());

        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }

    @GetMapping("/verifyOTP/{phoneNumber}/{otp}")
    public ResponseEntity<?> verifyUserOTP(@PathVariable("phoneNumber") String phoneNumber,@PathVariable("otp") String otp) throws Exception {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
//        Twilio.init("AC149c076bc2b866a0c48789efbe10e394", "e31135e1397345256efbbde63b72dcc3");
        try {

            VerificationCheck verificationCheck = VerificationCheck.creator(
                            TWILIO_AUTH_SERVICE_SID)
                    .setTo(phoneNumber)
                    .setCode(otp)
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
    }
}