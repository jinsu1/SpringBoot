package kr.jinsu.cookie_session.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionController {
    @GetMapping("/session/home")
    public String home(Model model) {
        return "/session/home";
    }

    @GetMapping("/session/login")
    public String home() {
        return "/session/login";
    }
}
