package com.example.basic.access.autentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class UserContoller {

  @GetMapping("/user")
  public String user(){
    return "Elcin Orceliyev";
  }

  @GetMapping("/customer")
  public String customer(){
    return "Murad Eliyev";
  }

  @GetMapping("/client")
  public String client(){
    return "Semed Veliyev";
  }
}
