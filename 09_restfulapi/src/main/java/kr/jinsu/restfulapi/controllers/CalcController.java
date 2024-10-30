package kr.jinsu.restfulapi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CalcController {
    
    @GetMapping("/calc")
    public String calc() {
        return "calc.html";
    }
    
}
