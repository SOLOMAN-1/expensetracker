package com.ust.ExpenseTracker.service;

import com.ust.ExpenseTracker.entity.LogInDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtService {
    private String secretKey=null;
    Map<String , Objects> claims=new HashMap<>();
    public String getToken(LogInDto logInDto) {
        return Jwts.builder().claims().add(claims).subject(logInDto.getEmailId()).issuer("Soloman")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*10*1000))
                .and().signWith(generateSecretKey()).compact();
    }

    private SecretKey generateSecretKey() {
         byte[] byte1=Decoders.BASE64.decode(getSecretKey());
         return Keys.hmacShaKeyFor(byte1);
    }

    public String getSecretKey(){
        return secretKey="brF6Uc98vM47iaLSzfJGM/emVBTFcJFJdPIiOdbAdt4=";
    }

    public String extractUsername(String jwt) {

        return extractClaims(jwt, Claims::getSubject);
    }

    private <T>T extractClaims(String jwt, Function<Claims,T>claimResolver) {
        Claims claims=extractClaimsOriginal(jwt);
        return claimResolver.apply(claims);
    }

    private Claims extractClaimsOriginal(String jwt) {
        return Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(jwt).getPayload();
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        String userName=userDetails.getUsername();

        return userName.equals(extractUsername(jwt))&&!isExpired(jwt);
    }

    private boolean isExpired(String jwt) {
        Date date=expiration(jwt);
        return date.before(new Date());
    }

    private Date expiration(String jwt) {
        return extractClaims(jwt,Claims::getExpiration);
    }
}
