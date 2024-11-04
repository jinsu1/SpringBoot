package kr.jinsu.crud.services;

import java.util.List;

import kr.jinsu.crud.exceptions.MyBatisException;
import kr.jinsu.crud.exceptions.ServiceNoResultException;
import kr.jinsu.crud.models.Department;

/**
 * 하나의 처리를 위해서 두 개 이상의 Mapper 기능을 호출할 필요가 있을 경우
 * 이 인터페이스 구현체(Impl)을 통해서 처리한다.
 * 
 * 1) delete 기능의 경우 삭제된 데이터의 수를 의미하는 int를 리턴
 * 2) 목록 조회 기능의 경우 List<DTO클래스>를 리턴
 * 3) 입력, 수정, 상세조회의 경우는 DTO 클래스를 리턴
 */


public interface DepartmentService {
    /**
     * 학과 정보를 새로 저장하고 저장된 정보를 조회하여 리턴한다.
     * @param params    - 저장할 정보를 담고 있는 Beans
     * @return  Department - 저장된 데이터
     * @throws MyBatisException - SQL처리에 실패한 경우
     * @throws ServiceNoResultException - 저장된 데이터가 없는 경우
     */
    public Department addItem(Department params) throws ServiceNoResultException, Exception;

    /**
     *  학과 정보를 수정하고 수정된 정보를 조회하여 리턴한다. 
     * @param params    - 수정할 정보를 담고 있는 Beans
     * @return  Department - 수정된 데이터
     * @throws MyBatisException  - SQL처리에 실패한 경우
     * @throws ServiceNoResultException - 수정된 데이터가 없는 경우
     */
    public Department editItem(Department params) throws ServiceNoResultException, Exception;

    /**
     * 학과 정보를 삭제한다
     * @param params    - 삭제할 정보를 담고 있는 Beans
     * @return int  - 삭제할 정보의 개수
     * @throws MyBatisException - SQL처리에 실패한 경우
     * @throws ServiceNoResultException - 삭제된 데이터가 없는 경우
     */
    public int deleteItem(Department params) throws ServiceNoResultException, Exception;

    /**
     * 단일 학과 정보 조회
     * @param params
     * @return
     * @throws MyBatisException
     * @throws ServiceNoResultException
     */
    public Department getItem(Department params) throws ServiceNoResultException, Exception;

    /**
     * 전체 학과 정보 조회
     * @param params
     * @return
     * @throws ServiceNoResultException
     * @throws Exception
     */
    public List<Department> getList(Department params) throws ServiceNoResultException, Exception;

    /**
     * 학과 목록에 대한 카운트 결과를 반환한다.
     * @param input - 조회할 학과의 학과번호를 담고 있는 Beans
     * @return  Department - 조회된 데이터
     * @throws Exception    - SQL처리에 실패한 경우
     */
    public int getCount(Department input) throws Exception;
} 
