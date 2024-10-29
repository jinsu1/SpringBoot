package kr.jinsu.restfulapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Slf4j
// @RestController 어노테이션을 사용하여
// 이 클래스가 RESTful API를 처리하는 컨트롤러임을 명시
@RestController
public class MycalcController{

    @GetMapping("/my_calc")
    public Map<String, Object> sum(
            HttpServletResponse response,
            @RequestParam("x") int x,
            @RequestParam("y") int y){

        int result = x + y;

        Map<String, Object> output = new LinkedHashMap<String, Object>();
        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }

    @PostMapping("/my_calc")
    public Map<String, Object> minus(
            HttpServletResponse response,
            @RequestParam("x") int x,
            @RequestParam("y") int y){

        int result = x - y;

        Map<String, Object> output = new LinkedHashMap<String, Object>();
        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }

    @PutMapping("/my_calc")
    public Map<String, Object> multi(
            HttpServletResponse response,
            @RequestParam("x") int x,
            @RequestParam("y") int y){

        int result = x * y;

        Map<String, Object> output = new LinkedHashMap<String, Object>();
        output.put("x", x);
        output.put("y", y);
        output.put("result", result);

        return output;
    }

    @DeleteMapping("/my_calc")
    public Map<String, Object> divide(
            HttpServletResponse response,
            @RequestParam("x") int x,
            @RequestParam("y") int y){

        double result;

        Map<String, Object> output = new LinkedHashMap<String, Object>();
        output.put("x", x);
        output.put("y", y);

        if( y == 0 || x == 0) {
            output.put("result", "x 또는 y가 0이면 나눌 수 없습니다.");
        } else {
            result = x / y;
            output.put("result", result);
        }

        return output;
    }
}
