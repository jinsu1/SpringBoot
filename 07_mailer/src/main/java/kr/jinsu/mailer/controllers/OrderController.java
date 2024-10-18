package kr.jinsu.mailer.controllers;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.jinsu.mailer.helpers.FileHelper;
import kr.jinsu.mailer.helpers.MailHelper;
import kr.jinsu.mailer.helpers.UtilHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;



@Slf4j
@Controller
public class OrderController {
    
    @Autowired
    private FileHelper FileHelper = null;

    @Autowired
    private MailHelper mailHelper = null;

    @Autowired
    private UtilHelper utilHelper = null;

    @GetMapping("/order")
    public String order() {
        return "order";
    }

    @PostMapping("/order_ok")
    public String orderOK(
            HttpServletResponse response,
            @RequestParam("order-name") String orderName,
            @RequestParam("order-email") String orderEmail,
            @RequestParam("order-price") String orderPrice) {

        //1)DB에서 상품정보를 가져왔다고 가정 => 상품명, 주문수량

        String productName = "내가 주문한 상품명";
        int qty = 1;

        //2)결제 일자와 주문번호 생성
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        // 결제 일자
        String orderDate = String.format("%d년 %d월 %d일", year, month, date);

        // 주문 번호 --> 년월일 + 그 날 몇번째 주문인지에 대한 값 (예: 20210801001)
        String orderNumber = String.format("%04d%02d%02d%04d", year, month, date, 78);

        //3)주문정보에 따른 DB처리
        //주문정보를 DB에 저장하는 로직은 생략

        //4)주문정보를 메일로 발송
        String mailTemplatePath = "src/main/resources/mail_templates/order_result.html";
        log.debug("메일 템플릿 경로: " + mailTemplatePath);

        //메일 템플릿 파일 가져오기
        String template = null;

        try {
            template = FileHelper.readString(mailTemplatePath);
        } catch (Exception e) {
            //에러 발생 시 에러 발생 여부를 사용자에게 알리고 전 페이지로 이동
            utilHelper.redirect(response, 500, null, "메일 템플릿을 읽을 수 없습니다.");
            e.printStackTrace();
            return null;
        }

        //메일 템플릿 안의 치환자 처리
        template = template.replace("{{orderName}}", orderName);
        template = template.replace("{{orderNumber}}", orderNumber);
        template = template.replace("{{productName}}", productName);
        template = template.replace("{{qty}}", String.valueOf(qty));
        template = template.replace("{{orderDate}}", orderDate);
        template = template.replace("{{orderPrice}}", String.valueOf(orderPrice));

        //메일 제목
        String subject = orderName + "님의 주문이 완료되었습니다.";

        try {
            mailHelper.sendMail(orderEmail, subject, template);
        } catch (Exception e) {
            //에러 발생 시 에러 발생 여부를 사용자에게 알리고 전 페이지로 이동
            utilHelper.redirect(response, 500, null, "메일 발송에 실패했습니다.");
            e.printStackTrace();
            return null;
        }
        // 주문 결과 페이지로 이동
        return "redirect:/order_result";

    }
        
    @GetMapping("/order_result")
    public String orderResult() {
        return "order_result";
    }
}
