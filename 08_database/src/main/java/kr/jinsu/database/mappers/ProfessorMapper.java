package kr.jinsu.database.mappers;

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

import kr.jinsu.database.models.Professor;

@Mapper
public interface ProfessorMapper {
    
    /**
     * 학과 정보를 입력한다.
     * PK값은 파라미터로 전달된 input 객체에 참조로 처리된다.
     * @param input - 입력할 학과 정보에 대한 모델 객체
     * @return  입력된 데이터 수
     */
    @Insert("insert into professor (name, userid, position, sal, hiredate, comm, deptno) values (#{name}, #{userid}, #{position}, #{sal}, #{hiredate}, #{comm}, #{deptno})")
    // insert 문에서 필요한 PK에 대한 옵션 정의
    // --> useGeneratedKeys : auto_increment가 적용된 테이블인 경우 사용
    // --> keyProperty : 파라미터로 전달되는 model 객체에서 PK에 대응되는 멤버변수
    // --> keyColumn : 테이블의 PK 컬럼 명
    @Options(useGeneratedKeys = true, keyProperty = "profno", keyColumn = "profno")
    int insert(Professor input);

    /**
     * 
     * @param input -   수정할 데이터에 대한 모델 객체
     * @return  수정된 데이터 수
     */
    @Update("update professor set name=#{name}, userid=#{userid}, position=#{position}, sal=#{sal}, hiredate=#{hiredate}, comm=#{comm}, deptno=#{deptno} where profno=#{profno}")
    int update(Professor input);


    @Delete("delete from professor where profno=#{profno}")
    int delete(Professor input);

    @Delete("delete from professor where deptno = #{deptno}")
    int deleteByDeptno(Professor input);

    @Select("select " + 
            "profno, name, userid, position, sal, " +
            "date_format(hiredate, '%Y-%m-%d') as hiredate, comm, " +
            "p.deptno as deptno, dname " +
            "from professor p " + 
            "inner join department d on p.deptno = d.deptno " +
            "where profno=#{profno}")
    /** 조회 결과와 리턴할 model 객체를 연결하기 위한 규칙 정의
     * --> property : model 객체의 멤버변수 이름
     * --> column : select 문에 명시된 필드 이름 (AS 옵션을 사용할 경우 별칭으로 명시)
     */
    @Results(id="professorMap", value={
        @Result(property = "profno", column = "profno"),
        @Result(property = "name", column = "name"),
        @Result(property = "userid", column = "userid"),
        @Result(property = "position", column = "position"),
        @Result(property = "sal", column = "sal"),
        @Result(property = "hiredate", column = "hiredate"),
        @Result(property = "comm", column = "comm"),
        @Result(property = "deptno", column = "deptno"),
        @Result(property = "dname", column = "dname")
    })
    Professor selectItem(Professor input);

    @Select("<script>" +
            "select profno, name, userid, position, sal, hiredate, comm, p.deptno as deptno, dname from professor p " +
            "inner join department d on p.deptno = d.deptno " +
            "<where>" +
            "<if test='name != null'>name like concat('%', #{name}, '%')</if> " +
            "<if test='userid != null'>or userid like concat('%', #{userid}, '%')</if> " +
            "</where>" +
            "order by profno desc " +
            "<if test='listCount > 0'>limit #{offset}, #{listCount}</if>" +
            "</script>")
    @ResultMap("professorMap")
    List<Professor> selectList(Professor input);

    /**
     * 검색 결과의 수를 조회하는 메서드
     * 목록 조회와 동일한 검색 조건을 적용해야 한다.
     * @param input - 조회 조건을 담고 있는 객체
     * @return  조회 결과 수
     */
    @Select("<script>" +
            "select count(*) as cnt from professor p " +
            "inner join department d on p.deptno = d.deptno " +
            "<where>" +
            "<if test='name != null'>name like concat('%', #{name}, '%')</if>" +
            "<if test='userid != null'>or userid like concat('%', #{userid}, '%')</if> " +
            "</where>" +
            "</script>")
    public int selectCount(Professor input);
}
