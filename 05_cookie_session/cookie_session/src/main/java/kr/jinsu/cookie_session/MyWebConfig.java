package kr.jinsu.cookie_session;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.jinsu.cookie_session.interceptors.MyInterceptor;

@Configuration
@SuppressWarnings("null")
public class MyWebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 직접 정의한 MyInterceptor를 Spring에 등록
        InterceptorRegistration ir = registry.addInterceptor(new MyInterceptor());
        // 해당 경로는 인터셉터가 가로채지 않는다.
        // url /hello 있음.
        ir.excludePathPatterns("/hello","/world","/error","/robots.txt",
        "/favicon.ico","/assets/**");
    }
}
