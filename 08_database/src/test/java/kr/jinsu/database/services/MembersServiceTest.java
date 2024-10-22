package kr.jinsu.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.models.Members;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MembersServiceTest {
    @Autowired
    private MembersService membersService;
    
    @Test
    @DisplayName("회원 추가 테스트")
    void insertMembers() throws Exception { 
        Members input = new Members();
        input.setUserId("jinsu5");
        input.setUserPw("aaa111");
        input.setUserName("박진수5");
        input.setEmail("wlstn4205@naver.com");
        input.setPhone("010-3306-4205");
        input.setBirthday("1996-09-05");
        input.setGender("M");
        input.setPostcode("16125");
        input.setAddr1("경기도 수원시5");
        input.setAddr2("우만동5");
        input.setPhoto("photo");

        Members output = null;

        try {
            output = membersService.addItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;  
        }

        if (output != null){
            log.debug("output: " + output.toString());
        }
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMembers() throws Exception { 
        Members input = new Members();
        input.setId(6);
        input.setUserId("수정된 jinsu2");
        input.setUserPw("42051234");
        input.setUserName("수정된 진수2");
        input.setEmail("수정된4205@naver.com");
        input.setPhone("010-9999-9999");
        input.setBirthday("1996-09-06");
        input.setGender("F");
        input.setPostcode("15026");
        input.setAddr1("수정된 수원시 팔달구2");
        input.setAddr2("수정된 우만2동");
        input.setPhone("photo2");

        Members output = null;

        try {
            output = membersService.editItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }

        if (output != null){
            log.debug("output: " + output.toString());
        }
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteMembers() throws Exception {
        Members input = new Members();
        input.setId(10);
    
        try {
            membersService.deleteItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }
    }

    @Test
    @DisplayName("단일행 회원 조회 테스트")
    void selectOneMembers() throws Exception {
        Members input = new Members();
        input.setId(6);
    
        Members output = null;
        try {
            output = membersService.getItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }

        if (output != null){
            log.debug("output: " + output.toString());
        }
    }

    @Test
    @DisplayName("회원 목록 조회 테스트")
    void selectListMembers() throws Exception {
        List<Members> output = null;

        Members input = new Members();
        input.setUserId("jinsu");

        try {
            output = membersService.getList(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }

        if (output != null){
            for(Members item : output){
                log.debug("output: " + item.toString());
            }
        }
    }
}
