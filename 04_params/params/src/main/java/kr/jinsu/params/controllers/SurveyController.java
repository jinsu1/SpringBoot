package kr.jinsu.params.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SurveyController {
    /** 설문 시작 - 이름을 입력받는다. */
    @GetMapping("/survey")
    public String index() {
        return "survey/index";
    }
    
    /** 나이를 입력받는다. */
    @PostMapping("/survey/step1")
    public String step1(Model model,
            @RequestParam("name") String name) {
        // 이전 페이지에서 입력받은 내용을 View에 전달한다.
        model.addAttribute("name", name);
        return "survey/step1";
    }
    
    /** 학생/교사 여부를 선택한다. */
    @PostMapping("/survey/step2")
    public String step2(Model model,
            @RequestParam("name") String name,
            @RequestParam("age") int age) {

        // 이전 페이지에서 입력받은 내용을 View에 전달한다.
        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "survey/step2";
    }

    /** 안경 착용 여부를 선택한다. */
    @GetMapping("/survey/step3")
    public String step3(Model model,
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("group") String group) {

        // 이전 페이지에서 입력받은 내용을 View에 전달한다.
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("group", group);
        return "survey/step3";
    }

    /** 최종 결과 페이지 */
    @GetMapping("/survey/step4")
    public String step4(Model model,
            @RequestParam("name") String name,
            @RequestParam("age") int age,
            @RequestParam("group") String group,
            @RequestParam("glasses") String glasses) {

        // 이전 페이지에서 입력받은 내용을 View에 전달한다.
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        model.addAttribute("group", group);
        model.addAttribute("glasses", glasses);

        return "survey/step4";
    }
    
}
