package ru.job4j.dreamjob.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.dreamjob.service.SessionService;
import javax.servlet.http.HttpSession;

@RestController
public class IndexController {
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        SessionService.modelAddUser(model, session);
        return "index";
    }
}
