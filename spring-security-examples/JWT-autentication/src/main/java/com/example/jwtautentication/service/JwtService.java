package com.example.jwtautentication.service;

import com.example.jwtautentication.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  @Value("${token.signature}")
  private String SECRET;
  @Value("${token.expiredDate}")
  private int expitedDate;

  public String getUsernameFromToken(String token) {
    var username = getClaimFromToken(token, Claims::getSubject);
    return username;
  }

  public boolean isValidToken(String token, UserDetails userDetails) {

    var username = getClaimFromToken(token, Claims::getSubject);

    return !isExpired(token) && username.equals(userDetails.getUsername());

  }


  public String generateToken(User user) {

    Claims claims = new DefaultClaims();
    claims.setId(user.getId().toString());
    claims.setSubject(user.getUsername());
    claims.setIssuedAt(new Date());
    claims.setExpiration(new Date(System.currentTimeMillis() + expitedDate));

    String jwt = Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();


    return jwt;
  }

  private boolean isExpired(String token) {
    var date = getClaimFromToken(token, Claims::getExpiration);

    return date.before(new Date());
  }

  private  <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
    Claims claims = getAllClaimsFromToken(token);

    return claimsTFunction.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
  }

}
