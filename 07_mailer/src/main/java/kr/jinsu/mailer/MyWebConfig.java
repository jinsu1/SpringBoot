package kr.jinsu.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.jinsu.mailer.interceptors.MyInterceptor;

@Configuration
@SuppressWarnings("null")
public class MyWebConfig implements WebMvcConfigurer {
    
    // MyInterceptor 객체를 자동 주입 받는다
    // 이 과정에서 myInterceptor안에 @Autowired로 선언된 UtilHelper 객체도 자동 주입된다.
    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 직접 정의한 MyInterceptor를 Spring에 등록
        InterceptorRegistration ir = registry.addInterceptor(myInterceptor);
        // 해당 경로는 인터셉터가 가로채지 않는다.
        // url /hello 있음.
        ir.excludePathPatterns("/error","/robots.txt", "/favicon.ico","/assets/**");
    }
}
