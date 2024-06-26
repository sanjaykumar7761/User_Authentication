package com.User_Authentication.payload;



import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;

    private String mobileNumber;
    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "captcha is required")
    private String captcha;

}
