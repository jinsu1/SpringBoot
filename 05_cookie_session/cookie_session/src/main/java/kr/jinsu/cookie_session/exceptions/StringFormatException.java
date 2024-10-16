package kr.jinsu.cookie_session.exceptions;

/**
 * 정규표현식에 부합하지 않음을 의미하는 예외 상황을 처리
 */
public class StringFormatException extends Exception {
    public StringFormatException(String message) {
        super(message);
    }
}
