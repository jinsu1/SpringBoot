package kr.jinsu.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.models.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class StudentMapperTest {
    @Autowired
    private StudentMapper studentMapper;

    @Test 
    @DisplayName("학생 추가 테스트")
    void insertTest(){
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
        
        int output = studentMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + output);
        // 생성된 pk값
        log.debug("new Profno: "+ input.getProfno());
    }
    
    @Test
    @DisplayName("학생 수정 테스트")
    void updateTest(){
        Student input = new Student();
        input.setStudno(10101);
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
        
        int output = studentMapper.update(input);

        log.debug("output: " + output);
    }
    
    @Test
    @DisplayName("학생 삭제 테스트")
    void deleteTest(){
        Student input = new Student();
        input.setStudno(10204);

        int output = studentMapper.delete(input);
        log.debug("output: " + output);
    }
    
    @Test
    @DisplayName("학생 단일행 조회 테스트")
    void selectOneTest(){
        Student input = new Student();
        input.setStudno(50510);

        Student output = studentMapper.selectItem(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("학과 다중행 조회 테스트")
    void selectListTest(){
        List<Student> output = studentMapper.selectList(null);
        for (Student item : output){
            log.debug("output: " + item.toString());
        }
    }
}
