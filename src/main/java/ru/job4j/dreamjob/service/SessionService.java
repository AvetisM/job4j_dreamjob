package ru.job4j.dreamjob.service;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public final class SessionService {
    public SessionService() {
        throw new java.lang.UnsupportedOperationException(
                "Utility class and cannot be instantiated");
    }

    public static void modelAddUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User(0, "Гость", "", "");
        }
        model.addAttribute("user", user);
    }
}
