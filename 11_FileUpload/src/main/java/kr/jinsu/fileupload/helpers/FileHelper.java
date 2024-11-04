package kr.jinsu.fileupload.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 파일 입출력을 위한 기능을 제공하는 클래스
 */
@Slf4j
@Component
public class FileHelper {

    public void write(String filePath, byte[] data) throws Exception{
        OutputStream os = null;
        try {
            //저장할 파일 스트림 생성
            os = new FileOutputStream(filePath);
            //파일 쓰기
            os.write(data);
        } catch (FileNotFoundException e) {
            log.error("파일을 찾을 수 없습니다.", e);
            throw e;
        } catch (IOException e) {
            log.error("파일을 쓸 수 없습니다.", e);
            throw e;
        } catch (Exception e) {
            log.error("파일 입출력 오류가 발생했습니다.", e);
            throw e;
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    log.error("파일을 닫는 중 오류가 발생했습니다.", e);
                    throw e;
                }
            }
        }
    }

    /**
     * 파일에 문자열을 읽는 메서드
     * @param filePath      -파일 경로
     * @return 파일에 저장된 데이터
     * @throws Exception    -파일 입출력 예외
     */
    public byte[] read(String filePath) throws Exception {
        byte[] data = null;

        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            data = new byte[is.available()];
            is.read(data);
            } catch (FileNotFoundException e) {
                log.error("파일을 찾을 수 없습니다.", e);
                throw e;
            } catch (IOException e) {
                log.error("파일을 쓸 수 없습니다.", e);
                throw e;
            } catch (Exception e) {
                log.error("파일 입출력 오류가 발생했습니다.", e);
                throw e;
            } finally {
            if( is != null) {
                try {    is.close();
            } catch (IOException e) {
                log.error("파일을 닫는 중 오류가 발생했습니다.", e);
                throw e;
                } 
            }
        }
        return data;
    }

    /**
     * 파일에 문자열을 쓰는 메서드
     * @param filePath  -파일 경로
     * @param content   -저장할 문자열
     * @throws Exception    -파일 입출력 예외
     */
    public void writeString(String filePath, String content) throws Exception {
        try {
            this.write(filePath, content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("지원하지 않는 인코딩입니다.", e);
            throw e;
        }
    }

    public String readString(String filePath) throws Exception {
        String content = null;

        try {
            byte[] data = read(filePath);
            content = new String(data, "utf-8");
        } catch (Exception e) {
            log.error("지원하지 않는 인코딩입니다.", e);
            throw e;
        }
        return content;
    }
}