package com.User_Authentication.controller;

import com.User_Authentication.payload.LoginDto;
import com.User_Authentication.payload.SignupDto;
import com.User_Authentication.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auths")
public class LoginConroller {

    @Autowired
    private OtpService otpService;

    private Map<String, String> otpCache = new HashMap<>();

    //http://localhost:8080/api/auths/sendOtp
    @PostMapping("/sendOtp")
    public void sendOtp(@RequestBody LoginDto loginDto) {
        String mobileNumber = loginDto.getMobileNumber();
        String otp = otpService.generateOtp();
        otpService.sendOtp(mobileNumber, otp);
        // Store OTP in cache for verification
        otpCache.put(mobileNumber, otp);
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestBody LoginDto loginDto) {
        String mobileNumber = loginDto.getMobileNumber();
        String userEnteredOtp = loginDto.getOtp();
        String storedOtp = otpCache.get(mobileNumber);
        if (userEnteredOtp.equals(storedOtp)) {
            // Successful login
//            otpCache.remove(mobileNumber);
            return "Login successful";
        } else {
            // Incorrect OTP
            return "Incorrect OTP";
        }
    }
}

