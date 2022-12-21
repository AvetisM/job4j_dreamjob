package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formAddUser")
    public String formAddUser(Model model) {
        return "addUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user) {
        userService.add(user);
        //return "redirect:/users";
        return "redirect:/posts";
    }
}
