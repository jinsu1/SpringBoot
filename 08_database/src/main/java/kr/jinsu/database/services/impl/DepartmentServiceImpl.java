package kr.jinsu.database.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.jinsu.database.exceptions.ServiceNoResultException;
import kr.jinsu.database.mappers.DepartmentMapper;
import kr.jinsu.database.mappers.ProfessorMapper;
import kr.jinsu.database.mappers.StudentMapper;
import kr.jinsu.database.models.Department;
import kr.jinsu.database.models.Professor;
import kr.jinsu.database.models.Student;
import kr.jinsu.database.services.DepartmentService;

/**
 * 학과 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한 기능 명세
 * 
 * (1) 모든 메서드를 재정의 한 직후 리턴값 먼저 정의
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired 
    private ProfessorMapper professorMapper;

    @Autowired
    private StudentMapper studentMapper;
    
    @Override
    public Department addItem(Department input) throws ServiceNoResultException, Exception {
        int rows = departmentMapper.insert(input);
        

        if(rows == 0) {
            throw new ServiceNoResultException("저장된 데이터가 없습니다.");
        }

        return departmentMapper.selectItem(input);
    }

    @Override
    public Department editItem(Department input) throws ServiceNoResultException, Exception {
        int rows = departmentMapper.update(input);

        if(rows == 0) {
            throw new ServiceNoResultException("수정된 데이터가 없습니다.");
        }

        return departmentMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Department input) throws ServiceNoResultException, Exception {
        //학과 데이터 삭제를 위해 참조관계에 있는 자식 데이터를 순서대로 삭제
        Student student = new Student();
        student.setDeptno(input.getDeptno());
        studentMapper.deleteByDeptno(student);

        Professor professor = new Professor();
        professor.setDeptno(input.getDeptno());
        professorMapper.deleteByDeptno(professor);

        // delete문 수행 --> 리턴되는 값은 수정된 데이터의 수
        int rows = departmentMapper.delete(input);

        if(rows == 0) {
            throw new ServiceNoResultException("삭제된 데이터가 없습니다.");
        }

        return rows;
    }

    @Override
    public Department getItem(Department input) throws ServiceNoResultException, Exception {
        Department output = departmentMapper.selectItem(input);

        if(output == null) {
            throw new ServiceNoResultException("조회된 데이터가 없습니다.");
        }

        return output;
    }

    @Override
    public List<Department> getList(Department input) throws ServiceNoResultException, Exception {
        return departmentMapper.selectList(input);
    }
}
