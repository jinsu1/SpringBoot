package kr.jinsu.crud.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.jinsu.crud.models.Department;

@Mapper
public interface DepartmentMapper {
    
    /**
     * 학과 정보를 입력한다.
     * PK값은 파라미터로 전달된 input 객체에 참조로 처리된다.
     * @param input - 입력할 학과 정보에 대한 모델 객체
     * @return  입력된 데이터 수
     */
    @Insert("insert into department (dname, loc) values (#{dname}, #{loc})")
    // insert 문에서 필요한 PK에 대한 옵션 정의
    // --> useGeneratedKeys : auto_increment가 적용된 테이블인 경우 사용
    // --> keyProperty : 파라미터로 전달되는 model 객체에서 PK에 대응되는 멤버변수
    // --> keyColumn : 테이블의 PK 컬럼 명
    @Options(useGeneratedKeys = true, keyProperty = "deptno", keyColumn = "deptno")
    int insert(Department input);

    /**
     * 
     * @param input -   수정할 데이터에 대한 모델 객체
     * @return  수정된 데이터 수
     */
    @Update("update department set dname=#{dname}, loc=#{loc} where deptno=#{deptno}")
    int update(Department input);


    @Delete("delete from department where deptno=#{deptno}")
    int delete(Department input);

    @Select("select deptno, dname, loc from department where deptno=#{deptno}")
    /** 조회 결과와 리턴할 model 객체를 연결하기 위한 규칙 정의
     * --> property : model 객체의 멤버변수 이름
     * --> column : select 문에 명시된 필드 이름 (AS 옵션을 사용할 경우 별칭으로 명시)
     */
    @Results(id="departmentMap", value={
        @Result(property = "deptno", column = "deptno"),
        @Result(property = "dname", column = "dname"),
        @Result(property = "loc", column = "loc")
    })
    Department selectItem(Department input);


    /**
     * 다중행 조회를 수행하는 메서드 정의
     * @param input - 조회 조건을 담고 있는 객체
     * @return  조회 결과를 담은 컬렉션
     */
    @Select("<script>" +
        "select deptno, dname, loc from department " +
        "<where>" +
        "<if test='dname != null'>dname like concat('%', #{dname}, '%')</if> " +
        "<if test='loc != null'>or loc like concat('%', #{loc}, '%')</if> " +
        "</where>" +
        "order by deptno desc " +
        "<if test='listCount > 0'>limit #{offset}, #{listCount}</if>" +
        "</script>")
    //조회 결과와 Model의 맵핑이 이전 규칙과 동일한 경우 id값으로 이전 규칙을 재사용
    @ResultMap("departmentMap")
    List<Department> selectList(Department input);

    /**
     * 검색 결과의 수를 조회하는 메서드
     * 목록 조회와 동일한 검색 조건을 적용해야 한다.
     * @param input - 조회 조건을 담고 있는 객체
     * @return  조회 결과 수
     */
    @Select("<script>" +
            "select count(*) as cnt from department" +
            "<where>" +
            "<if test='dname != null'>dname like concat('%', #{dname}, '%')</if>" +
            "<if test='loc != null'>or loc like concat('%', #{loc}, '%')</if> " +
            "</where>" +
            "</script>")
    public int selectCount(Department input);
}
