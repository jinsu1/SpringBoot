package kr.jinsu.params.helpers;

import jakarta.servlet.http.HttpServletRequest;

public class UtilHelper {
    /** 싱글톤 객체 */
    private static UtilHelper current;

    public static UtilHelper getInstance() {
        if (current == null) {
            current = new UtilHelper();
        }

        return current;
    }

    private UtilHelper() {
        super();
    }

    /**
     * 랜덤 숫자를 생성하는 메서드
     * @param min   - 최소값
     * @param max   - 최대값
     * @return      - 생성된 랜덤 숫자
     */
    public int random(int min, int max) {
        int num = (int)((Math.random() * (max - min + 1)) + min);
        return num;
    }

    /**
     * 클라이언트의 IP 주소를 가져오는 메서드
     * 
     * @param request - HttpServletRequest 객체
     * @return        - IP 주소
     */
    public String getClientIp(HttpServletRequest request) {
        /** 접근한 웹 브라우저의 IP */
        // 출처: https://velog.io/@chullll/spring-boot-IPv4-%EC%84%A4%EC%A0%95 
        String ip = request.getHeader("X-Forwarded-For"); 
        if (ip == null) { 
            ip = request.getHeader("Proxy-Client-IP"); 
            
        } 
        if (ip == null) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직 
            
        } 
        if (ip == null) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
           
        } 
        if (ip == null) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
           
        } 
        if (ip == null) { 
            ip = request.getRemoteAddr(); 
        }

        return ip;
    }
}
