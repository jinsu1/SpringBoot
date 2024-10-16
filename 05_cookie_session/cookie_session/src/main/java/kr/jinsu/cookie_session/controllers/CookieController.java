package kr.jinsu.cookie_session.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.jinsu.cookie_session.helpers.UtilHelper;

@Controller
public class CookieController {

    @GetMapping("/cookie/home")
    public String home(Model model, 
            @CookieValue(value = "name", defaultValue = "") String myCookieName,
            @CookieValue(value = "age", defaultValue =  "0") int myCookieAge) {
        //컨트롤러에서 쿠키를 식별하기 위한 처리
        try {
            myCookieName = URLDecoder.decode(myCookieName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //추출한 값을 View에게 전달
        model.addAttribute("myCookieName", myCookieName);
        model.addAttribute("myCookieAge", myCookieAge);

        return "/cookie/home";
    }

    /** 쿠키를 저장하기 위한 action 페이지 */
    @PostMapping("/cookie/save")
    public String save(HttpServletResponse response,
            @RequestParam(value = "cookie_name", defaultValue = "") String cookieName,
            @RequestParam(value = "cookie_time", defaultValue = "0") int cookieTime,
            @RequestParam(value = "cookie_var", defaultValue = "") String cookieVar) {

        //파라미터를 쿠키에 저장하기 위한 URLEncoding처리
        //쿠키 저장을 위해서는 URLEncoding 처리가 필요하다.
        if (!cookieVar.equals("")) {
            try {
                cookieVar = URLEncoder.encode(cookieVar, "utf-8");                    
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // 쿠키 저장하기
        // 저장할 쿠키 객체 생성
        Cookie cookie = new Cookie(cookieName, cookieVar);

        cookie.setPath("/");

        //유효 도메신 www.naver.com인경우 naver.com으로 설정

        //유효시간 설정(0이하면 즉시 삭제, 초 단위)
        //설정하지 않을 경우 브라우저 닫기 전까지 유지됨
        cookie.setMaxAge(cookieTime);

        response.addCookie(cookie);

        //강제 페이지 이동
        //이 페이지에 머물렀다는 사실이 웹 브라우저의 history에 남지 않는다.
        return "redirect:/cookie/home";
    }

    /** 팝업 창 제어 페이지 */
    @GetMapping("/cookie/popup")
    public String popup(Model model,
            @CookieValue(value = "no-open", defaultValue = "") String noOpen) {
        
        //쿠키값을 View에게 전달
        model.addAttribute("noOpen", noOpen);

        return "/cookie/popup";
    }

    @PostMapping("/cookie/popup_close")
    public String popupClose(HttpServletResponse response,
            @RequestParam(value = "no-open", defaultValue = "") String noOpen) {

        /** 1) 쿠키 저장하기
         * 60초 간 유효한 쿠키 설정
         * 실제 상용화 시에는 domain을 설정해야 한다.
         */
        UtilHelper.getInstance().writeCookie(response, "no-open", noOpen, 15);

        // 2) 강제 페이지 이동
        return "redirect:/cookie/popup";
    }
}
