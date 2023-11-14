package com.experis.course.springlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  @GetMapping
  public String index(Model model) {
    // recupero la lista di users e la passo al model
    return "users/index";
  }
}
