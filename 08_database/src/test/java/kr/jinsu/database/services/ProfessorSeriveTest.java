package kr.jinsu.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.models.Professor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProfessorSeriveTest {
    @Autowired
    private ProfessorService professorService;
    
    @Test
    @DisplayName("교수 추가 테스트")
    void insertProfessor() throws Exception { 
        Professor input = new Professor();
        input.setName("박진수1");
        input.setUserid("jinsu1");
        input.setPosition("교수");
        input.setSal(3000);
        input.setHiredate("2024-10-20");
        input.setComm(1000);
        input.setDeptno(102);

        Professor output = null;

        try {
            output = professorService.addItem(input);
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
    @DisplayName("교수 수정 테스트")
    void updateProfessor() throws Exception { 
        Professor input = new Professor();
        input.setProfno(9901);
        input.setName("박진수");
        input.setUserid("jinsu");
        input.setPosition("교수");
        input.setSal(3000);
        input.setHiredate("2024-10-20");
        input.setComm(1000);
        input.setDeptno(102);

        Professor output = null;

        try {
            output = professorService.editItem(input);
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
    @DisplayName("교수 삭제 테스트")
    void deleteProfessor() throws Exception {
        Professor input = new Professor();
        input.setProfno(9901);
    
        try {
            professorService.deleteItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }
    }

    @Test
    @DisplayName("단일행 교수 조회 테스트")
    void selectOneProfessor() throws Exception {
        Professor input = new Professor();
        input.setProfno(9913);
    
        Professor output = null;
        try {
            output = professorService.getItem(input);
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
    @DisplayName("교수 목록 조회 테스트")
    void selectListProfessor() throws Exception {
        List<Professor> output = null;

        Professor input = new Professor();
        input.setName("진수");

        try {
            output = professorService.getList(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }

        if (output != null){
            for(Professor item : output){
                log.debug("output: " + item.toString());
            }
        }
    }
}
