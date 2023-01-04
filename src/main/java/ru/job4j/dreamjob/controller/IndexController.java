package ru.job4j.dreamjob.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

@RestController
public class IndexController {
    @GetMapping(value = {"/", "/index"})
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User(0, "Гость", "", "");
        }
        model.addAttribute("user", user);
        return "index";
    }
}
