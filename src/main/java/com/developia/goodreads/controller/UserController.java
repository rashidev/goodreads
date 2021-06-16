package com.developia.goodreads.controller;

import com.developia.goodreads.dao.entity.UsersEntity;
import com.developia.goodreads.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new UsersEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") UsersEntity user, BindingResult bindingResult) {

        //TODO: Check fName lName email validations
        UsersEntity existingUser = userService.getUserByLogin(user.getLogin());
        if (existingUser != null) {
            bindingResult.rejectValue("login", "error.user", "User already exist");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.register(user);
        return "redirect:/users/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
