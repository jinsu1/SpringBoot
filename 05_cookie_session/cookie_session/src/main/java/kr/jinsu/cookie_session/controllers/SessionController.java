package kr.jinsu.cookie_session.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.jinsu.cookie_session.helpers.UtilHelper;
import kr.jinsu.cookie_session.models.Member;
import java.io.PrintWriter;
import java.io.IOException;

@Controller
public class SessionController {

    /** 세션 저장을 위한 작성 페이지
     * 작성 페이지에 다녀온 후에는 저장된 세션값을 꺼내서 view에 전달한다.
     */
    @GetMapping("/session/home")
    public String home(Model model, HttpServletRequest request) {

        //1) request 객체를 사용해서 세션 객체 만들기
        HttpSession session = request.getSession();

        //2) 세션 값 가져오기
        // 값으로 가져오는 것은 Object 타입이다 => unboxing을 해야한다
        // 기본 데이터 타입을 가져올 때는 Wrapper 클래스를 사용한다
        String userName = (String) session.getAttribute("user_name");
        Integer userAge = (Integer) session.getAttribute("user_age");

        //3) View에 전달할 데이터 저장하기
        model.addAttribute("userName", userName);
        model.addAttribute("userAge", userAge);
        return "/session/home";
    }

    /**세션저장 처리 페이지 */
    @PostMapping("/session/save")
    public String save(HttpServletRequest request,
        @RequestParam("user_name") String userName,
        @RequestParam("user_age") int userAge) {
        
        //1) request 객체를 사용해서 세션 객체만들기
        HttpSession session = request.getSession();

        //2)세션값 저장하기
        //값으로 저장하는 것은 Object 타입이다. --> Boxing 처리가 일어난다
        // 기본데이터는 타입을 저장할 때는 Wrapper 클래스를 사용한다.
        session.setAttribute("user_name", userName);
        session.setAttribute("user_age", userAge);

        //3) 원래의 페이지로 되돌아간다.
        return "redirect:/session/home";
        }

    /** 심플 로그인 폼 */
    @GetMapping("/session/login")
    public String home(
        Model model,
        //쿠키값을 가져오기 위한 어노테이션
        //required=false : 해당 쿠키가 없을 경우 null로 처리
        @CookieValue(value="remmemberId", required=false) String remmemberIdCookie) {

        //쿠키값이 존재하는 경우, 해당 값을 View에 전달
        model.addAttribute("remmemberId", remmemberIdCookie);

        return "/session/login";
    }

    /**
     * 로그인 처리 페이지
     */
    @SuppressWarnings("null")
    @PostMapping("/session/login_ok")
    public String loginOk(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("user_id") String userId,
            @RequestParam("user_password") String userPassword,
            @RequestParam(value="remmember_id", defaultValue="N") String rememberId) {
        /**
         * 1)로그인 가능 여부 검사
        */   
        if(!userId.equals("jinsu") || !userPassword.equals("1234"))  {
            /**
             * 알림 메시지 표시 후 바로 이전 페이지로 이동 ==> helper에 이식 예정
             * http 403 forbidden 클라이언트 오류 상태 응답 코드는 서버에 요청이 전달되었지만,
             * 권한 때문에 거절되었다는 것을 의미
             */
            response.setStatus(403);
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println("<script>");
            out.println("alert('아이디 또는 비밀번호가 일치하지 않습니다.');");
            out.println("history.back();");
            out.println("</script>");
            out.flush();
            return null;
        }
        
        /** 
         * 2) 로그인한 회원의 정보를 생성한다
         * 여기서는 POJO클래스의 객체를 강제로 생성하지만,
         * 실제로는 DB에서 회원정보를 가져와야 한다.
         * MyBatis를 사용하면 DB에서 가져온 회원정보를 객체로 만드렁서 리턴받을 수 있다.
         */
        Member member = new Member();
        member.setUserId(userId);
        member.setUserPw(userPassword);

        /** 
         * 1) requset 객체를 사용해서 세션 객체 만들기
         */
        HttpSession session = request.getSession();

        /**
         * 2) 세션값 저장하기
         * 값으로 저장하는 것은 Object 타입이다. 
         */
        session.setAttribute("memberInfo", member);

        // 3) 아이디 기억하기 처리
        if(rememberId.equals("Y")) {
            //아이디를 7일간 쿠키에 저장한다.
            UtilHelper.getInstance().writeCookie(response, "rememberId", userId, 60 * 60 * 24 * 7);
        }

        // 3) 원래의 페이지로 되돌아간다.
        return "redirect:/session/login";
    }

    /**
     * 로그아웃 처리 페이지
     * 로그인이 된 상태에서만 접근 가능해야 한다.
     * 로그인이 된 상태 : 세션에 memberInfo가 저장되어 있는 상태
     */

     @GetMapping("/session/logout")
     public String logout(
        HttpServletResponse response,
        HttpServletRequest request) {

        /*1) 세션의 존재 여부 확인 */
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("memberInfo");

        if(member == null) {
            response.setStatus(403);
            response.setContentType("text/html; charger=UTF-8");

            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println("<script>");
            out.println("alert('로그인 후에 접근 가능합니다.');");
            out.println("history.back();");
            out.println("</script>");
            out.flush();
            return null;
        }

        /**
         * 2) 세션값 저장하기
         * 단일 값 삭제하기
         * sesstion.removeAttribute("memberInfo");
         * 현재 접속중이 클라이언트에게 종속된 모든 세션 일괄 삭제
         */
        session.invalidate();

        // 3) 원래의 페이지로 되돌아간다.
        return "redirect:/session/login";
    }
}
