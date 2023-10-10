package com.example.Twitter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TokenService {
 private JwtEncoder jwtEncoder;
 private JwtDecoder jwtDecoder;

 @Autowired
 public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder){
  this.jwtDecoder = jwtDecoder;
  this.jwtEncoder = jwtEncoder;
 }

 public String generateToken(Authentication auth){
  Instant now = Instant.now();
  String scope = auth.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.joining());
  JwtClaimsSet claims = JwtClaimsSet.builder()
          .issuer("self")
          .issuedAt(now)
          .expiresAt(now.plus(1, ChronoUnit.HOURS))
          .subject(auth.getName())
          .claim("scope", scope)
          .build();
  return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
 }

 public  String getUsernameFromToken(String token){
  Jwt decoded = jwtDecoder.decode(token);
  String username = decoded.getSubject();
  return username;
 }
}
