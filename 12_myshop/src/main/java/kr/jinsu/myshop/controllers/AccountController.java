package kr.jinsu.myshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AccountController {
    @GetMapping("/account/after")
    public String after() {
        return "account/after";
    }

    @GetMapping("/account/before")
    public String before() {
        return "account/before";
    }
    
    @GetMapping("/account/find_id")
    public String findId() {
        return "account/find_id";
    }

    @GetMapping("/account/join")
    public String join() {
        return "account/join";
    }

    @GetMapping("/account/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/account/out")
    public String out() {
        return "account/out";
    }

    @GetMapping("/account/reset_pw")
    public String reset_pw() {
        return "account/reset_pw";
    }
}
