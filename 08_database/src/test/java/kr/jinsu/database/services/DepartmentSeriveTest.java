package kr.jinsu.database.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.models.Department;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class DepartmentSeriveTest {
    @Autowired
    private DepartmentService departmentService;
    
    @Test
    @DisplayName("학과 추가 테스트")
    void addServiceTest() throws Exception { 
        Department input = new Department();
        input.setDname("소프트웨어 학과");
        input.setLoc("A강의실");

        Department output = null;

        try {
            output = departmentService.addItem(input);
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
    @DisplayName("학과 수정 테스트")
    void updateServiceTest() throws Exception { 
        Department input = new Department();
        input.setDeptno(102);
        input.setDname("수정된 소프트웨어 학과");
        input.setLoc("수정된 A강의실");

        Department output = null;

        try {
            output = departmentService.editItem(input);
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
    @DisplayName("학과 삭제 테스트")
    void deleteServiceTest() throws Exception {
        Department input = new Department();
        input.setDeptno(203);
    
        try {
            departmentService.deleteItem(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }
    }

    @Test
    @DisplayName("하나의 학과 조회 테스트")
    void selectItemServiceTest() throws Exception {
        Department input = new Department();
        input.setDeptno(102);
    
        Department output = null;
        try {
            output = departmentService.getItem(input);
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
    @DisplayName("학과 목록 조회 테스트")
    void selectListServiceTest() throws Exception {
        List<Department> output = null;

        Department input = new Department();
        input.setDname("학과");

        try {
            output = departmentService.getList(input);
        } catch (ServiceNoResultException e) {
            log.error("SQL문 처리 결과 없음", e);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);      
            throw e;  
        }

        if (output != null){
            for(Department item : output){
                log.debug("output: " + item.toString());
            }
        }
    }
}
