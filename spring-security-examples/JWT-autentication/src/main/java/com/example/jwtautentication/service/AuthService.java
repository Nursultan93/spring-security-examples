package com.example.jwtautentication.service;

import com.example.jwtautentication.dto.AuthResponse;
import com.example.jwtautentication.dto.LoginRequest;
import com.example.jwtautentication.model.User;
import com.example.jwtautentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(User user) {

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);

    String token = jwtService.generateToken(savedUser);

    return new AuthResponse(token);
  }

  public AuthResponse singIn(LoginRequest request) {

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword())
    );

    var user = userRepository.findUserByUsername(request.getUsername())
        .orElseThrow(()-> new UsernameNotFoundException("User not foud!"));

    var token = jwtService.generateToken(user);

    return new AuthResponse(token);

  }

}
