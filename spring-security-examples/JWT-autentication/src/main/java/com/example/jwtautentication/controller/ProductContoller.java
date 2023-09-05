package com.example.jwtautentication.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductContoller {


  @GetMapping
  public String get() {
    return "get products";
  }

  @PostMapping
  public String post() {
    return "save product";
  }

  @PutMapping
  public String put() {
    return "update product";
  }

  @DeleteMapping
  public String delete() {
    return "delete product";
  }

}
