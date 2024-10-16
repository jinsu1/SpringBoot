package kr.jinsu.hellospring;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@SpringBootApplication
// 웹페이지로써 동작이 가능한 기능을 탑재
@Controller
public class HellospringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellospringApplication.class, args);
	}

    @ResponseBody
    @GetMapping("/hellospring")
    public String hello() {
        String message = "<h1>Hello SpringBoot</h1>";
        message += "<p>안녕하세요 스프링</p>";
        System.out.println(message);
        return message;
    }

    @GetMapping("/now")
    public void world(Model model) {
        model.addAttribute("nowtime", new Date().toString());
    }
    
    @GetMapping("/today")
    public String nice(Model model) {
        model.addAttribute("message1", "스트링부트 View 테스트 입니다.");
        model.addAttribute("message2", "안녕하세요");
        model.addAttribute("message3", "반갑습니다.");

        return "myview";
    }
    

}
