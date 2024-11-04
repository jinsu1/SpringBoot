package kr.jinsu.fileupload.helpers;

import java.util.regex.Pattern;
import kr.jinsu.fileupload.exceptions.StringFormatException;

public class RegexHelper {
    private static RegexHelper current;

    public static RegexHelper getInstance() {
        if (current == null) {
            current = new RegexHelper();
        }

        return current;
    }

    private RegexHelper() {
        super();
    }
    
    /**
     * 주어진 문자열이 공백이거나 null 인지 검사
     * 
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isValue(String str, String message) throws StringFormatException {
        if(str == null || str.trim().equals("")) {
            throw new StringFormatException(message);
        }
    }
   
    /**
     * 숫자에 대한 형식 검사
     * @param str
     * @param message
     * @throws StringFormatException
     */
    public void isNum(String str, String message) throws StringFormatException{
        if(!Pattern.matches("^[0-9]*$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * 영문으로만 구성되었는지에 대한 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isEng(String str, String message) throws StringFormatException {
        if(!Pattern.matches("^[a-zA-Z]*$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * 한글로만 구성되었는지에 대한 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isKor(String str, String message) throws StringFormatException {
        if (!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * 영문과 숫자로만 구성되었는지에 대한 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isEngNum(String str, String message) throws StringFormatException {
        if (!Pattern.matches("^[a-zA-Z0-9]]*$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * 한글과 숫자로만 구성되었는지에 대한 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isKorNum(String str, String message) throws StringFormatException {
        if (!Pattern.matches("^[ㄱ-ㅎ가-힣0-9]*$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * 이메일 형식인지에 대한 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isEmail(String str, String message) throws StringFormatException {
        if (!Pattern.matches("^[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * "-"없이 핸드폰 번호 형식인지에 대한 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isCellphone(String str, String message) throws StringFormatException {
        if (!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * "-"없이 진전화번호인지 형식 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isTel(String str, String message) throws StringFormatException {
        if (!Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str)) {
            throw new StringFormatException(message);
        }
    }

    /**
     * "-"없이 집전화번호 혹은 핸드폰 번호 둘 중 하나를 충족하는지 검사
     * @param str       -검사할 문자열
     * @param message   -조건을 충족하지 않은 경우 표시할 에러 메세지
     * @throws StringFormatException
     */
    public void isPhone(String str, String message) throws StringFormatException {
        boolean cellPhone = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str);
        boolean telPhone = Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);

        if(!cellPhone && !telPhone) {
            throw new StringFormatException(message);
        }
    }
}
