package com.example.basic.access.autentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api")
public class UserContollerV2 {

  @GetMapping("/user")
  public String user(){
    return "Elcin Orceliyev 2";
  }
}
