package com.example.jwtautentication.controller;

import com.example.jwtautentication.dto.LoginRequest;
import com.example.jwtautentication.model.User;
import com.example.jwtautentication.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final UserServiceImpl userService;

  @PostMapping("/register")
  public User register(@RequestBody User user) {
    return userService.create(user);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest request) {
    return userService.autenticate(request);
  }
}
