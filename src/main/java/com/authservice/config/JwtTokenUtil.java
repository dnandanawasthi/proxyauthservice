package com.authservice.config;

import com.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import static com.authservice.common.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.authservice.common.Constants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {

    public String getUsernameFromToken(String token) {
        System.out.println("JwtTokenUtil:getUsernameFromToken  token is:"+token);
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        System.out.println("JwtTokenUtil:getClaimFromToken  token is:"+token);
        final Claims claims = getAllClaimsFromToken(token);
        System.out.println("JwtTokenUtil:getClaimFromToken next step is applying ");
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        System.out.println("JwtTokenUtil:getAllClaimsFromToken  token is:"+token);
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        System.out.println("JwtTokenUtil:isTokenExpired  triggered ");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getUsername());
    }

    private String doGenerateToken(String subject) {
        System.out.println("JwtTokenUtil:doGenerateToken  triggered ");
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://authservice.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        System.out.println("JwtTokenUtil:validateToken  triggered token and userDetails: "+token+"      :       " +userDetails);
        final String username = getUsernameFromToken(token);
        System.out.println("JwtTokenUtil:validateToken  username from token:  "+username);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

}
