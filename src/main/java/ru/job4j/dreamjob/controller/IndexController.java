package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.service.SessionService;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        SessionService.modelAddUser(model, session);
        return "index";
    }
}
