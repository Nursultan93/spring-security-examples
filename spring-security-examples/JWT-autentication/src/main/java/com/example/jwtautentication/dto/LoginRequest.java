package com.example.jwtautentication.dto;

import lombok.Data;

@Data
public class LoginRequest {

  private String username;
  private String password;
}
