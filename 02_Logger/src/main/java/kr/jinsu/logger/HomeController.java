package kr.jinsu.logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import ua_parser.Client;
import ua_parser.Parser;
import org.springframework.web.bind.annotation.GetMapping;



@Slf4j              // log 객체 생성 (lombok)
@Controller         // 컨트롤러로 사용
public class HomeController {
    @GetMapping("/")
    public String helloworld(Model model, HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info(">>>> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
            log.info(">>>> WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info(">>>> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        model.addAttribute("clientIp", ip);
        log.debug(">>>>>> Client IP : " + ip);

        String ua = request.getHeader("user-agent");
        model.addAttribute("ua", ua);
        log.debug(">>>>>> User-Agent : " + ua);

        Parser uaParser = new Parser();

        Client c = uaParser.parse(ua);

        model.addAttribute("uac", c.toString());
        log.debug(">>>>>> User-Agent : " + c.toString());

        model.addAttribute("browserFamily", c.userAgent.family);
        model.addAttribute("browserMajor", c.userAgent.major);
        model.addAttribute("browserMinor", c.userAgent.minor);
        log.debug(c.userAgent.family); 
        log.debug(c.userAgent.major); 
        log.debug(c.userAgent.minor);

        model.addAttribute("osFamily", c.os.family);
        model.addAttribute("osMajor", c.os.major);
        model.addAttribute("osMinor", c.os.minor);
        log.debug(c.os.family); 
        log.debug(c.os.major); 
        log.debug(c.os.minor); 

        model.addAttribute("deviceFamily", c.device.family);
        log.debug("device.family : " + c.device.family);

        String url = request.getRequestURL().toString();

        String methodName = request.getMethod();

        String queryString = request.getQueryString();

        if(queryString != null) {
            url = url + "?" + queryString;
        }

        model.addAttribute("method", methodName);
        model.addAttribute("url", url);

        return "index";
    }
}
