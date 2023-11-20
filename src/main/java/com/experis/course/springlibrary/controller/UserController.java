package com.experis.course.springlibrary.controller;

import com.experis.course.springlibrary.model.User;
import com.experis.course.springlibrary.repository.UserRepository;
import com.experis.course.springlibrary.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @GetMapping
  public String index(Authentication authentication, Model model) {

    DatabaseUserDetails principal = (DatabaseUserDetails) authentication.getPrincipal();
    User loggedUser = userRepository.findById(principal.getId()).get();
    model.addAttribute(loggedUser.getFirstName());
    model.addAttribute(loggedUser.getLastName());

    // recupero la lista di users e la passo al model
    return "users/index";
  }


}
