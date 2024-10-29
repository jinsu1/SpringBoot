package kr.jinsu.restfulapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import kr.jinsu.restfulapi.models.Department;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.mail.javamail.MimeMessageHelper;


@Slf4j
// @RestController 어노테이션을 사용하여
// 이 클래스가 RESTful API를 처리하는 컨트롤러임을 명시
@RestController
public class SimpleRestfulController {

    @Autowired
    JavaMailSender javaMailSender;
    
    /**
     * 간단한 학과 정보를 JSON으로 출력하는 메서드
     * @param response  -HTTP 응답 객체
     * @return  JSON으로 - 변환된  학과 정보
     */
    @GetMapping("/simple_department")
    public Map<String, Object> simpleDepartment(
        HttpServletResponse response) {
        /** 1) JSON 형태로 응답 */
        //Json 형식임을 명시
        response.setContentType("application/json; charset=UTF-8");
        //HTTP 상태코드 (200, 404, 500)
        response.setStatus(200);

        /** 2) CORS 허용
         * 보안에 안좋아서 인증키 등의 보안장치가 필요함
         * 실습을 위해 허용
         */
        response.setHeader("Access-Control-Allow-Methods", "POST, GET PUT DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        // 3) JSON으로 변환 될 MAP 객체 구성
        Map<String, Object> department = new LinkedHashMap<String, Object>();

        department.put("deptno", 101);
        department.put("dname", "컴퓨터학과");
        department.put("loc", "산악협력관");

        // 4) MAP 객체 리턴 -> Spring에 의해 JSON으로 변환됨
        return department;
    }

    /**
     * 간단한 학과 정보를 JSON으로 출력하는 메서드
     * @param response HTTP 응답 객체
     * @return  JSON으로 변환된 학과 정보 목록
     */
    @GetMapping("/simple_department_list")
    public Map<String, Object> simpleDepartmentList(HttpServletResponse response) {

        /** 1) JSON형식 출력을 위한 HTTP 헤더 설정 */
        //JSON 형식임을 명시함
        response.setContentType("application/json; charset=UTF-8");
        //HTTP 상태 코드 설정 (200, 404, 500 등)
        response.setStatus(200);

        /** 2) CORS 허용 */
        // 필요에 따라 추가

        /** 3) JSON으로 변환될 Map객체 구성*/
        Map<String, Object> departments = new LinkedHashMap<String, Object>();

        List<Department> departmentList = new ArrayList<Department>();

        departmentList.add(new Department(101, "컴퓨터공학과", "1호관"));
        departmentList.add(new Department(102, "전자공학과", "2호관"));
        departmentList.add(new Department(103, "기계공학과", "3호관"));
        departmentList.add(new Department(104, "화학공학과", "4호관"));
        
        //List를 Map에 담아서 리턴
        departments.put("items", departmentList);

        return departments;
    }
    
    /**
     * 메일 발송 처리 메서드 (백엔드 기능 담당)
     * @param response  = HTTP 응답 개체
     * @param senderName    - 발신자 이름
     * @param senderEmail   - 발신자 이메일
     * @param receiverName  - 수신자 이름
     * @param receiverEmail - 수신자 이메일
     * @param subject   - 메일 제목
     * @param content   - 메일 내용
     * @return JSON으로 변환된 결과
     */
    @PostMapping("/sendmail")
    public Map<String, Object> sendmail(
        HttpServletResponse response,
        @RequestParam("sender-name") String senderName,
        @RequestParam("sender-email") String senderEmail,
        @RequestParam("receiver-name") String receiverName,
        @RequestParam("receiver-email") String receiverEmail,
        @RequestParam("subject") String subject,
        @RequestParam("content") String content
    ) {
        /** 1) 리턴할 객체 */
        Map<String, Object> output = new LinkedHashMap<String, Object>();

        /** 2) 메일 발송 정보 로그 확인 */
        log.debug("------------------------------------");
        log.debug(String.format("senderName: %s", senderName));
        log.debug(String.format("senderEmail: %s", senderEmail));
        log.debug(String.format("receiverName: %s", receiverName));
        log.debug(String.format("receiverEmail: %s", receiverEmail));
        log.debug(String.format("subject: %s", subject));
        log.debug(String.format("content: %s", content));
        log.debug("------------------------------------");
        
        /** 3) Java Mail 라이브러리를 활용한 메일 발송 */
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        // 제목, 내용, 수신자 설정

        try {
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setTo(new InternetAddress(receiverEmail, receiverName, "UTF-8"));
            helper.setFrom(new InternetAddress(senderEmail, senderName, "UTF-8"));
        } catch (MessagingException e) {
            //에러가 발생했음을 JSON으로 출력
            response.setStatus(500);
            output.put("result", "메일 발송에 실패했습니다.");
            output.put("reason", e.getMessage());
            //에러가 발생한 상황이므로 처리 중단을 위해 return
            return output;
        } catch (UnsupportedEncodingException e) {
            //에러가 발생했음을 JSON으로 출력
            response.setStatus(500);
            output.put("result", "메일 발송에 실패했습니다.");
            output.put("reason", e.getMessage());
            //에러가 발생한 상황이므로 처리 중단을 위해 return
            return output;
        }

        //메일 보내기
        javaMailSender.send(message);

        /** 4) 결과 표시 */
        output.put("result", "메일이 발송되었습니다.");
        
        return output;
    }
    
}
