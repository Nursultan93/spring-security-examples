package com.example.jwtautentication.controller;

import com.example.jwtautentication.dto.AuthResponse;
import com.example.jwtautentication.dto.LoginRequest;
import com.example.jwtautentication.model.User;
import com.example.jwtautentication.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public AuthResponse register(@RequestBody User user) {
    return authService.register(user);
  }

  @PostMapping("/sing-in")
  public AuthResponse singIn(@RequestBody LoginRequest request) {
    return authService.singIn(request);
  }

}
