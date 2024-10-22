package kr.jinsu.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.models.Professor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProfessorMapperTest {
    // 테스트 클래스에서는 객체 주입을 사용해야 함
    @Autowired
    private ProfessorMapper professorMapper;

    @Test
    @DisplayName("교수 추가 테스트")
    void insertProfessor() {
        // given
        Professor input = new Professor();
        input.setName("박진수1");
        input.setUserid("jinsu1");
        input.setPosition("교수");
        input.setSal(3000);
        input.setHiredate("2024-10-20");
        input.setComm(1000);
        input.setDeptno(102);

        int output = professorMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + output);
        // 생성된 PK값
        log.debug("new profno: " + input.getProfno());
    }

    @Test
    @DisplayName("교수 수정 테스트")
    void updateProfessor() {
        Professor input = new Professor();
        input.setProfno(9901);
        input.setName("박진수");
        input.setUserid("jinsu");
        input.setPosition("교수");
        input.setSal(3000);
        input.setHiredate("2024-10-20");
        input.setComm(1000);
        input.setDeptno(102);

        int output = professorMapper.update(input);

        log.debug("output: " + output);
    }

    @Test
    @DisplayName("교수 삭제 테스트")
    void deleteProfessor() {
        Professor input = new Professor();
        input.setProfno(9914);

        int output = professorMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 교수 조회 테스트")
    void selectOneProfessor() {
        Professor input = new Professor();
        input.setProfno(9901);

        Professor output = professorMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("교수 목록 조회 테스트")
    void selectListProfessor() {
        List<Professor> output = professorMapper.selectList(null);

        for(Professor item : output) {
            log.debug("output: " + item.toString());
        }
    }
}