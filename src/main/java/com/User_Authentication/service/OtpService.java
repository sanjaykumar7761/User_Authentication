package com.User_Authentication.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class OtpService {

    @Value("${twilio.phone_number}")
    private String twilioPhoneNumber;

    // Generate a random 6-digit OTP
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Send OTP via Twilio SMS
    public void sendOtp(String mobileNumber, String otp) {
        Message.creator(
                new PhoneNumber(mobileNumber),
                new PhoneNumber(twilioPhoneNumber),
                "Your OTP is: " + otp
        ).create();
    }
}

