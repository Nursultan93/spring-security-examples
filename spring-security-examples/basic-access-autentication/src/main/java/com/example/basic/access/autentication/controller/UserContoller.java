package com.example.basic.access.autentication.controller;

import com.example.basic.access.autentication.model.User;
import com.example.basic.access.autentication.repository.UserRepository;
import com.example.basic.access.autentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class UserContoller {

  private final UserService userService;

  @PostMapping("/register")
  public User create(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @GetMapping("/all")
  public String viewAllUser() {
    return "Bu servisi butun userler gore bilir.";
  }

  @GetMapping("/user")
  public String viewOnlyUserAndAdmin() {
    return "Burdaki melumatlari ancaq user ve admin gore biler";
  }

  @GetMapping("/manager")
  public String viewOnlyManagerAndAdmin() {
    return "Burdaki melumati andcaq meneger ve admin gore biler";
  }

  @GetMapping("/operator")
  public String viewOnlyOperatorAndAdmin() {
    return "Burdaki melumati andcaq operator ve admin gore biler";
  }

  @GetMapping("/admin")
  public String viewOnlyAdmin() {
    return "Burdaki melumati ise ancaq admin gore biler";
  }
}
