package com.User_Authentication.security;



import com.User_Authentication.entity.User;
import com.User_Authentication.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    @Autowired
    private UserRepository userRepository;



    // generate token

    public String generateToken(Authentication authentication){
        String username=authentication.getName();
        Date currentDate=new Date();
        Date expireDate=new Date(currentDate.getTime()+jwtExpirationInMs);

        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
        return token;
    }

    //get username from teh token

    public String getUsernameFromJWT(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token) {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

    }
    public String createToken(String usernameOrEmail) {
        // Set the token's expiration date
        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);

        // Generate the JWT token
        return Jwts.builder()
                .setSubject(usernameOrEmail)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

          public String captchaGenerate(String email) throws Exception{
              String s="qwertyuiopasdfghjklzxcvbnmASDFGHJKLPOIUYTREWQZXCVBNM012" +
                      "3456789";
              String captcha="";
              for (int i=0;i<5;i++){
                  int index=(int)(Math.random()*62);
                  captcha+=s.charAt(index);
              }
              Optional<User> byEmail = userRepository.findByEmail(email);
              if (!byEmail.isPresent()){
                  throw new Exception("Data is not found for this email"+byEmail);
              }
              User user = byEmail.get();
              user.setCaptcha(captcha);
              userRepository.save(user);

              return captcha;
          }




}
