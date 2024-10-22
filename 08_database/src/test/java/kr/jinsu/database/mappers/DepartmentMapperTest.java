package kr.jinsu.database.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.jinsu.database.models.Department;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class DepartmentMapperTest {
    // 테스트 클래스에서는 객체 주입을 사용해야 함
    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    @DisplayName("학과 추가 테스트")
    void insertDepartment() {
        // given
        Department input = new Department();
        input.setDname("스프링학과");
        input.setLoc("G강의실");

        int output = departmentMapper.insert(input);

        // 저장된 데이터의 수
        log.debug("output: " + output);
        // 생성된 PK값
        log.debug("new deptno: " + input.getDeptno());
    }

    @Test
    @DisplayName("학과 수정 테스트")
    void updateDepartment() {
        Department input = new Department();
        input.setDeptno(102);
        input.setDname("스프링학과1");
        input.setLoc("G강의실1");

        int output = departmentMapper.update(input);

        log.debug("output: " + output);
    }

    @Test
    @DisplayName("학과 삭제 테스트")
    void deleteDepartment() {
        Department input = new Department();
        input.setDeptno(203);

        int output = departmentMapper.delete(input);
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("하나의 학과 조회 테스트")
    void selectOneDepartment() {
        Department input = new Department();
        input.setDeptno(102);

        Department output = departmentMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("학과 목록 조회 테스트")
    void selectListDepartment() {
        List<Department> output = departmentMapper.selectList(null);

        for(Department item : output) {
            log.debug("output: " + item.toString());
        }
    }
}