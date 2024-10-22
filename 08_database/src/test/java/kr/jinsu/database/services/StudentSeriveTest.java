package kr.jinsu.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.models.Student;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class StudentSeriveTest {
    @Autowired
    private StudentService studentService;
    
    @Test
    @DisplayName("학생 추가 테스트")
    void insertStudent() throws Exception { 
        Student input = new Student();
        input.setName("박진수2");
        input.setUserid("jinsu2");
        input.setGrade(3); 
        input.setIdnum("9609051111111");
        input.setBirthdate("2024-10-10");
        input.setTel("010-1234-1234");
        input.setHeight(175);
        input.setWeight(100);
        input.setDeptno(102);
        input.setProfno(9905);

        Student output = null;

        try {
            output = studentService.addItem(input);
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
    @DisplayName("학생 수정 테스트")
    void updateStudent() throws Exception { 
        Student input = new Student();
        input.setStudno(10102);
        input.setName("수정된 박진수1");
        input.setUserid("수정된 jinsu2");
        input.setGrade(1); 
        input.setIdnum("1234567891011");
        input.setBirthdate("2000-01-01");
        input.setTel("010-1234-1111");
        input.setHeight(175);
        input.setWeight(120);
        input.setDeptno(102);
        input.setProfno(9905);

        Student output = null;

        try {
            output = studentService.editItem(input);
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
    @DisplayName("학생 삭제 테스트")
    void deleteStudent() throws Exception {
        Student input = new Student();
        input.setStudno(20110);
    
        try {
            studentService.deleteItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }
    }

    @Test
    @DisplayName("단일행 교수 조회 테스트")
    void selectOneStudent() throws Exception {
        Student input = new Student();
        input.setStudno(10102);
    
        Student output = null;
        try {
            output = studentService.getItem(input);
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
    @DisplayName("학생 목록 조회 테스트")
    void selectListStudent() throws Exception {
        List<Student> output = null;

        Student input = new Student();
        input.setName("진수");

        try {
            output = studentService.getList(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }

        if (output != null){
            for(Student item : output){
                log.debug("output: " + item.toString());
            }
        }
    }
}
