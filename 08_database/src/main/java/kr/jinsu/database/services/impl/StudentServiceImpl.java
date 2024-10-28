package kr.jinsu.database.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.jinsu.database.mappers.StudentMapper;
import kr.jinsu.database.models.Student;
import kr.jinsu.database.services.StudentService;
import lombok.extern.slf4j.Slf4j;

/**
 * 학과 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한 기능 명세
 * 
 * (1) 모든 메서드를 재정의 한 직후 리턴값 먼저 정의
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student addItem(Student input) throws Exception {
        int rows = 0;
        

        try {
            rows = studentMapper.insert(input);

            if(rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return studentMapper.selectItem(input);
    }

    @Override
    public Student editItem(Student input) throws Exception {
        int rows = 0;

        try {
            rows = studentMapper.update(input);

            if(rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return studentMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Student input) throws Exception {
        // delete문 수행 --> 리턴되는 값은 수정된 데이터의 수
        int rows = 0;

         try {
            rows = studentMapper.delete(input);

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
    public Student getItem(Student input) throws Exception {
        Student output = null;

         try {
            output = studentMapper.selectItem(input);

            if(output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
       } catch (Exception e) {
        log.error("학생 조회에 실패했습니다.", e);
        throw e;
       }

        return output;
    }

    @Override
    public List<Student> getList(Student input) throws Exception {
        List<Student> output = null;

        try {
            output = studentMapper.selectList(input);
        } catch (Exception e) {
         log.error("학생 목록 조회에 실패했습니다.", e);
         throw e;
        }

        return output;
    }

    @Override
    public int getCount(Student input) throws Exception {
        int output = 0;

        try {
            output = studentMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }
        return output;
    }
}
