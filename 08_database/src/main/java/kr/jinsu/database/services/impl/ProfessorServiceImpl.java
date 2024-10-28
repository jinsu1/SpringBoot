package kr.jinsu.database.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinsu.database.mappers.ProfessorMapper;
import kr.jinsu.database.mappers.StudentMapper;
import kr.jinsu.database.models.Professor;
import kr.jinsu.database.models.Student;
import kr.jinsu.database.services.ProfessorService;
import lombok.extern.slf4j.Slf4j;

/**
 * 학과 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한 기능 명세
 * 
 * (1) 모든 메서드를 재정의 한 직후 리턴값 먼저 정의
 */
@Slf4j
@Service
public class ProfessorServiceImpl implements ProfessorService {
    @Autowired 
    private ProfessorMapper professorMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Professor addItem(Professor input) throws Exception {
        int rows = 0;

        try {
            rows = professorMapper.insert(input);

            if(rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return professorMapper.selectItem(input);
    }

    @Override
    public Professor editItem(Professor input) throws Exception {
        int rows = 0;

        try {
            rows = professorMapper.update(input);

            if(rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return professorMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Professor input) throws Exception {
        int rows = 0;

        Student student = new Student();
        student.setProfno(input.getProfno());

        try {
            studentMapper.deleteByDeptno(student);
            rows = professorMapper.delete(input);

            if(rows == 0) {
                throw new Exception("삭제된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 삭제에 실패했습니다.", e);
            throw e;
        }

        return rows;
    }
    @Override
    public Professor getItem(Professor input) throws Exception {
        Professor output = null;

       try {
            output = professorMapper.selectItem(input);

            if(output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
       } catch (Exception e) {
        log.error("교수 조회에 실패했습니다.", e);
        throw e;
       }

        return output;
    }

    @Override
    public List<Professor> getList(Professor input) throws Exception {
        List<Professor> output = null;

        try {
            output = professorMapper.selectList(input);
        } catch (Exception e) {
         log.error("교수 목록 조회에 실패했습니다.", e);
         throw e;
        }
 
         return output;
     }
    @Override
    public int getCount(Professor input) throws Exception {
        int output = 0;

        try {
            output = professorMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }
        return output;
    }
}
