package kr.jinsu.params.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import kr.jinsu.params.helpers.RegexHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PostController {
    @GetMapping("/post/home")
    public String home() {
        return "post/home";
    }

    /** POST 방식의 파라미터를 전송받기 위한 컨트롤러 메서드 */
    @PostMapping("/post/answer")
    public String post(Model model,
            HttpServletResponse response,
            // POST 파라미터 받기 ==> <input type="text" name="user_name">
            @RequestParam("user_name") String name,
            // POST 파라미터 받기 ==> <input type="text" name="user_age">
            // 값이 입력되지 않았을 경우를 대비하여 문자열로 선언해야 한다.
            @RequestParam("user_age") String age) {

        RegexHelper regex = RegexHelper.getInstance();

        try {
            regex.isValue(name, "이름을 입력하세요");
            regex.isKor(name, "이름은 한글로만 입력하세요");
            regex.isValue(age, "나이를 입력하세요");
            regex.isNum(age, "나이는 숫자로 입력하세요");
        } catch (Exception e) {
            // 에러 로그를 기록하고, 사용자에게 안내 메시지를 전달한다.
            log.error("입력값 유효성검사 실패", e);

            // alert 메시지 표시 후 이전 페이지로 이동하는 처리 (Helper에 이식될 예정)
            response.setContentType("text/html; charset=utf-8");

            try {
                PrintWriter out = response.getWriter();
                out.println("<script>");
                out.println("alert('" + e.getMessage() + "')");
                out.println("history.back()");
                out.println("</script>");
                out.flush();
            } catch (IOException e1) {}

            return null;
        }
        
        // 파라미터값을 View에게 전달한다.
        model.addAttribute("name", name);
        model.addAttribute("ageGroup", Integer.parseInt(age) / 10 * 10);

        return "post/result";
    }
    
}
