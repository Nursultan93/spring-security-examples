package com.example.jwtautentication.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

  @Value("${token.signature}")
  private String jwtSecret;

  @Value("${token.expiredDate}")
  private int jwtExpiredDate;

  public String getUserNameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUserNameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

//  public String geneareToken(Authentication authentication) {
//
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//    Claims claims = new DefaultClaims();
//    claims.setId(UUID.randomUUID().toString());
//    claims.setSubject(userDetails.getUsername());
//
//    return Jwts.builder()
//        .setClaims(claims)
//        .setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiredDate))
//        .signWith(SignatureAlgorithm.HS256, jwtSecret)
//        .compact();
//  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {

    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiredDate))
        .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
  }



  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

}
