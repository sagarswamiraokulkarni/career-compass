package com.ivanfranchin.orderapi.twilio;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/phoneNumber")
@Slf4j
public class PhoneNumberVerificationController {



//    @Autowired
//    private UserRepository userRepository;

//    @GetMapping(value = "/generateEmailOTP/{email}")
//    public ResponseEntity<String> generateEmailOTP(@PathVariable("email") String email){
//        EmailNotificationStrategy ens=new EmailNotificationStrategy();
//        Optional<User> user = userRepository.findByEmail(email);
//
//        ens.sendNotification(user.get());
//
//
//        log.info("Email OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
//
//        return new ResponseEntity<>("Your OTP has been sent to your Email", HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/generateOTP/{phoneNumber}")
//    public ResponseEntity<String> generateOTP(@PathVariable("phoneNumber") String phoneNumber){
//
////        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
//        Twilio.init(CareerCompassUtils.getInstance().TWILIO_ACCOUNT_SID, CareerCompassUtils.getInstance().TWILIO_AUTH_TOKEN);
//        Verification verification = Verification.creator(
////                        System.getenv("TWILIO_AUTH_SERVICE_SID"), // this is your verification sid
//                        CareerCompassUtils.getInstance().TWILIO_AUTH_SERVICE_SID,
//                        phoneNumber, //this is your Twilio verified recipient phone number
//                        "whatsapp") // this is your channel type
//                .create();
//
//        System.out.println(verification.getStatus());
//
//        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
//
//        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
//    }
//
//    @GetMapping("/verifyOTP/{phoneNumber}/{otp}")
//    public ResponseEntity<?> verifyUserOTP(@PathVariable("phoneNumber") String phoneNumber,@PathVariable("otp") String otp) throws Exception {
//        Twilio.init(CareerCompassUtils.getInstance().TWILIO_ACCOUNT_SID, CareerCompassUtils.getInstance().TWILIO_AUTH_TOKEN);
////        Twilio.init("AC149c076bc2b866a0c48789efbe10e394", "e31135e1397345256efbbde63b72dcc3");
//        try {
//
//            VerificationCheck verificationCheck = VerificationCheck.creator(
//                            CareerCompassUtils.getInstance().TWILIO_AUTH_SERVICE_SID)
//                    .setTo(phoneNumber)
//                    .setCode(otp)
//                    .create();
//
//            System.out.println(verificationCheck.getStatus());
//
//        } catch (Exception e) {
//            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
//    }
}